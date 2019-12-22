package com.example.sma.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityLogin extends AppCompatActivity {

    EditText emailField, passwordField;
    Button login_btn;
    TextView register;
    ProgressBar progressB;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        progressB = findViewById(R.id.NewReg_progressbar);
        login_btn = findViewById(R.id.login_btn);
        register = findViewById(R.id.register_text);
        firebaseAuth = FirebaseAuth.getInstance();


       login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String emailString = emailField.getText().toString().trim();
                String passwordString = passwordField.getText().toString().trim();


                if (emailString.isEmpty() || passwordString.isEmpty()){
                    Toast.makeText(ActivityLogin.this, "Fill all the fields.", Toast.LENGTH_SHORT).show();


                }

                else if (passwordString.length() < 6) {
                    Toast.makeText(ActivityLogin.this, "Password must be 6 characters", Toast.LENGTH_SHORT).show();
                }


                else {
                firebaseAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(ActivityLogin.this, ActivityProfile.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(ActivityLogin.this, "something went wrong." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        }
                });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, NewRegister.class);
                startActivity(intent);

            }
        });
    }
}