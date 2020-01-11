package com.example.sma.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewRegisterV2 extends AppCompatActivity {


    public static final String TAG = "TAG";
    private TextInputLayout inputCompany, inputName, inputEmail, inputPhone, inputZipcode, inputPassword;
    private TextInputEditText inputETCompany, inputETName, inputETEmail, inputETPhone, inputETZipcode, inputETPassword;
    TextView loginText;
    Button btn_register;
    ProgressBar progressBar;
    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_register_v2);


        inputCompany = findViewById(R.id.input_company);
        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_email);
        inputPhone = findViewById(R.id.input_phone);
        inputZipcode = findViewById(R.id.input_zipcode);
        inputPassword = findViewById(R.id.input_password);

        loginText = findViewById(R.id.login_text);
        btn_register = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressbar);


        inputETCompany = findViewById(R.id.input_ET_company);
        inputETName = findViewById(R.id.input_ET_name);
        inputETEmail = findViewById(R.id.input_ET_email);
        inputETPhone = findViewById(R.id.input_ET_phone);
        inputETZipcode = findViewById(R.id.input_ET_zipcode);
        inputETPassword = findViewById(R.id.input_ET_password);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), ActivityProfile.class));
            finish();
        }



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit_form();

                final String email = inputETEmail.getText().toString().trim();
                String password = inputETPassword.getText().toString().trim();
                final String fullname = inputETName.getText().toString();
                final String company = inputETCompany.getText().toString();
                final String zipcode = inputETZipcode.getText().toString();
                final String phone = inputETPhone.getText().toString();

                progressBar.setVisibility(View.VISIBLE);

                //registration
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(NewRegisterV2.this, "User created.", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("fname", fullname);
                            user.put("email", email);
                            user.put("phone", phone);
                            user.put("company", company);
                            user.put("zipcode", zipcode);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Log.d(TAG, "onSuccess: user profile is created for " + userID);


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Log.d(TAG, "onFailure: " + e.toString());

                                }
                            });

                            startActivity(new Intent(getApplicationContext(), ActivityProfile.class));

                        } else {

                            Toast.makeText(NewRegisterV2.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }

                    }
                });


            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewRegisterV2.this, ActivityLogin.class);
                startActivity(intent);

            }
        });

    }

    public void requestFocus(View view) {

        if (view.requestFocus()){

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        }
    }

    private boolean validateCompany() {

        if (inputETCompany.getText().toString().trim().isEmpty()) {

            inputCompany.setError("Enter company");
            requestFocus(inputETCompany);
            return false;

        }

        return true;
    }


    private boolean validateName() {

        if (inputETName.getText().toString().trim().isEmpty()) {

            inputName.setError("Enter name");
            requestFocus(inputETName);
            return false;

        }

        return true;

    }

    private boolean validateEmail(){

        if (inputETEmail.getText().toString().trim().isEmpty()) {

            inputEmail.setError("Enter email");
            requestFocus(inputETEmail);
            return false;


        }

        return true;

    }


    private boolean validatePhone() {

        if (inputETPhone.getText().toString().trim().isEmpty()) {

            inputPhone.setError("Enter phone");
            requestFocus(inputETPhone);
            return false;

        } else {

            if (inputETPhone.getText().toString().trim().length() < 8) {

                inputPhone.setError("Invalid number");
                requestFocus(inputETPhone);
                return false;

            }
        }

        return true;

    }


    private boolean validateZipcode() {

        if (inputETZipcode.getText().toString().trim().isEmpty()) {

            inputZipcode.setError("Enter zipcode");
            requestFocus(inputETZipcode);
            return false;

        } else {

            if (inputETPhone.getText().toString().trim().length() > 4) {

                inputPhone.setError("Invalid zipcode");
                requestFocus(inputETZipcode);
                return false;

            }
        }

        return true;

    }


    private boolean validatePassword() {


        if (inputETPassword.getText().toString().trim().isEmpty()) {

            inputPassword.setError("Enter password");
            requestFocus(inputETPassword);
            return false;

        }

        return true;

    }


    private void submit_form() {

        if (!validateCompany() && !validateZipcode() && !validatePassword() && !validatePhone() && !validateEmail() && !validateName()){

            return;

        }

        String userEmail = inputETEmail.getText().toString().trim() + "";
        String userPassword = inputETPassword.getText().toString().trim() + "";


        Toast.makeText(getApplicationContext(), userEmail + "\n" + userPassword, Toast.LENGTH_LONG).show();

    }
}