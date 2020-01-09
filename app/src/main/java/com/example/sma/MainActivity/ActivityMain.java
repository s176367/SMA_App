package com.example.sma.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.sma.Profile.ActivityLogin;
import com.example.sma.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityMain extends AppCompatActivity {


    protected static Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent i = new Intent(ActivityMain.this, ActivityLogin.class);
            startActivity(i);
        }



        super.onCreate(savedInstanceState);
        overridePendingTransition(0,0);
        mContext = this;
        setContentView(R.layout.main_activity);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();

    }


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


}


