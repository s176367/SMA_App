package com.example.sma.Overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.Database.LocalDatabase;
import com.example.sma.Model.MeetingObject;
import com.example.sma.R;

import java.util.List;

public class FragmentAgendaOverview extends Fragment {

    LocalDatabase db = new LocalDatabase();

    private AdapterEditTopic adapter;
    private RecyclerView.LayoutManager layoutManager;
    private MeetingObject tempMeeting;
    private int position;
    private List<MeetingObject> meetingList;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.overview_fragment_2, container, false);
        recyclerView = view.findViewById(R.id.recycler_overview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        position = ((ActivityOverview)getActivity()).getPosition();

        LocalDatabase db = new LocalDatabase();
        meetingList = db.retriveMeetingList();
        tempMeeting = meetingList.get(position);

        adapter = new AdapterEditTopic(view.getContext(), tempMeeting.topics, ((ActivityOverview) getActivity()).position);
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        LocalDatabase db = new LocalDatabase();
        meetingList = db.retriveMeetingList();
        tempMeeting = meetingList.get(position);

        adapter = new AdapterEditTopic(getContext(), tempMeeting.topics, ((ActivityOverview) getActivity()).position);
        recyclerView.setAdapter(adapter);
    }
}
