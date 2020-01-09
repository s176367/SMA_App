package com.example.sma.Overview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdapterViewPager extends FragmentPagerAdapter {

    private Fragment[] childFragments;




    public AdapterViewPager(@NonNull FragmentManager fm) {
        super(fm);
        childFragments = new Fragment[]{
                new FragmentOverview(), new FragmentAgendaOverview(), new FragmentParticipants()
        };
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return childFragments[position];
    }


    @Override
    public int getCount() {
        return childFragments.length;
    }

}
