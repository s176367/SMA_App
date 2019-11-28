package com.example.sma.CreateMeeting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.FakeMeetingDatabase;
import com.example.sma.MainActivity.ActivityMain;
import com.example.sma.Model.MeetingObject;
import com.example.sma.Model.Topic;
import com.example.sma.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentCreateAgenda extends Fragment{

    Button but_addTopic;
    Button but_finishAgenda;
    FakeMeetingDatabase db = new FakeMeetingDatabase();

    private TopicAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private MeetingObject tempMeeting;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_createagenda, container, false);
        recyclerView = view.findViewById(R.id.recycler_agenda);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        tempMeeting = ((ActivityCreateMeeting)getActivity()).getMeeting();

        adapter = new TopicAdapter(view.getContext(), tempMeeting.topics);
        recyclerView.setAdapter(adapter);




        but_finishAgenda = view.findViewById(R.id.finish_agenda);
        but_finishAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tempMeeting.topics != null) {
                    FakeMeetingDatabase db = new FakeMeetingDatabase();
                    db.addMeeting(tempMeeting);
                    Intent intent = new Intent(getContext(), ActivityMain.class);
                    startActivity(intent);
                    getActivity().finish();
               }
            }
        });

        but_addTopic = view.findViewById(R.id.addTopic);
        but_addTopic.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(container.getId(), new FragmentAddTopic()).commit();
            }
        });
        return view;
    }



}
