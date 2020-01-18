package com.example.sma.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.Database.FirebaseControl;
import com.example.sma.Database.LocalDatabase;
import com.example.sma.Database.ReceiverCallback;
import com.example.sma.Database.SenderCallback;
//import com.example.sma.Model.Contact;
import com.example.sma.Model.User;
import com.example.sma.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

public class FragmentContacts extends Fragment {

    // Dette fragment bruges til at vise kontakterne vha. contactsadapteren der indsættes i et recyclerview


    RecyclerView recyclerView;
    ContactsAdapter adapter;
    ImageButton butAddContact;
    List<User> contactsList;
    ImageButton refresh;




    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

      View view = inflater.inflate(R.layout.main_fragment_3, container, false);

      refresh  =view.findViewById(R.id.refresh_but);
      refresh.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              refreshContacts();
          }
      });
      butAddContact = view.findViewById(R.id.add_contact);
      butAddContact.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
                contactRequest();
            //½    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new FragmentAddContact()).commit();

          }
      });
      recyclerView = view.findViewById(R.id.recycler_contacts);
      recyclerView.setHasFixedSize(true);
      recyclerView.setNestedScrollingEnabled(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


      contactsList = LocalDatabase.LD.retriveContactList();
      adapter = new ContactsAdapter(getContext(),contactsList);
      recyclerView.setAdapter(adapter);


      return view;


    }


    public void contactRequest(){
        FirebaseControl.fc.contactRequest("test1234@test1234.dk", new SenderCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception exception) {

            }
        });
    }

    public void refreshContacts(){
        LocalDatabase.LD.deleteContactList();
        FirebaseControl.fc.retriveAllContacts(new ReceiverCallback() {
            @Override
            public void onSuccess(Task<DocumentSnapshot> task) {
                contactsList = LocalDatabase.LD.retriveContactList();
                adapter = new ContactsAdapter(getContext(),contactsList);
                recyclerView.setAdapter(adapter);

            }


            @Override
            public void onFailure(Exception exception) {
            }

            @Override
            public void noData() {

            }
        });
    }






}
