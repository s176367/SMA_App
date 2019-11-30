package com.example.sma.FirebaseAndLogin;

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

public class NewLogin extends AppCompatActivity {

    EditText Email, Password;
    Button NewLogin_btn;
    TextView TextClick;
    ProgressBar progressB;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);

        Email = findViewById(R.id.NewLoginEmail);
        Password = findViewById(R.id.NewLoginPassword);
        progressB = findViewById(R.id.NewReg_progressbar);
        NewLogin_btn = findViewById(R.id.NewLogin_btn);
        TextClick = findViewById(R.id.NewReg_TextClick);
        firebaseAuth = FirebaseAuth.getInstance();


        NewLogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    Toast.makeText(NewLogin.this, "Fill all the fields.", Toast.LENGTH_SHORT).show();

                }

                if (password.length() < 6) {
                    Toast.makeText(NewLogin.this, "Password must be at min. 6 characters", Toast.LENGTH_SHORT).show();

                }



                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(NewLogin.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(NewLogin.this, NewProfile.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(NewLogin.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        TextClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewLogin.this, NewRegister.class);
                startActivity(intent);

            }
        });
    }
}