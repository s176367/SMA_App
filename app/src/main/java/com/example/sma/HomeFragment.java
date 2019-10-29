package com.example.sma;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    MeetingAdapter adapter;

    List<MeetingCard> meetingList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        meetingList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        meetingList.add(
                new MeetingCard(0,"Møde", "19:00-20:00", "Ballerup DTU R2", 10)
        );

        meetingList.add(
                new MeetingCard(1,"Vigtigt møde", "19:00-21:00", "Ballerup DTU R4", 4)
        );
        meetingList.add(
                new MeetingCard(2,"Vigtigt møde", "19:00-21:00", "Ballerup DTU R4", 4)
        );

        meetingList.add(
                new MeetingCard(1,"Vigtigt møde", "19:00-21:00", "Ballerup DTU R4", 4)
        );

        meetingList.add(
                new MeetingCard(1,"Vigtigt møde", "19:00-21:00", "Ballerup DTU R4", 4)
        );

        adapter = new MeetingAdapter(getContext(), meetingList);
        recyclerView.setAdapter(adapter);

        return view;








    }
}
