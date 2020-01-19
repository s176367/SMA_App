package com.example.sma.Overview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.sma.CreateMeeting.ActivityEditMeeting;
import com.example.sma.Database.FirebaseControl;
import com.example.sma.Database.LocalDatabase;
import com.example.sma.Model.MeetingObject;
import com.example.sma.Profile.ActivityProfile;
import com.example.sma.R;
import com.google.android.material.tabs.TabLayout;

public class ActivityOverview extends AppCompatActivity {

    int position;
    boolean update = false;
    ViewPager viewPager;
    Spinner spinner;

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
       /** spinner = findViewById(R.id.spinnerBar);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.edit, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent edit = new Intent(getApplicationContext(), ActivityEditMeeting.class);
                        startActivity(edit);
                        break;
                    case 1:


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });**/




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
    }

    public int getPosition(){
        return position;
        
    }
}


