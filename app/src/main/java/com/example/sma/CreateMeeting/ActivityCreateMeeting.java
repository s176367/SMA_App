package com.example.sma.CreateMeeting;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sma.Model.MeetingObject;
import com.example.sma.R;


public class ActivityCreateMeeting extends AppCompatActivity {


    private MeetingObject Meeting = new MeetingObject();

    public MeetingObject getMeeting() {
        return Meeting;
    }

    public void setMeeting(MeetingObject meeting) {
        Meeting = meeting;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.createmeeting_activity);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentCreateMeeting()).commit();

    }

    @Override
    public void onBackPressed()
    {


        if(getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }


    }
}



