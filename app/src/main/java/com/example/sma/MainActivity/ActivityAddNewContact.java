package com.example.sma.MainActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sma.Database.FirebaseControl;
import com.example.sma.Database.SenderCallback;
import com.example.sma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ActivityAddNewContact extends AppCompatActivity {

    public static final String TAG = "TAG";
    private Button btn_emailCheck;
    private EditText inputEmailCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_add_new_contact);


        inputEmailCheck = findViewById(R.id.emailfield);
        btn_emailCheck = findViewById(R.id.btnCheckEmail);
        btn_emailCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = inputEmailCheck.getText().toString();

                if (!emailInput.isEmpty()){
                contactRequest(inputEmailCheck.getText().toString());

                // Der skal laves en metode til at chekke om emailen eksisterer.
                    FragmentContacts.refreshFragment = true;
                   finish();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Email field cannot be empty", Toast.LENGTH_SHORT).show();
                }


                final String content = inputEmailCheck.getText().toString();
                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                rootRef.collection("users").whereEqualTo("email", content).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                if (document.getString("email").equals(content)) {

                                    Toast.makeText(ActivityAddNewContact.this, "Invitation send", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "Successfull", task.getException());

                                } else {

                                    Toast.makeText(ActivityAddNewContact.this, "Invitation isn't send", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "Not successfull");

                                }
                            }

                        }
                    }

                });


                }
         });
    }


    public void contactRequest(String email){
        FirebaseControl.fc.contactRequest(email, new SenderCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception exception) {

            }
        });
    }
}


