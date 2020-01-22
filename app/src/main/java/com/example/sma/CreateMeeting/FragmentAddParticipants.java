package com.example.sma.CreateMeeting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.Database.FirebaseControl;
import com.example.sma.Database.LocalDatabase;
//import com.example.sma.Model.Contact;
import com.example.sma.Database.SenderCallback;
import com.example.sma.MainActivity.ActivityMain;
import com.example.sma.Model.MeetingObject;
import com.example.sma.Model.User;
import com.example.sma.R;
import com.example.sma.RefreshContext;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class FragmentAddParticipants extends Fragment {

    // Dette fragment bruges til at vise kontakterne vha. contactsadapteren der indsættes i et recyclerview


    RecyclerView recyclerViewContacts;
    InviteToMeetingAdapter adapter;
    List<User> contactsList;
    MeetingObject tempMeeting;
    Button but_createMeeting;





    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.createmeeting_fragment_4, container, false);

        tempMeeting = ((ActivityCreateMeeting)getActivity()).getMeeting();
        recyclerViewContacts = view.findViewById(R.id.recycler_participants);
        recyclerViewContacts.setHasFixedSize(true);
        recyclerViewContacts.setNestedScrollingEnabled(true);
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        but_createMeeting = view.findViewById(R.id.finish_participants);
        but_createMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAgenda();
            }
        });

        contactsList = LocalDatabase.LD.retriveContactList();
        adapter = new InviteToMeetingAdapter(getContext(), contactsList, this);


        // Skjuler tekst hvis der ikke er nogle contact requests.

        recyclerViewContacts.setAdapter(adapter);

        return view;
    }

    public void addUser(String userId){
        tempMeeting.addParticipant(userId);
    }

    public void deleteUser(String userId) {
        tempMeeting.deleteParticipant(userId);
    }


    public void finishAgenda () {




            FirebaseControl.fc.createMeeting(tempMeeting, new SenderCallback() {
            @Override
            public void onSuccess() {

                RefreshContext.setHome(true);
                Intent intent = new Intent(getContext(), ActivityMain.class);
                startActivity(intent);

                getActivity().finish();
            }
            @Override
            public void onFailure(Exception exception) {


            }
        });

    }






}
