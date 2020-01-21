package com.example.sma.MainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.sma.Database.CollectionReceiverCallback;
import com.example.sma.Database.FirebaseControl;
import com.example.sma.Database.ReceiverCallback;
import com.example.sma.Database.SenderCallback;
import com.example.sma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ActivityAddNewContact extends AppCompatActivity {

    public static final String TAG = "TAG";
    private Button btn_emailCheck;
    private EditText inputEmailCheck;
    TextView notValidEmail;
    LottieAnimationView sendAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_add_new_contact);

        sendAnim = findViewById(R.id.sendContactInviteAnim);
        notValidEmail = findViewById(R.id.emailNotValid);
        inputEmailCheck = findViewById(R.id.emailfield);
        btn_emailCheck = findViewById(R.id.btnCheckEmail);
        btn_emailCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailInput = inputEmailCheck.getText().toString();




                    FirebaseControl.fc.contactRequest(inputEmailCheck.getText().toString(), new CollectionReceiverCallback() {
                        @Override
                        public void onSuccess(Task<QuerySnapshot> task) {
                            sendAnim.setVisibility(View.VISIBLE);
                            Float speed = new Float(3);
                            sendAnim.setSpeed(speed);
                            sendAnim.playAnimation();
                            handler.postDelayed(finish, 750);



                        }

                        @Override
                        public void onFailure(Exception exception) {
                        notValidEmail.setText("Invalid email.");
                        handler.post(show);
                        handler.postDelayed(hide, 1500);
                        }

                        @Override
                        public void noData() {
                            notValidEmail.setText("Email field cannot be empty.");
                            handler.post(show);
                            handler.postDelayed(hide, 1500);

                        }
                    });
                    // Der skal laves en metode til at chekke om emailen eksisterer.


                }


                final String content = inputEmailCheck.getText().toString();
                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        });
    }


    Handler handler = new Handler();
    Runnable show = new Runnable() {
        @Override
        public void run() {
            notValidEmail.setVisibility(View.VISIBLE);
        }
    };

    Runnable hide = new Runnable() {
        @Override
        public void run() {
            notValidEmail.setVisibility(View.INVISIBLE);

        }
    };

    Runnable finish = new Runnable() {
        @Override
        public void run() {
            finish();
        }
    };

}


