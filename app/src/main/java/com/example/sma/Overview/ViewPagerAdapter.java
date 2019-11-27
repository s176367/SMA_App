package com.example.sma.Overview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] childFragments;




    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        childFragments = new Fragment[]{
                new FragmentOverview(), new FragmentAgendaOverview()
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
