package com.example.sma.Database;

import androidx.annotation.NonNull;

import com.example.sma.Model.MeetingObject;
import com.example.sma.Model.Topic;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetingDAO extends MeetingObject {




    FirebaseFirestore db = FirebaseFirestore.getInstance();
    
   



    public void uploadMeeting(MeetingObject meeting){

        Map<String, Object> mapMeeting = new HashMap<>();


        mapMeeting.put("title", meeting.getTitle());
        mapMeeting.put("time", meeting.getTime());
        mapMeeting.put("location", meeting.getLocation());
        mapMeeting.put("duration", meeting.getDuration());
        mapMeeting.put("date", meeting.getDate());
        
        //mapMeeting.put("topic", meeting.getTopics());

        db.collection("meetings").add(mapMeeting).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                System.out.println("Data blev tilføjet!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Der blev ikke tilføjet data");
            }
        });




    }




}
