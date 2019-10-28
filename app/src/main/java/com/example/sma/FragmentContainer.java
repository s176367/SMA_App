package com.example.sma;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class FragmentContainer extends AppCompatActivity {

    private static final String TAG ="Fragment Container";

    private SectionsStatePagerAdapter mSectionStatePagerAdapter;
    private ViewPager mViewPager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        Log.d(TAG, "onCreate: Started");

        mSectionStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);
        this.setViewPager(1);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Home(),"Home");
        adapter.addFragment(new Meetings(),"Meetings");
        adapter.addFragment(new Invites(),"Invites");
        viewPager.setAdapter(adapter);
     }



     public void setViewPager(int fragmentnumer){
        mViewPager.setCurrentItem(fragmentnumer);
     }
}
