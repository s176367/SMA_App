package com.example.sma.CreateMeeting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.example.sma.MainActivity.ActivityMain;
import com.example.sma.Model.MeetingObject;
import com.example.sma.R;

// @Author Gustav Kristensen s180077
public class ActivityCreateMeeting extends AppCompatActivity {

    // Denne aktivitet bruges til at vise de gældende fragmenter der anvendes til at mødeoprettelsesprocessen.
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

        Button crashButton = new Button(this);
        crashButton.setText("Crash!");
        crashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Crashlytics.getInstance().crash(); // Force a crash
            }
        });

        addContentView(crashButton, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            Intent intent =  new Intent(this, ActivityMain.class);
            startActivity(intent);
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}



