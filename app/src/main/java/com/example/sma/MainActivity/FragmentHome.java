package com.example.sma.MainActivity;

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
import com.example.sma.FakeMeetingDatabase;
import com.example.sma.Model.MeetingObject;
import com.example.sma.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    MeetingAdapter adapter;
    Button but_create;


    List<MeetingObject> meetingList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_home, container, false);


        meetingList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_agenda);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        but_create = view.findViewById(R.id.but_createMeeting);
        but_create.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FakeMeetingDatabase db = new FakeMeetingDatabase();



       updateMeetingList();
        adapter = new MeetingAdapter(getContext(), meetingList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == but_create){
            Intent intent = new Intent(getActivity(), ActivityCreateMeeting.class);
            startActivity(intent);
        }
    }



    public void updateMeetingList(){
        FakeMeetingDatabase db = new FakeMeetingDatabase();
        meetingList = db.retriveMeetingList();





    }
}