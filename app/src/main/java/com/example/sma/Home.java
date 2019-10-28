package com.example.sma;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Home extends Fragment {
    private static final String TAG = "Home";


    private Button btnNavHome;
    private Button btnNavInvites;
    private Button btnNavMeetings;
    private Button btnNavFrontpage;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }



}
