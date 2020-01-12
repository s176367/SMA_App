package com.example.sma.Database;

import com.example.sma.Model.MeetingObject;
import com.google.firebase.firestore.FirebaseFirestore;

public class MeetingDTO {

    MeetingObject meeting = new MeetingObject();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

}
