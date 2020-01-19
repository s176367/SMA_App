package com.example.sma.MainActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.Model.User;
import com.example.sma.R;

import java.util.List;

public class ContactRequestAdapter extends RecyclerView.Adapter<ContactRequestAdapter.ContactViewHolder> {

    // Denne adapter bruges til at lave views af brugerens "venneanmodninger".

    private Context mCtx;
    private List<User> inviteList;



    public ContactRequestAdapter(Context mCtx, List<User> inviteList) {
        this.mCtx = mCtx;
        this.inviteList = inviteList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.model_contact_invite, null);
        ContactViewHolder holder = new ContactViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactViewHolder holder, final int position) {

        User contact = inviteList.get(position);
        holder.textViewName.setText(contact.getName());
        holder.textViewMail.setText(contact.getEmail());
        holder.textViewPhone.setText(contact.getPhone());
        holder.textViewCompany.setText(contact.getCompany());

    }

    @Override
    public int getItemCount() {
        return inviteList.size();
    }


    class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewMail, textViewPhone, textViewCompany;
        Button yes, no;


        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.ContactInviteName);
            textViewMail = itemView.findViewById(R.id.ContactKInviteEmail);
            textViewPhone = itemView.findViewById(R.id.ContactInvitePhone);
            textViewCompany = itemView.findViewById(R.id.ContactInviteCompany);
            yes = itemView.findViewById(R.id.acceptContact_button);
            no = itemView.findViewById(R.id.denyContact_button);
        }
    }



}