package com.example.sma;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FindEmail extends AppCompatActivity {

    public static final String TAG = "TAG";
    private Button btn_emailCheck;
    private EditText inputEmailCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_email);


        inputEmailCheck = findViewById(R.id.inputCheckEmail);
        btn_emailCheck = findViewById(R.id.btnCheckEmail);
        btn_emailCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String content = inputEmailCheck.getText().toString();
                FirebaseFirestore fStore = FirebaseFirestore.getInstance();
                fStore.collection("users").whereEqualTo("email", content)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                if (document.getString("email").equals(content)) {

                                    Toast.makeText(FindEmail.this, "Email is valid", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "Email is valid.");

                                }
                            }
                        }

                    }
                });

            }

        });
    }
}


