package com.example.sma;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CreateMeetingActivity extends AppCompatActivity {






    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createmeeting);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateMeetingFragment()).commit();

    }









    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}



