package com.example.sma.CreateMeeting;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sma.R;


public class ActivityCreateMeeting extends AppCompatActivity {






    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createmeeting);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentCreateMeeting()).commit();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}



