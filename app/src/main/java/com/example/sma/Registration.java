package com.example.sma;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText Fullname_ET, Phone_ET, Email_ET, Password_ET, ConfirmPassword_ET, Username_ET, Company_ET, Address_ET;
    String userId;
    Button Registerbtn;
    TextView Loginbtn;
    ProgressBar progressB;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        ConfirmPassword_ET = findViewById(R.id.ConfirmPassword);
        Username_ET = findViewById(R.id.Username);
        Company_ET = findViewById(R.id.Company);
        Address_ET = findViewById(R.id.Address);

        Fullname_ET = findViewById(R.id.Name);
        Email_ET = findViewById(R.id.Email);
        Password_ET = findViewById(R.id.Password);
        Phone_ET = findViewById(R.id.Phone);
        Registerbtn = findViewById(R.id.registerBtn);
        Loginbtn = findViewById(R.id.TextClick);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressB = findViewById(R.id.progressBar);

        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Profile.class));
            finish();
        }


        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Email_ET.getText().toString().trim();
                String password = Password_ET.getText().toString().trim();
                final String confirmpassword = ConfirmPassword_ET.getText().toString().trim(); //mangler
                final String fullname = Fullname_ET.getText().toString().trim();
                final String phone = Phone_ET.getText().toString();
                final String username = Username_ET.getText().toString();
                final String company = Company_ET.getText().toString(); //mangler
                final String address = Address_ET.getText().toString(); //mangler


                if(TextUtils.isEmpty(email)){
                    Email_ET.setError("Write your mail");
                    return;
                }

                if (TextUtils.isEmpty(fullname)){
                    Fullname_ET.setError("Write your full name");
                    return;

                }

                if(TextUtils.isEmpty(password)){
                    Password_ET.setError("Write your password");
                    return;
                }

                if(password.length() < 6){
                    Password_ET.setError("Password must be at min. 6 characters ");
                    return;
                }

                if (TextUtils.isEmpty(confirmpassword)){
                    ConfirmPassword_ET.setError("Write your password again");
                    return;
                }

                if (confirmpassword.length() < 6) {
                    ConfirmPassword_ET.setError("Password must be at min. 6 characters");

                }

                if (confirmpassword.length() != password.length()){

                    ConfirmPassword_ET.setError("The password is not the same");
                    return;
                }


                if (TextUtils.isEmpty(username)){
                    Username_ET.setError("Write your username");
                    return;

                }

                progressB.setVisibility(View.VISIBLE);

                //User registration in Firebase
                //createUserWithEmailAndPassword is a method in Android-library

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Registration.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userId = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firebaseFirestore.collection("users").document(userId);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fname", fullname);
                            user.put("email", email);
                            user.put("phone", phone);
                            user.put("username", username);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "user Profile is created for "+ userId);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });

                            Intent intent = new Intent(Registration.this, Profile.class);
                            startActivity(intent);


                        }else {
                            Toast.makeText(Registration.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressB.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);

            }
        });

    }
}