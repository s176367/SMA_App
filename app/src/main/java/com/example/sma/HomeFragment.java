package com.example.sma;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    MeetingAdapter adapter;
    Button but_create;

    List<MeetingCard> meetingList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        meetingList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);


        but_create = view.findViewById(R.id.but_createMeeting);
        but_create.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


       // fake meetings for prototype
        meetingList.add(new MeetingCard(0,"Møde", "19:00-20:00", "Ballerup DTU R2", 10)
        );
        meetingList.add(new MeetingCard(1,"Vigtigt møde", "19:00-21:00", "Ballerup DTU R4", 4));
        meetingList.add(new MeetingCard(2,"Vigtigt møde", "19:00-21:00", "Ballerup DTU R4", 4));
        meetingList.add(new MeetingCard(1,"Vigtigt møde", "19:00-21:00", "Ballerup DTU R4", 4));
        meetingList.add(new MeetingCard(1,"Vigtigt møde", "19:00-21:00", "Ballerup DTU R4", 4));


        adapter = new MeetingAdapter(getContext(), meetingList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onClick(View view) {

        if (view == but_create){
            Intent intent = new Intent(getActivity(), CreateMeetingActivity.class);
            startActivity(intent);

        }
    }
}
