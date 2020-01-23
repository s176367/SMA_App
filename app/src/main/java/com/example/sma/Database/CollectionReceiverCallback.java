package com.example.sma.Database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
// @Author Gustav Kristensen s180077
public interface CollectionReceiverCallback {
        void onSuccess(Task<QuerySnapshot> task);
        void onFailure(Exception exception);
        void noData();
}
