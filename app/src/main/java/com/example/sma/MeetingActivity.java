package com.example.sma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MeetingActivity extends AppCompatActivity implements View.OnClickListener {



    TextView meetings;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);
        meetings = findViewById(R.id.meetings);

        Controller controller = new Controller();




        back = findViewById(R.id.back);

        back.setOnClickListener(this);












    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
