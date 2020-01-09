package com.example.sma.MainActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.Model.Contact;
import com.example.sma.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentContacts extends Fragment {




    RecyclerView recyclerView;
    ContactsAdapter adapter;



    List<Contact> contactList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      View view = inflater.inflate(R.layout.main_fragment_3, container, false);
      contactList = new ArrayList<>();


      // Falske kontakter, skal slettes når database kommer op og køre.
      Contact c1 = new Contact("Mas", "Mas@123.dk", "Bing", "12341234");
      Contact c2 = new Contact("Gus", "gus@123.dk", "Google", "12341234");
      Contact c3 = new Contact("Ser", "Ser@123.dk", "Yahoo", "12341234");
      Contact c4 = new Contact("Moh", "Moh@123.dk", "Nokia", "12341234");
      contactList.add(c1);
      contactList.add(c2);
      contactList.add(c3);
      contactList.add(c4);

      recyclerView = view.findViewById(R.id.recycler_contacts);
      recyclerView.setHasFixedSize(true);
      recyclerView.setNestedScrollingEnabled(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
      adapter = new ContactsAdapter(getContext(), contactList);
      recyclerView.setAdapter(adapter);


      return view;


    }






}
