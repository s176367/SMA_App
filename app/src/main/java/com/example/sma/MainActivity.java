package com.example.sma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static ArrayList<User> userList = new ArrayList<>();
    Button meeting;
    Button create;
    Button delete;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        meeting =findViewById(R.id.meetings);
        create = findViewById(R.id.create);
        delete = findViewById(R.id.Invitations);

        meeting.setOnClickListener(this);
        create.setOnClickListener(this);
        delete.setOnClickListener(this);








    }

    @Override
    public void onClick(View view) {
        if (view == meeting){
            Intent intent = new Intent(this, MeetingActivity.class);
            startActivity(intent);

        }


        if (view == delete){

        }


    }
}
