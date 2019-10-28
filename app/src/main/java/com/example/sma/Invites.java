package com.example.sma;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Invites extends Fragment {
    private static final String TAG = "Home";

    private Button btnNavHome;
    private Button btnNavInvites;
    private Button btnNavMeetings;
    private Button btnNavFrontpage;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        btnNavFrontpage = view.findViewById(R.id.home);
        btnNavHome = view.findViewById(R.id.homefrag);
        btnNavInvites = view.findViewById(R.id.invitesfrag);
        btnNavMeetings = view.findViewById(R.id.meetingsfrag);
        Log.d(TAG,"onCreateView: Started.");

        btnNavFrontpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Going to Frontpage",Toast.LENGTH_SHORT);
                Intent intent = new Intent(getActivity(), Frontpage.class);
                startActivity(intent);
            }
        });

        btnNavInvites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Going to Invites",Toast.LENGTH_SHORT);
                ((FragmentContainer)getActivity()).setViewPager(0);
            }
        });

        btnNavMeetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Going to Meetings",Toast.LENGTH_SHORT);
                ((FragmentContainer)getActivity()).setViewPager(1);
            }
        });

        btnNavHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Going to Home",Toast.LENGTH_SHORT);
                ((FragmentContainer)getActivity()).setViewPager(2);
            }
        });

        return view;
    }

}
