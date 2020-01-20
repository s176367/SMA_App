package com.example.sma.Profile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityForgotPassword extends AppCompatActivity {


    private Button resetPassword_submitbtn;
    private EditText editTextEmail;
    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_forgotpassword_activity);


        editTextEmail = findViewById(R.id.forgotPassword_email);

        resetPassword_submitbtn = findViewById(R.id.resetPassword_submitbtn);
        resetPassword_submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetPassword();

            }
        });

    }

    private void resetPassword() {

        // https://stackoverflow.com/questions/42800349/forgot-password-in-firebase-for-android

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailaddress = editTextEmail.getText().toString();
        auth.sendPasswordResetEmail(emailaddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(getApplicationContext(), "Check Your Email", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ActivityForgotPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
         });
    }
}




