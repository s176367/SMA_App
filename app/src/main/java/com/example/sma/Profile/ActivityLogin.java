package com.example.sma.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sma.Database.FirebaseControl;
import com.example.sma.Database.LocalDatabase;
import com.example.sma.Database.ReceiverCallback;
import com.example.sma.MainActivity.ActivityMain;
import com.example.sma.Model.User;
import com.example.sma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

public class ActivityLogin extends AppCompatActivity {

    private TextInputLayout email_input, password_input;
    private TextInputEditText email_ET, password_ET;
    Button login_btn;
    TextView register, forgotPassword;
    ProgressBar progressB;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_login_activity);

        email_input = findViewById(R.id.email_input);
        password_input = findViewById(R.id.password_input);

        email_ET = findViewById(R.id.email_ET);
        password_ET = findViewById(R.id.password_ET);

        login_btn = findViewById(R.id.login_btn);
        register = findViewById(R.id.register_text);
        forgotPassword = findViewById(R.id.forgotPassword);
        firebaseAuth = FirebaseAuth.getInstance();


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(ActivityLogin.this, ActivityForgotPassword.class);
               startActivity(intent);

            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email_ET.getText().toString();
                String passwordString = password_ET.getText().toString();

                if (emailString.isEmpty() || passwordString.isEmpty()) {
                    Toast.makeText(ActivityLogin.this, "Fill all the fields.", Toast.LENGTH_SHORT).show();
                }
                else if (passwordString.length() < 6) {
                    Toast.makeText(ActivityLogin.this, "Password must be 6 characters", Toast.LENGTH_SHORT).show();
                }
                else {
                    signIn(emailString,passwordString);
                }
            }
        });


            }


            private void signIn(String email, String pass){
                final FirebaseAuth fAuth = FirebaseAuth.getInstance();

                fAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseControl.fc.getUser(firebaseAuth.getCurrentUser().getUid(), new ReceiverCallback() {
                                        @Override
                                        public void success(Task<DocumentSnapshot> task) {
                                            User user = task.getResult().toObject(User.class);
                                            LocalDatabase.LD.setUser(user);
                                            Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
                                            startActivity(intent);
                                            finish();
                                            System.out.println("hej");
                                        }
                                        @Override
                                        public void failure(Exception exception) {
                                            System.out.println(exception);
                                        }

                                        @Override
                                        public void noData() {
                                            System.out.println("no data");;
                                        }
                                    });
                                } else {
                                    Toast.makeText(ActivityLogin.this, "something went wrong.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                ;
            }
        }