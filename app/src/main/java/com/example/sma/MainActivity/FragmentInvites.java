package com.example.sma.MainActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.sma.Database.CollectionReceiverCallback;
import com.example.sma.Database.FirebaseControl;
import com.example.sma.Database.LocalDatabase;
import com.example.sma.Model.MeetingObject;
import com.example.sma.R;
import com.example.sma.RefreshContext;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.List;

// @Author Guztav Kristensen s180077

public class FragmentInvites extends Fragment {

    // Dette fragment bruges til at vise kontakterne vha. contactsadapteren der indsættes i et recyclerview

    String TAG = this.getClass().getName();
    RecyclerView recyclerViewInvites;
    MeetingInviteAdapter adapter;
    List<MeetingObject> inviteList;
    SwipeRefreshLayout refreshSwipe;
    FragmentInvites fragment;
    boolean refreshFragment = true;





    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment_2, container, false);
        fragment = this;

        refreshSwipe = view.findViewById(R.id.refreshInvites);
        refreshSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshInvites();
            }
        });

        recyclerViewInvites = view.findViewById(R.id.recycler_meeting_invites);
        recyclerViewInvites.setHasFixedSize(true);
        recyclerViewInvites.setNestedScrollingEnabled(true);
        recyclerViewInvites.setLayoutManager(new LinearLayoutManager(getContext()));
        inviteList = LocalDatabase.LD.retrieveMeetingInviteLst();
        adapter = new MeetingInviteAdapter(getContext(), inviteList);
        recyclerViewInvites.setAdapter(adapter);
        checkRefresh();

        return view;
    }

    public void refreshInvites() {


        FirebaseControl.fc.retrieveAllMeetingInvites(new CollectionReceiverCallback() {
            @Override
            public void onSuccess(Task<QuerySnapshot> task) {
                inviteList = LocalDatabase.LD.retrieveMeetingInviteLst();
                adapter = new MeetingInviteAdapter(getContext(), inviteList);
                recyclerViewInvites.setAdapter(adapter);
                Log.d(TAG, "Refreshed all invites");
                refreshSwipe.setRefreshing(false);
            }

            @Override
            public void onFailure(Exception exception) {
                Log.d(TAG, "Failed to retrieve invites" + exception);
                refreshSwipe.setRefreshing(false);
            }

            @Override
            public void noData() {
                Log.d(TAG, "There is no invites");
                refreshSwipe.setRefreshing(false);
            }
        });

        if (inviteList.isEmpty()) {
            // Skjuler tekst hvis der ikke er nogle contact requests.
            recyclerViewInvites.setAdapter(null);
            refreshSwipe.setRefreshing(false);
        }
    }

    public void checkRefresh() {
        if (RefreshContext.getInvites()) {

            refreshSwipe.setRefreshing(true);
            Handler handler = new Handler();
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    refreshInvites();
                }
            };

            handler.postDelayed(run, 500);
            RefreshContext.setInvites(false);
        }
        else {

            inviteList = LocalDatabase.LD.retrieveMeetingInviteLst();
            adapter = new MeetingInviteAdapter(getContext(), inviteList);
            recyclerViewInvites.setAdapter(adapter);
            refreshSwipe.setRefreshing(false);
        }

    }
}

