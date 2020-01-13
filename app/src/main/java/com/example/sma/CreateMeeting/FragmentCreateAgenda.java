package com.example.sma.CreateMeeting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.Database.MeetingDAO;
import com.example.sma.Database.LocalDatabase;
import com.example.sma.MainActivity.ActivityMain;
import com.example.sma.Model.MeetingObject;
import com.example.sma.R;

public class FragmentCreateAgenda extends Fragment{

    CardView newAgendaCard;
    ScrollView scroll;
    Button but_finishAgenda;
    LocalDatabase db = new LocalDatabase();

    private TopicAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private MeetingObject tempMeeting;
    private RecyclerView recyclerView;
    MeetingDAO meetingDAO = new MeetingDAO();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.createmeeting_fragment_2, container, false);
        recyclerView = view.findViewById(R.id.recycler_agenda);
        newAgendaCard = view.findViewById(R.id.newAgendaCard);
        scroll = view.findViewById(R.id.scrollview);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        tempMeeting = ((ActivityCreateMeeting)getActivity()).getMeeting();
        adapter = new TopicAdapter(view.getContext(), tempMeeting.topics);
        recyclerView.setAdapter(adapter);
        but_finishAgenda = view.findViewById(R.id.finish_agenda);



        but_finishAgenda.setVisibility(View.INVISIBLE);

        if (!tempMeeting.topics.isEmpty()){
            but_finishAgenda.setVisibility(View.VISIBLE);
        }

        but_finishAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tempMeeting.topics.isEmpty()) {
                    LocalDatabase db = new LocalDatabase();
                    db.addMeeting(tempMeeting);
                    meetingDAO.uploadMeeting(tempMeeting);
                    Intent intent = new Intent(getContext(), ActivityMain.class);
                    startActivity(intent);
                    getActivity().finish();
               }
                else {
                    Toast.makeText(getContext(), "Insert a topic", Toast.LENGTH_SHORT).show();
                }
            }
        });

        newAgendaCard.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(container.getId(), new FragmentAddTopic()).addToBackStack(null).commit();
            }
        });

     /*   scroll.post(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });
        */
        scroll.getBottom();

        return view;
    }


}
