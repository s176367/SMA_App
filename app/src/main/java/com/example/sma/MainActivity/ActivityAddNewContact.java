package com.example.sma.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sma.Database.FirebaseControl;
import com.example.sma.Database.SenderCallback;
import com.example.sma.R;

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


