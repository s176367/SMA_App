package com.example.sma.MainActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.sma.Database.CollectionReceiverCallback;
import com.example.sma.Database.FirebaseControl;
import com.example.sma.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

// @Author Gustav Kristensen s180077
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
            }
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


