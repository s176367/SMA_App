package com.example.sma.CreateMeeting;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sma.MainActivity.ActivityMain;
import com.example.sma.Model.MeetingObject;
import com.example.sma.R;


public class ActivityCreateMeeting extends AppCompatActivity {

    /*
    Denne aktivitet bruges til at vise de gældende fragmenter der anvendes til at mødeoprettelsesprocessen.

     */


    private MeetingObject Meeting = new MeetingObject();

    public MeetingObject getMeeting() {
        return Meeting;
    }

    public void setMeeting(MeetingObject meeting) {
        Meeting = meeting;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createmeeting_activity);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentCreateMeeting()).commit();


    }



    /*
    Metode til at håndtere hvad appen skal gøre ved tilbageklik fra de respektive fragmenter.

    Hvis det er det første fragment i processen lukkes aktivitetenm, men hvis det er en af de
    senere så går den tilbage til det forrige fragment.
     */

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            Intent intent =  new Intent(this, ActivityMain.class);
            startActivity(intent);
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }
}



