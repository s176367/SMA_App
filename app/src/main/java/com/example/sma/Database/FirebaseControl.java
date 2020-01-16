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
import java.util.HashMap;
import java.util.Map;

public class FirebaseControl implements IFirebaseControl {

     FirebaseFirestore FC = FirebaseFirestore.getInstance();

      public static FirebaseControl fc = new FirebaseControl();



    @Override
    public void createUser(User user, final SenderCallback senderCallback) {
        user.setUserID(FirebaseAuth.getInstance().getUid());
        FC.collection("users").document(user.getUserID()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                senderCallback.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                senderCallback.onFailure(e);
            }
        });
        FC.collection("meetings").document().getId();
    }

    @Override
    public void getUser(String userID, final ReceiverCallback receiverCallback) {
        DocumentReference dr = FC.collection("users").document(userID);
        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot ds = task.getResult();
                    if (ds != null) {
                        receiverCallback.onSuccess(task);

                    }

                    if (ds == null) {
                        receiverCallback.noData();
                    }
                }
                else {
                    receiverCallback.onFailure(task.getException());
                }
            }
        });

    }

    @Override
    public void createMeeting(final MeetingObject meetingObject, final SenderCallback senderCallback) {
        DocumentReference dr = FC.collection("meetings").document();

        FC.collection("meetings").add(meetingObject).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                senderCallback.onSuccess();
                System.out.println(documentReference.getId());
               insertMeetingID(documentReference.getId(), new SenderCallback() {
                   @Override
                   public void onSuccess() {
                   }

                   @Override
                   public void onFailure(Exception exception) {

                   }
               });
            }
        });
    }

    @Override
    public void getMeeting(String meetingID, ReceiverCallback receiverCallback) {

    }

    @Override
    public void insertMeetingID(String meetingID, SenderCallback senderCallback) {
        String userId = LocalDatabase.LD.getUser().getUserID();
        Map<String, Object> meetingIDmap = new HashMap<>();
        meetingIDmap.put("meetingID", meetingID);
        FC.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("meetings").add(meetingIDmap);
    }
}
