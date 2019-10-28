package com.example.sma;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.Button;

public class Frontpage extends AppCompatActivity {




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





    }
}


