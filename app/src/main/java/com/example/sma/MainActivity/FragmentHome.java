package com.example.sma.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sma.CreateMeeting.ActivityCreateMeeting;
import com.example.sma.Database.CollectionReceiverCallback;
import com.example.sma.Database.FirebaseControl;
import com.example.sma.Database.LocalDatabase;
import com.example.sma.Database.ReceiverCallback;
import com.example.sma.Model.MeetingObject;
import com.example.sma.Profile.ActivityProfile;
import com.example.sma.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FragmentHome extends Fragment implements View.OnClickListener {

    // Dette er "hovedaktiviteten" hvor man kan se alle sine møder og starte oprettelsesprocessen af nye møder.
    // Klassen anvender MeetingCardAdapter til at vise de allerede oprettede møder.


    RecyclerView recyclerView;
    MeetingCardAdapter adapter;
    Button but_create, but_profile;
    List<MeetingObject> meetingList;
    SwipeRefreshLayout swipe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment_1, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_agenda);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        but_create = view.findViewById(R.id.but_createMeeting);
        but_create.setOnClickListener(this);

        but_profile = view.findViewById(R.id.but_profile);
        but_profile.setOnClickListener(this);

        swipe = view.findViewById(R.id.swipeContacts);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            ;
        });



        meetingList = LocalDatabase.LD.retriveMeetingList();
        adapter = new MeetingCardAdapter(getContext(), meetingList);
        recyclerView.setAdapter(adapter);

        checkRefresh();
        return view;
    }


    // Opsættelse af de forskellige knapper
    @Override
    public void onClick(View view) {
        if (view == but_create) {
            Intent intent = new Intent(getActivity(), ActivityCreateMeeting.class);
            startActivity(intent);
        }
        if (view == but_profile) {
            Intent profile = new Intent(getActivity(), ActivityProfile.class);
            startActivity(profile);
        }
    }


    public void refresh() {
        swipe.setRefreshing(true);
        FirebaseControl.fc.retrieveAllMeetings(new CollectionReceiverCallback() {
            @Override
            public void onSuccess(Task<QuerySnapshot> task) {

                if(task.isSuccessful()) {
                    meetingList = LocalDatabase.LD.retriveMeetingList();

                    // Opsættelse af adapter
                    adapter = new MeetingCardAdapter(getContext(), meetingList);
                    recyclerView.setAdapter(adapter);
                    if (meetingList.size() == 0){
                        recyclerView.setAdapter(null);
                    }
                    swipe.setRefreshing(false);


                }
            }

            @Override
            public void onFailure(Exception exception) {
                System.out.println("failure");
            }

            @Override
            public void noData() {
                System.out.println("no data");
            }
        });


        // "Lappe løsning" til at refresh ikke kører forevigt. Skal ændres hvis der er tid.
        Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                if (LocalDatabase.LD.retriveMeetingList().isEmpty()) {
                    swipe.setRefreshing(false);
                }
            }
        };
        handler.postDelayed(run, 2000);
    }


    // https://stackoverflow.com/questions/7876043/android-new-intent-starts-particular-method

    public void checkRefresh() {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras == null) {
            // Do nothing
        } else {

            Handler handler = new Handler();
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    refresh();
                }
            };
            handler.postDelayed(run,300);
        }
    }
}

