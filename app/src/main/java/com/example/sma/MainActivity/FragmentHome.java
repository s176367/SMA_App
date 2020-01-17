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
import com.example.sma.Database.FirebaseControl;
import com.example.sma.Database.LocalDatabase;
import com.example.sma.Database.ReceiverCallback;
import com.example.sma.Model.MeetingObject;
import com.example.sma.Profile.ActivityProfile;
import com.example.sma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
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

        View view =  inflater.inflate(R.layout.main_fragment_1, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_agenda);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        but_create = view.findViewById(R.id.but_createMeeting);
        but_create.setOnClickListener(this);

        but_profile = view.findViewById(R.id.but_profile);
        but_profile.setOnClickListener(this);

        swipe = view.findViewById(R.id.swipeScroll);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            };
        });


        meetingList = new ArrayList<>();
        refresh();

        return view;
    }


    // Opsættelse af de forskellige knapper
    @Override
    public void onClick(View view) {
        if (view == but_create){
            Intent intent = new Intent(getActivity(), ActivityCreateMeeting.class);
            startActivity(intent);
        }
        if (view==but_profile){
            Intent profile = new Intent(getActivity(), ActivityProfile.class);
            startActivity(profile);
        }
    }


    public void refresh (){
        swipe.setRefreshing(true);
        LocalDatabase.LD.deleteMeetingList();
        FirebaseControl.fc.getAllMeetings(new ReceiverCallback() {
            @Override
            public void onSuccess(Task<DocumentSnapshot> task) {
                swipe.setRefreshing(false);
                meetingList = LocalDatabase.LD.retriveMeetingList();
                // Opsættelse af adapter
                adapter = new MeetingCardAdapter(getContext(), meetingList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Exception exception) {

            }
            @Override
            public void noData() {

            }
        });
    }


}