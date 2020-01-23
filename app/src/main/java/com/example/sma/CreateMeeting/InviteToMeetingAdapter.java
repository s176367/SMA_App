package com.example.sma.CreateMeeting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.Model.User;
import com.example.sma.R;

import java.util.ArrayList;
import java.util.List;
//@Author Mads Geertsen 176367
public class InviteToMeetingAdapter extends RecyclerView.Adapter<InviteToMeetingAdapter.ContactViewHolder> {


    // Denne adapter bruges til at lave views af brugerens kontakter.
    private Context mCtx;
    private List<User> contactList;
    Button but_createMeeting;
   ArrayList<String> contactsToInvite = new ArrayList<>();
    FragmentAddParticipants fragment;

    public InviteToMeetingAdapter(Context mCtx, List<User> contactList, FragmentAddParticipants fragment) {
        this.mCtx = mCtx;
        this.fragment = fragment;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.model_invitecontact, null);
        ContactViewHolder holder = new ContactViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactViewHolder holder, final int position) {
        final User contact = contactList.get(position);
        holder.textViewName.setText(contact.getName());
        holder.textViewMail.setText(contact.getEmail());
        holder.textViewPhone.setText(contact.getPhone());
        holder.textViewCompany.setText(contact.getCompany());
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if (holder.checkBox.isChecked()){
                    holder.checkBox.setChecked(false);
                    fragment.deleteUser(contact.getUserID());

                }
                else {
                    holder.checkBox.setChecked(true);
                    fragment.addUser(contact.getUserID());

                }

            }


        });

    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewMail, textViewPhone, textViewCompany;
        CheckBox checkBox;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.ContactInviteName);
            textViewMail = itemView.findViewById(R.id.ContactInviteEmail);
            textViewPhone = itemView.findViewById(R.id.ContactInvitePhone);
            textViewCompany = itemView.findViewById(R.id.ContactInviteCompany);
            but_createMeeting = itemView.findViewById(R.id.finish_participants);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }




}