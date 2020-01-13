package com.example.sma.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sma.MainActivity.ActivityMain;
import com.example.sma.R;
import com.example.sma.ResetPasswordFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityLogin extends AppCompatActivity {

    EditText emailField, passwordField;
    Button login_btn;
    TextView register, forgotPassword;
    ProgressBar progressB;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_login_activity);
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        login_btn = findViewById(R.id.login_btn);
        register = findViewById(R.id.register_text);
        forgotPassword = findViewById(R.id.forgotPassword);
        firebaseAuth = FirebaseAuth.getInstance();


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Fragment fragment;

                    if (v == findViewById(R.id.forgotPassword)) {

                        fragment = new ResetPasswordFragment();
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_container, fragment);
                        ft.commit();

                    }
                }
        });



        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailString = emailField.getText().toString().trim();
                String passwordString = passwordField.getText().toString().trim();

                if (emailString.isEmpty() || passwordString.isEmpty()) {
                    Toast.makeText(ActivityLogin.this, "Fill all the fields.", Toast.LENGTH_SHORT).show();
                } else if (passwordString.length() < 6) {
                    Toast.makeText(ActivityLogin.this, "Password must be 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
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
                Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
                startActivity(intent);

            }
        });

    }
}