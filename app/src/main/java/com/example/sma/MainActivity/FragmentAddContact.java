package com.example.sma.MainActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.sma.Database.FirebaseControl;
import com.example.sma.Database.SenderCallback;
import com.example.sma.R;




public class FragmentAddContact extends Fragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.main_fragment_4, container, false);

            return view;
        }


        public void contactRequest(){
            FirebaseControl.fc.contactRequest("test123@test123.dk", new SenderCallback() {
                @Override
                public void onSuccess() {
                }
                @Override
                public void onFailure(Exception exception) {

                }
            });
        }
}


