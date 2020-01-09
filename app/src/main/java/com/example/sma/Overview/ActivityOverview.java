package com.example.sma.Overview;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.sma.Profile.LocalDatabase;
import com.example.sma.Model.MeetingObject;
import com.example.sma.R;
import com.google.android.material.tabs.TabLayout;

public class ActivityOverview extends AppCompatActivity {

    int position;


    private LocalDatabase db = new LocalDatabase();

    private MeetingObject meeting = db.retriveMeetingList().get(0);


    public MeetingObject getMeeting() {
        return meeting;
    }

    public void setMeeting(MeetingObject meeting) {
        this.meeting = meeting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_activity);
        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);

        meeting = db.retriveMeetingList().get(position);




        ViewPager viewPager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(new AdapterViewPager(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager,true);

    
    }
    
    
    public int getPosition(){
        return position;
        
    }


}


