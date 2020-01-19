package com.example.sma.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.sma.Profile.ActivityLogin;
import com.example.sma.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityMain extends AppCompatActivity {

    // Denne klasse anvendes til at vise de tre hovedfragmenter i appen, vha. bundnavigation


    protected static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        // Da appen starter herfra, chekkes der her hvis brugeren er logget ind. Hvis bruger ikke er logget ind starter login aktivitet.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent i = new Intent(ActivityMain.this, ActivityLogin.class);
            startActivity(i);
            finish();
        }

        super.onCreate(savedInstanceState);

        mContext = this;
        setContentView(R.layout.main_activity);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();




    }

    // Opsætning af bund navigationsbaren
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragmemt = null;

                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragmemt = new FragmentHome();
                            break;

                        case R.id.nav_contacts:
                            selectedFragmemt = new FragmentContacts();
                            break;

                        case R.id.nav_invites:
                            selectedFragmemt = new FragmentInvites();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragmemt).commit();
                    return true;
                }
            };

    public static Context getContext(){
        return mContext;
    }

    @Override
    public void onBackPressed() {

    }


}


