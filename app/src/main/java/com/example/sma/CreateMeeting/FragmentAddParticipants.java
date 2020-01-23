package com.example.sma.CreateMeeting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.Database.CollectionReceiverCallback;
import com.example.sma.Database.FirebaseControl;
import com.example.sma.Database.LocalDatabase;
//import com.example.sma.Model.Contact;
import com.example.sma.Database.ReceiverCallback;
import com.example.sma.Database.SenderCallback;
import com.example.sma.MainActivity.ActivityMain;
import com.example.sma.Model.MeetingObject;
import com.example.sma.Model.User;
import com.example.sma.R;
import com.example.sma.RefreshContext;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


// @Author Mads Geertsen s176367
public class FragmentAddParticipants extends Fragment {
    // Dette fragment bruges til at vise kontakterne vha. contactsadapteren der indsættes i et recyclerview
    RecyclerView recyclerViewContacts;
    InviteToMeetingAdapter adapter;
    List<User> contactsList;
    String TAG = getTag();
    MeetingObject tempMeeting;
    Button but_createMeeting;
    FragmentAddParticipants adapterFragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.createmeeting_fragment_4, container, false);

        adapterFragment = this;
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



        refresh();


        // Skjuler tekst hvis der ikke er nogle contact requests.



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

    public void refresh(){
        FirebaseControl.fc.retriveAllContacts(new CollectionReceiverCallback() {
            @Override
            public void onSuccess(Task<QuerySnapshot> task) {
                contactsList = LocalDatabase.LD.retriveContactList();
                adapter = new InviteToMeetingAdapter(getContext(), contactsList, adapterFragment);
                recyclerViewContacts.setAdapter(adapter);
            }

            @Override
            public void onFailure(Exception exception) {
                Log.d(TAG, "onFailure: " + exception);

            }

            @Override
            public void noData() {
                Log.d(TAG, "noData");

            }
        });
    }






}
