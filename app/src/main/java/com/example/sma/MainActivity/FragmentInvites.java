package com.example.sma.MainActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

//import com.example.sma.Model.Contact;

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




        adapter = new MeetingInviteAdapter(getContext(), inviteList);


        //LocalDatabase.LD.deleteInviteList();
        //   refreshContacts();
        inviteList = LocalDatabase.LD.retrieveMeetingInviteLst();


        checkRefresh();

        inviteList = LocalDatabase.LD.retrieveMeetingInviteLst();
        adapter = new MeetingInviteAdapter(getContext(), inviteList);
        recyclerViewInvites.setAdapter(adapter);
        refreshSwipe.setRefreshing(false);



        return view;


    }

    public void refreshInvites() {

        FirebaseControl.fc.retrieveAllMeetingInvites(new CollectionReceiverCallback() {
            @Override
            public void onSuccess(Task<QuerySnapshot> task) {
                inviteList = LocalDatabase.LD.retrieveMeetingInviteLst();
                adapter = new MeetingInviteAdapter(getContext(), inviteList);
                recyclerViewInvites.setAdapter(adapter);
                refreshSwipe.setRefreshing(false);
                Log.d(TAG, "Refreshed all contacts");
            }

            @Override
            public void onFailure(Exception exception) {
                refreshSwipe.setRefreshing(false);
                Log.d(TAG, "Failed to retrieve contacts" + exception);
            }

            @Override
            public void noData() {
                refreshSwipe.setRefreshing(false);
                Log.d(TAG, "There is no contacts");
            }
        });


        if (inviteList.isEmpty()) {
            // Skjuler tekst hvis der ikke er nogle contact requests.
            recyclerViewInvites.setAdapter(null);
        }
    }


    // https://stackoverflow.com/questions/7876043/android-new-intent-starts-particular-method

    public void checkRefresh() {

        if(refreshFragment){
            refreshInvites();
            // refreshFragment = false;
        }
        else{
            inviteList = LocalDatabase.LD.retrieveMeetingInviteLst();
            adapter = new MeetingInviteAdapter(getContext(), inviteList);

            recyclerViewInvites.setAdapter(adapter);


        }
    }
}

