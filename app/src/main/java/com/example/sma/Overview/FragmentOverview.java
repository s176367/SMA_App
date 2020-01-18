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

import com.example.sma.Database.FirebaseControl;
import com.example.sma.Database.LocalDatabase;
import com.example.sma.Database.SenderCallback;
import com.example.sma.MainActivity.ActivityMain;
import com.example.sma.Model.MeetingObject;
import com.example.sma.R;

import java.util.ArrayList;

public class FragmentOverview extends Fragment implements View.OnClickListener {

    // Klasse til at vise de basale informationer som mødet indeholder.


    TextView title;
    TextView date;
    TextView time;
    TextView duration;
    TextView location;
    Button back;
    Button delete;
    int position;
    ArrayList<MeetingObject> list;
    LocalDatabase db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.overview_fragment_1, container, false);

        delete = view.findViewById(R.id.but_delete);
        delete.setOnClickListener(this);
        time = view.findViewById(R.id.time_text);
        title = view.findViewById(R.id.title_text);
        date = view.findViewById(R.id.date_text);
        duration = view.findViewById(R.id.duration_text);
        location = view.findViewById(R.id.location_text);
        db = new LocalDatabase();
        position = ((ActivityOverview)getActivity()).getPosition();

        list = db.retriveMeetingList();

                title.setText(list.get(position).getTitle());
                time.setText(list.get(position).getTime());
                date.setText(list.get(position).getDate());
                location.setText(list.get(position).getLocation());
                duration.setText(list.get(position).getDuration());



        return view;
    }



    @Override
    public void onClick(View view) {
        if (view == delete) {

            FirebaseControl.fc.deleteMeeting(list.get(position).getId(), new SenderCallback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
            db.deleteMeeting(position);
            }
            Intent intent = new Intent(getContext(), ActivityMain.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

    }
}


