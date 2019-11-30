package com.example.sma.FirebaseAndLogin;

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

import com.example.sma.R;
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

public class NewRegister extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText Company, Fullname, Email, Phone, Zipcode, Password;
    TextView LoginText;
    Button buttonRegister;
    ProgressBar progressBar;
    String userId;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_register);

        Company = findViewById(R.id.editTextCompany);
        Fullname = findViewById(R.id.editTextName);
        Email = findViewById(R.id.NewLoginEmail);
        Phone = findViewById(R.id.editTextMobil);
        Zipcode = findViewById(R.id.editTextZipcode);
        Password = findViewById(R.id.NewLoginPassword);
        progressBar = findViewById(R.id.NewReg_progressbar);
        LoginText = findViewById(R.id.NewReg_TextClick);
        buttonRegister = findViewById(R.id.NewLogin_btn);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), NewProfile.class));
            finish();
        }


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String company = Company.getText().toString().trim();
                final String fullname = Fullname.getText().toString().trim();
                final String email = Email.getText().toString().trim();
                final String phone = Phone.getText().toString().trim();
                final String zipcode = Zipcode.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if (!TextUtils.isEmpty(company) && !TextUtils.isEmpty(fullname) && !TextUtils.isEmpty(email)
                        && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(zipcode) && !TextUtils.isEmpty(password)){

                    Toast.makeText(NewRegister.this, "Fill all the fields", Toast.LENGTH_SHORT).show();

                }

                progressBar.setVisibility(View.VISIBLE);

                //User registration in Firebase
                //createUserWithEmailAndPassword is a method in Android-library

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(NewRegister.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userId = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firebaseFirestore.collection("users").document(userId);
                            Map<String,Object> user = new HashMap<>();
                            user.put("company", company);
                            user.put("fname", fullname);
                            user.put("email", email);
                            user.put("phone", phone);
                            user.put("zipcode", zipcode);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "user profile is created for "+ userId);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });

                            Intent intent = new Intent(NewRegister.this, NewProfile.class);
                            startActivity(intent);


                        }else {
                            Toast.makeText(NewRegister.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        LoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewRegister.this, NewLogin.class);
                startActivity(intent);

            }
        });

    }

    }

