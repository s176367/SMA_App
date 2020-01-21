package com.example.sma.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sma.Database.CollectionReceiverCallback;
import com.example.sma.Database.FirebaseControl;
import com.example.sma.Database.LocalDatabase;
import com.example.sma.Database.ReceiverCallback;
//import com.example.sma.Model.Contact;
import com.example.sma.Model.User;
import com.example.sma.R;
import com.example.sma.RefreshContext;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FragmentContacts extends Fragment {

    // Dette fragment bruges til at vise kontakterne vha. contactsadapteren der indsættes i et recyclerview

    String TAG = this.getClass().getName();
    RecyclerView recyclerViewMeetings;
    RecyclerView recyclerViewRequests;
    ContactsAdapter adapter;
    ContactRequestAdapter requestAdapter;
    ImageButton butAddContact;
    List<User> contactsList;
    List<User> inviteList;
    SwipeRefreshLayout refreshSwipe;
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
        refreshSwipe = view.findViewById(R.id.swipeContacts);
        refreshSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContacts();
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

        FirebaseControl.fc.retriveAllContacts(new CollectionReceiverCallback() {
            @Override
            public void onSuccess(Task<QuerySnapshot> task) {
               contactsList = LocalDatabase.LD.retriveContactList();
                adapter = new ContactsAdapter(getContext(), contactsList);
                recyclerViewMeetings.setAdapter(adapter);
                refreshSwipe.setRefreshing(false);
                Log.d(TAG, "Refreshed all contacts");
                }

            @Override
            public void onFailure(Exception exception) {
                refreshSwipe.setRefreshing(false);
                Log.d(TAG, "Failed to retrieve contacts" + exception);
            }

            @Override
            public void noData() {
                refreshSwipe.setRefreshing(false);
                Log.d(TAG, "There is no contacts");
            }
        });

        FirebaseControl.fc.retriveAllInvites(new CollectionReceiverCallback() {
            @Override
            public void onSuccess(Task<QuerySnapshot> task) {
                inviteList = LocalDatabase.LD.retriveInviteList();
                requestAdapter = new ContactRequestAdapter(getContext(), inviteList, fragment);
                recyclerViewRequests.setAdapter(requestAdapter);
                cardContainer.removeView(pendingContacts);
                cardContainer.removeView(contactsTitle);
                cardContainer.addView(pendingContacts);
                cardContainer.addView(contactsTitle);
                refreshSwipe.setRefreshing(false);
            }

            @Override
            public void onFailure(Exception exception) {
                refreshSwipe.setRefreshing(false);
            }

            @Override
            public void noData() {
                refreshSwipe.setRefreshing(false);
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
      if(RefreshContext.getContacts()){
          refreshSwipe.setRefreshing(true);
                Handler handler = new Handler() ;
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        refreshContacts();
                    }
                };
                handler.postDelayed(run,400);
                RefreshContext.setContacts(false);
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

