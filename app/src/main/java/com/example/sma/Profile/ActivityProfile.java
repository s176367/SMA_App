package com.example.sma.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.example.sma.Database.LocalDatabase;
import com.example.sma.MainActivity.ActivityMain;
import com.example.sma.MainActivity.FragmentHome;
import com.example.sma.Model.User;
import com.example.sma.Overview.FragmentOverview;
import com.example.sma.R;
import com.google.firebase.auth.FirebaseAuth;

// @Author Sercan Bicen s185030
public class ActivityProfile extends AppCompatActivity {

    TextView Fullname, Email, Phone, Company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        Fullname = findViewById(R.id.profileName);
        Email = findViewById(R.id.profileEmail);
        Phone = findViewById(R.id.profilePhone);
        Company = findViewById(R.id.profileCompany);

        User user;
        user = LocalDatabase.LD.getUser();
        try {
            Fullname.setText(user.getName());
            Email.setText(user.getEmail());
            Phone.setText(user.getPhone());
            Company.setText(user.getCompany());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void logout(View v) {
        FirebaseAuth.getInstance().signOut();
        LocalDatabase.LD.deleteMeetingList();
        LocalDatabase.LD.deleteParticipantList();
        LocalDatabase.LD.deleteContactList();
        LocalDatabase.LD.deleteMeetingInviteList();
        LocalDatabase.LD.deleteContactInviteList();
        startActivity(new Intent(getApplicationContext(), ActivityLogin.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityMain.class);
        startActivity(intent);
    }
}
