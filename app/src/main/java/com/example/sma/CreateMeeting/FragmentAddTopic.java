package com.example.sma.CreateMeeting;

import android.content.Intent;
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

import com.example.sma.Model.MeetingObject;
import com.example.sma.Model.Topic;
import com.example.sma.R;

import java.util.List;

public class FragmentAddTopic extends Fragment {


    private Button addTopic;
    private MeetingObject tempMeeting;
    private Topic topic;
    private EditText topicTitle;
    private EditText topicDesc;
    private String title = "";
    private String desc = "";
    private int position;
    private ViewGroup container;
    private Boolean updateTopic = false;

    public FragmentAddTopic(){};


    public FragmentAddTopic (String title, String desc,int position){
        this.title = title;
        this.desc = desc;
        this.position = position;
        updateTopic = true;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        tempMeeting = ((ActivityCreateMeeting)getActivity()).getMeeting();

        View view = inflater.inflate(R.layout.createmeeting_fragment_3, container,false);

        topicTitle = view.findViewById(R.id.topicTitle);

        topicDesc = view.findViewById(R.id.topic_description);

        if (updateTopic){
            topicTitle.setText(title);
            topicDesc.setText(desc);
        }

        this.container = container;

        addTopic = view.findViewById(R.id.but_addTopic);

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
                    if (updateTopic){
                        tempMeeting.topics.remove(position);
                        tempMeeting.topics.add(position,topic);
                    }
                    else tempMeeting.topics.add(topic);


                    updateTopic = false;
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
                                                                                                                            
        return view;

    }














}
