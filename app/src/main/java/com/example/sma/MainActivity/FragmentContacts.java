package com.example.sma.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.Database.FirebaseControl;
import com.example.sma.Database.LocalDatabase;
import com.example.sma.Database.ReceiverCallback;
//import com.example.sma.Model.Contact;
import com.example.sma.Model.User;
import com.example.sma.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FragmentContacts extends Fragment {

    // Dette fragment bruges til at vise kontakterne vha. contactsadapteren der indsættes i et recyclerview


    RecyclerView recyclerViewMeetings;
    RecyclerView recyclerViewRequests;
    ContactsAdapter adapter;
    ContactRequestAdapter requestAdapter;
    ImageButton butAddContact;
    List<User> contactsList;
    List<User> inviteList;
    ImageButton refresh;
    TextView pendingContacts;
    TextView contactsTitle;
    RelativeLayout cardContainer;
    FragmentContacts fragment;
    public static Boolean refreshFragment = true;






    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment_3, container, false);


        fragment = this;
        pendingContacts = view.findViewById(R.id.pendingTitle);
        contactsTitle = view.findViewById(R.id.contactsTitle);
        cardContainer = view.findViewById(R.id.cardContainer);

        refresh = view.findViewById(R.id.refresh_but);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshContacts();

                Handler handler = new Handler();
                Runnable runInvis = new Runnable() {
                    @Override
                    public void run() {
                        refresh.setVisibility(View.INVISIBLE);
                    }
                };
                Runnable runVis = new Runnable() {
                    @Override
                    public void run() {
                        refresh.setVisibility(View.VISIBLE);
                    }
                };
                handler.post(runInvis);
                handler.postDelayed(runVis, 1000);
            }
        });
        butAddContact = view.findViewById(R.id.add_contact);
        butAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityAddNewContact.class);
                startActivity(intent);

            }
        });


        recyclerViewMeetings = view.findViewById(R.id.recycler_contacts);
        recyclerViewMeetings.setHasFixedSize(true);
        recyclerViewMeetings.setNestedScrollingEnabled(true);
        recyclerViewMeetings.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerViewRequests = view.findViewById(R.id.recycler_invites);
        recyclerViewRequests.setHasFixedSize(true);
        recyclerViewRequests.setNestedScrollingEnabled(true);
        recyclerViewRequests.setLayoutManager(new LinearLayoutManager(getContext()));



        contactsList = LocalDatabase.LD.retriveContactList();
        adapter = new ContactsAdapter(getContext(), contactsList);

        requestAdapter = new ContactRequestAdapter(getContext(), inviteList, fragment);
        requestAdapter = new ContactRequestAdapter(getContext(), inviteList, fragment);


        //LocalDatabase.LD.deleteInviteList();
     //   refreshContacts();
        inviteList = LocalDatabase.LD.retriveInviteList();

        cardContainer.removeView(pendingContacts);
        cardContainer.removeView(contactsTitle);

        checkRefresh();




        return view;


    }




    public void refreshContacts() {

        LocalDatabase.LD.deleteContactList();
        LocalDatabase.LD.deleteInviteList();


        FirebaseControl.fc.retriveAllContacts(new ReceiverCallback() {
            @Override
            public void onSuccess(Task<DocumentSnapshot> task) {
                contactsList = LocalDatabase.LD.retriveContactList();
                adapter = new ContactsAdapter(getContext(), contactsList);
                recyclerViewMeetings.setAdapter(adapter);

            }

            @Override
            public void onFailure(Exception exception) {
                System.out.println("failure");
            }

            @Override
            public void noData() {
                System.out.println("no data");

            }
        });

        FirebaseControl.fc.retriveAllInvites(new ReceiverCallback() {
            @Override
            public void onSuccess(Task<DocumentSnapshot> task) {
                inviteList = LocalDatabase.LD.retriveInviteList();
                requestAdapter = new ContactRequestAdapter(getContext(), inviteList, fragment);
                recyclerViewRequests.setAdapter(requestAdapter);
                cardContainer.removeView(pendingContacts);
                cardContainer.removeView(contactsTitle);
                cardContainer.addView(pendingContacts);
                cardContainer.addView(contactsTitle);
            }

            @Override
            public void onFailure(Exception exception) {
            }

            @Override
            public void noData() {
            }
        });

        if (inviteList.isEmpty()) {
            // Skjuler tekst hvis der ikke er nogle contact requests.

            recyclerViewRequests.setAdapter(null);
            cardContainer.removeView(pendingContacts);
            cardContainer.removeView(contactsTitle);

        }
    }


    // https://stackoverflow.com/questions/7876043/android-new-intent-starts-particular-method

    public void checkRefresh() {
      if(refreshFragment){
                Handler handler = new Handler() ;
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        refreshContacts();
                    }
                };
                handler.postDelayed(run,0);
               // refreshFragment = false;
            }
      else{
          contactsList = LocalDatabase.LD.retriveContactList();
          adapter = new ContactsAdapter(getContext(), contactsList);
          requestAdapter = new ContactRequestAdapter(getContext(), inviteList, fragment);
          requestAdapter = new ContactRequestAdapter(getContext(), inviteList, fragment);
          recyclerViewMeetings.setAdapter(adapter);
          recyclerViewRequests.setAdapter(requestAdapter);

      }
        }
    }

