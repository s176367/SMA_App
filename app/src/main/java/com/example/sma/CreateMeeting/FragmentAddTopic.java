package com.example.sma.CreateMeeting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sma.Model.MeetingObject;
import com.example.sma.R;

public class FragmentAddTopic extends Fragment {


    Button addTopic;
    MeetingObject meetingObject;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_addtopic, container,false);

        addTopic = view.findViewById(R.id.addTopic2);
        meetingObject = ((ActivityCreateMeeting)this.getActivity()).getMeeting();





        return view;

    }
}
