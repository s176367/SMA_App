package com.example.sma;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Overview_fragment extends Fragment implements View.OnClickListener {


    TextView title;
    TextView date;
    TextView time;
    TextView duration;
    TextView location;
    Button back;
    Button delete;
    String recivedInfo;
    ArrayList<MeetingCard> list;
    FakeMeetingDatabase db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_overview1, container, false);



        back = view.findViewById(R.id.but_back);
        back.setOnClickListener(this);
        delete = view.findViewById(R.id.but_delete);
        delete.setOnClickListener(this);
        time = view.findViewById(R.id.time_text);
        title = view.findViewById(R.id.title_text);
        date = view.findViewById(R.id.date_text);
        duration = view.findViewById(R.id.duration_text);
        location = view.findViewById(R.id.location_text);
        db = new FakeMeetingDatabase();
        recivedInfo = getArguments().getString("title");
        list = db.retriveMeetingList();


        for (int i = 0; i <list.size() ; i++) {
            if (list.get(i).getTitle().equals(recivedInfo)){
                title.setText(list.get(i).getTitle());
                time.setText(list.get(i).getTime());
                date.setText(list.get(i).getDate());
                location.setText(list.get(i).getLocation());
            }
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == back) {
            getActivity().finish();
        }

        if (view == delete) {
            list = db.retriveMeetingList();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getTitle().equals(recivedInfo)) {
                    db.deleteMeeting(list.get(i).getTitle());
                }
            }
            Intent intent = new Intent(getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
    }
}

