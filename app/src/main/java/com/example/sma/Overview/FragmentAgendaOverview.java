package com.example.sma.Overview;

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

import com.example.sma.CreateMeeting.ActivityCreateMeeting;
import com.example.sma.CreateMeeting.FragmentAddTopic;
import com.example.sma.CreateMeeting.TopicAdapter;
import com.example.sma.FakeMeetingDatabase;
import com.example.sma.MainActivity.ActivityMain;
import com.example.sma.Model.MeetingObject;
import com.example.sma.R;

import java.util.List;

public class FragmentAgendaOverview extends Fragment {

    FakeMeetingDatabase db = new FakeMeetingDatabase();

    private TopicAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private MeetingObject tempMeeting;
    private String titleString;
    private List<MeetingObject> meetingList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview2, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_overview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        titleString = ((ActivityOverview)getActivity()).getTitleString();

        FakeMeetingDatabase db = new FakeMeetingDatabase();
        meetingList = db.retriveMeetingList();


        for (int i = 0; i <meetingList.size() ; i++) {
            if (meetingList.get(i).getTitle().equalsIgnoreCase(titleString)){
                tempMeeting = meetingList.get(i);
            }

        }
        adapter = new TopicAdapter(view.getContext(), tempMeeting.topics);
        recyclerView.setAdapter(adapter);



        return view;
    }



}
