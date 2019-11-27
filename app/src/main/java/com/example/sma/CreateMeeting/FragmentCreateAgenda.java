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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.R;

public class FragmentCreateAgenda extends Fragment {

    Button addTopic;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_createagenda, container, false);

        recyclerView = view.findViewById(R.id.recycler_agenda);

        layoutManager = new LinearLayoutManager(view.getContext());
        mAdapter = new Topic


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
