package com.example.sma.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sma.R;

public class SplashScreen extends AppCompatActivity implements View.OnClickListener {

    //En splashscreen klasse der muligvis skal implementers hvis det tager lang tid at indlæse information når appen åbnes.



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_activity);



        openActivity();


    }

    private void openActivity() {

        Intent intent = new Intent(this, ActivityRegister.class);
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);

    }
}
