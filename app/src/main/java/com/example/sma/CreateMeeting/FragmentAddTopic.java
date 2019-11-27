package com.example.sma.CreateMeeting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sma.Model.MeetingObject;
import com.example.sma.Model.Topic;
import com.example.sma.R;

public class FragmentAddTopic extends Fragment {


    Button addTopic;
    MeetingObject tempMeeting;
    private Topic topic;
    EditText topicTitle;
    EditText topicDesc;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {


        tempMeeting = ((ActivityCreateMeeting)getActivity()).getMeeting();

        View view = inflater.inflate(R.layout.fragment_addtopic, container,false);

        topicTitle = view.findViewById(R.id.topicTitle);
        topicDesc = view.findViewById(R.id.topic_description);


        addTopic = view.findViewById(R.id.but_addtopic);




        addTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (topicTitle.getText().toString().isEmpty() || topicDesc.getText().toString().isEmpty()){

                    Toast.makeText(getContext(), "Please insert all informations", Toast.LENGTH_SHORT).show();

                }
                else {
                    topic = new Topic();
                    topic.setTopicName(topicTitle.getText().toString());
                    topic.setTopicDescription(topicDesc.getText().toString());
                    tempMeeting.topics.add(topic);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(container.getId(), new FragmentCreateAgenda()).commit();
                }
            }
        });





        return view;

    }
}
