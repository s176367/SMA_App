package com.example.sma;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OverviewActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetingoverview);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");







        Bundle bundle = new Bundle();
        bundle.putString("title", title );
        Overview_fragment overFrag = new Overview_fragment();
        overFrag.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, overFrag).commit();


    }






}


