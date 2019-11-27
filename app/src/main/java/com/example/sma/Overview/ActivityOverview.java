package com.example.sma.Overview;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sma.R;

public class ActivityOverview extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetingoverview);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");







        Bundle bundle = new Bundle();
        bundle.putString("title", title );
        FragmentOverview overFrag = new FragmentOverview();
        overFrag.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, overFrag).commit();


    }






}


