package com.example.sma.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sma.MainActivity.ActivityMain;
import com.example.sma.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class ActivityProfile extends AppCompatActivity {

    TextView Fullname, Email, Phone;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);


        Fullname = findViewById(R.id.NewProfileName);
        Email = findViewById(R.id.NewProfileEmail);
        Phone = findViewById(R.id.NewProfilePhone);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();


        DocumentReference documentReference = firestore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Fullname.setText(documentSnapshot.getString("fname"));
                Email.setText(documentSnapshot.getString("email"));
                Phone.setText(documentSnapshot.getString("phone"));
            }
        });
    }

    public void logout(View v) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), ActivityLogin.class));
        finish();
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityMain.class);
        startActivity(intent);
    }
}
