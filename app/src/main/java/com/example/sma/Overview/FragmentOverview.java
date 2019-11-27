package com.example.sma.Overview;

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

import com.example.sma.CreateMeeting.ActivityCreateMeeting;
import com.example.sma.FakeMeetingDatabase;
import com.example.sma.MainActivity.ActivityMain;
import com.example.sma.Model.MeetingObject;
import com.example.sma.R;

import java.util.ArrayList;

public class FragmentOverview extends Fragment implements View.OnClickListener {

    String titleString;
    TextView title;
    TextView date;
    TextView time;
    TextView duration;
    TextView location;
    Button back;
    Button delete;
    String recivedInfo;
    ArrayList<MeetingObject> list;
    FakeMeetingDatabase db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_overview1, container, false);

        delete = view.findViewById(R.id.but_delete);
        delete.setOnClickListener(this);
        time = view.findViewById(R.id.time_text);
        title = view.findViewById(R.id.title_text);
        date = view.findViewById(R.id.date_text);
        duration = view.findViewById(R.id.duration_text);
        location = view.findViewById(R.id.location_text);
        db = new FakeMeetingDatabase();
        recivedInfo = ((ActivityOverview)getActivity()).getTitleString();

        list = db.retriveMeetingList();


        for (int i = 0; i <list.size() ; i++) {
            if (list.get(i).getTitle().equals(recivedInfo)){
                title.setText(list.get(i).getTitle());
                time.setText(list.get(i).getTime());
                date.setText(list.get(i).getDate());
                location.setText(list.get(i).getLocation());
                duration.setText(list.get(i).getDuration());
            }
        }

        return view;
    }


    @Override
    public void onClick(View view) {
        if (view == delete) {
            list = db.retriveMeetingList();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getTitle().equals(recivedInfo)) {
                    db.deleteMeeting(list.get(i).getTitle());
                }
            }
            Intent intent = new Intent(getContext(), ActivityMain.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
    }
}

