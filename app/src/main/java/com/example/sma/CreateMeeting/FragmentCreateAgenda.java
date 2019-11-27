package com.example.sma.CreateMeeting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sma.R;

public class FragmentCreateAgenda extends Fragment {

    Button addTopic;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_createagenda, container, false);

        addTopic = view.findViewById(R.id.addTopic);
        addTopic.setOnClickListener(new View.OnClickListener() {


           @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(container.getId(), new FragmentAddTopic()).commit();
            }
        });
        return view;
    }






}
