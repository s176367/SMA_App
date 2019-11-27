package com.example.sma;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText ET_Email, ET_Password;
    Button Btn_Login;
    TextView Btn_Create;
    ProgressBar progressB;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ET_Email = findViewById(R.id.Phone);
        ET_Password = findViewById(R.id.Email);
        progressB = findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        Btn_Login = findViewById(R.id.loginBtn);
        Btn_Create = findViewById(R.id.TextClick);

        Btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = ET_Email.getText().toString().trim();
                String password = ET_Password.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    ET_Email.setError("Write your email");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    ET_Password.setError("Write your password");
                    return;
                }

                if(password.length() < 6){
                    ET_Password.setError("Password must be at min. 6 characters");
                    return;
                }

                progressB.setVisibility(View.VISIBLE);


                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Login.this, Profile.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(Login.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressB.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });



        Btn_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);

            }
        });


    }
}
