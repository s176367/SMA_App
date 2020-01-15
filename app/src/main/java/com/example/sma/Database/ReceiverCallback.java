package com.example.sma.Database;


import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public interface ReceiverCallback {
    void success(Task<DocumentSnapshot> task);
    void failure(Exception exception);
    void noData();
}
