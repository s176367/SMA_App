package com.example.sma.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.CreateMeeting.TopicAdapter;
import com.example.sma.Model.Contact;
import com.example.sma.Model.MeetingObject;
import com.example.sma.Overview.ActivityOverview;
import com.example.sma.R;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {

    // Denne adapter bruges til at lave views af brugerens kontakter.


    private Context mCtx;
    private List<Contact> contactList;



    public ContactsAdapter(Context mCtx, List<Contact> contactList) {
        this.mCtx = mCtx;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.model_contact, null);
        ContactViewHolder holder = new ContactViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactViewHolder holder, final int position) {

        final Contact contact = contactList.get(position);
        holder.textViewName.setText(contact.getName());
        holder.textViewMail.setText(contact.getEmail());
        holder.textViewPhone.setText(contact.getPhone());
        holder.textViewCompany.setText(contact.getCompany());




    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }


    class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewMail, textViewPhone, textViewCompany;


        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.ContactName);
            textViewMail = itemView.findViewById(R.id.ContactEmail);
            textViewPhone = itemView.findViewById(R.id.ContactPhone);
            textViewCompany = itemView.findViewById(R.id.ContactCompany);
        }
    }



}