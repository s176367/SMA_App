package com.example.sma;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Frontpage extends AppCompatActivity implements View.OnClickListener {


    Button account;
    Button favorites;
    Button meetings;
    Button invitations;
    Button create;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontpage);

        account = findViewById(R.id.account);
        meetings = findViewById(R.id.meetings);
        favorites = findViewById(R.id.favorites);
        invitations = findViewById(R.id.invitations);
        create = findViewById(R.id.create);

        meetings.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), FragmentContainer.class);
        startActivity(intent);
    }
}

