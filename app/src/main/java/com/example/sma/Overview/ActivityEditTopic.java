package com.example.sma.Overview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.sma.Model.MeetingObject;
import com.example.sma.Model.Topic;
import com.example.sma.Database.LocalDatabase;
import com.example.sma.R;

import java.util.List;

public class ActivityEditTopic extends Activity {


    Button updateTopic;
    MeetingObject tempMeeting;
    List <MeetingObject> meetingObjectList;
    private Topic topic;
    EditText topicTitle;
    EditText topicDesc;
    String title = "";
    String desc = "";
    int meetingPos;
    int cardPos;
    LocalDatabase db = new LocalDatabase();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_update_meeting);

        updateTopic = findViewById(R.id.but_updateTopic);
        topicTitle = findViewById(R.id.topicTitle);
        topicDesc = findViewById(R.id.topic_description);



        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        desc = bundle.getString("desc");
        meetingPos = bundle.getInt("MeetingPos");
        cardPos = bundle.getInt("CardPos");

        topicTitle.setText(title);
        topicDesc.setText(desc);


        meetingObjectList = db.retriveMeetingList();
        tempMeeting = meetingObjectList.get(meetingPos);


        updateTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (topicTitle.getText().toString().isEmpty() || topicDesc.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please insert all informations", Toast.LENGTH_SHORT).show();
                } else {
                    topic = new Topic();
                    topic.setTopicName(topicTitle.getText().toString());
                    topic.setTopicDescription(topicDesc.getText().toString());
                    tempMeeting.topics.remove(cardPos);
                    tempMeeting.topics.add(cardPos,topic);
                    db.updateMeeting(meetingPos,tempMeeting);
                    finish();

                    /*
                    Intent intent = new Intent(getApplicationContext(), ActivityOverview.class);
                    intent.putExtra("update", true);
                    startActivity(intent);

                     */
                }
            }
        });
    }
}