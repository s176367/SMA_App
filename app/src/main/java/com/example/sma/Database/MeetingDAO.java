package com.example.sma.Database;

import androidx.annotation.NonNull;

import com.example.sma.Model.MeetingObject;
import com.example.sma.Model.Topic;
import com.example.sma.Profile.ActivityProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// Denne klasse anvendes til at lave metoder der skal ændre databasen
public class MeetingDAO extends MeetingObject {




    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ActivityProfile profile = new ActivityProfile();

    public void uploadMeeting(MeetingObject meeting){


        db.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("meetings")
                .add(meeting)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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
