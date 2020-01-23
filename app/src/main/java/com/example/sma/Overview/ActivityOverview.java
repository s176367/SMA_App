package com.example.sma.Overview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.crashlytics.android.Crashlytics;
import com.example.sma.Database.LocalDatabase;
import com.example.sma.MainActivity.ActivityMain;
import com.example.sma.Model.MeetingObject;
import com.example.sma.R;
import com.google.android.material.tabs.TabLayout;

// @Author Gustav Kristensen s180077
public class ActivityOverview extends AppCompatActivity {

    int position;
    boolean update = false;
    ViewPager viewPager;
    private LocalDatabase db = new LocalDatabase();
    private MeetingObject meeting = db.retriveMeetingList().get(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_activity);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        update = intent.getBooleanExtra("update", false);
        meeting = db.retriveMeetingList().get(position);
        viewPager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(new AdapterViewPager(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager, true);

        if (update) {
            viewPager.setCurrentItem(1);
        }
        else{
            viewPager.setCurrentItem(0);
            update = false;
        }

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

    public int getPosition(){
        return position;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


