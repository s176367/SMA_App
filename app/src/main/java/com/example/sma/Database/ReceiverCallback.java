package com.example.sma.Database;


import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public interface ReceiverCallback {
    void onSuccess(Task<DocumentSnapshot> task);
    void onFailure(Exception exception);
    void noData();
}
