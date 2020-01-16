package com.example.sma.Database;

import androidx.annotation.NonNull;

import com.example.sma.Model.MeetingObject;
import com.example.sma.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseControl implements IFirebaseControl {

     FirebaseFirestore db = FirebaseFirestore.getInstance();

      public static FirebaseControl fc = new FirebaseControl();



    @Override
    public void createUser(User user, final SenderCallback senderCallback) {
        user.setUserID(FirebaseAuth.getInstance().getUid());
        db.collection("users").document(user.getUserID()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                senderCallback.succes();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                senderCallback.failure(e);
            }
        });
    }

    @Override
    public void getUser(String userID, final ReceiverCallback receiverCallback) {
        DocumentReference dr = db.collection("users").document(userID);
        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot ds = task.getResult();
                    if (ds != null) {
                        receiverCallback.success(task);
                    }

                    if (ds == null) {
                        receiverCallback.noData();
                    }
                } else {
                    receiverCallback.failure(task.getException());
                }
            }
        });

    }

    @Override
    public void createMeeting(MeetingObject meetingObject, SenderCallback senderCallback) {

    }

    @Override
    public void getMeeting(String meetingID, ReceiverCallback receiverCallback) {

    }
}
