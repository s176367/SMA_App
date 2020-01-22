package com.example.sma.MainActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sma.Database.FirebaseControl;
import com.example.sma.Database.LocalDatabase;
import com.example.sma.Database.SenderCallback;
import com.example.sma.Model.User;
import com.example.sma.R;
import com.google.firebase.auth.FirebaseAuth;
import java.util.List;

// @Author Gutav Kristensen s180077
public class ContactRequestAdapter extends RecyclerView.Adapter<ContactRequestAdapter.ContactViewHolder> {

    // Denne adapter bruges til at lave views af brugerens "venneanmodninger".
    private Context mCtx;
    private List<User> inviteList;
    private boolean refresh;
    private  FragmentContacts fragment;

    public ContactRequestAdapter(Context mCtx, List<User> inviteList, FragmentContacts fragment ) {
        this.mCtx = mCtx;
        this.inviteList = inviteList;
        this.fragment = fragment;

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

        holder.yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senderID = FirebaseAuth.getInstance().getUid();
                String receiverID = LocalDatabase.LD.retriveContactInviteList().get(position).getUserID();
                FirebaseControl.fc.acceptContactRequest(senderID, receiverID, new SenderCallback() {
                    @Override
                    public void onSuccess() {
                        removeAt(position);
                        fragment.refreshContacts();
                    }
                    @Override
                    public void onFailure(Exception exception) {
                    }
                });
            }
        });
        holder.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseControl.fc.deleteContactRequest(LocalDatabase.LD.retriveContactInviteList().get(position).getUserID(), new SenderCallback() {

                    @Override
                    public void onSuccess() {
                        removeAt(position);
                        fragment.refreshContacts();
                    }

                    @Override
                    public void onFailure(Exception exception) {

                    }
                });
            }
        });
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

    // https://stackoverflow.com/questions/26076965/android-recyclerview-addition-removal-of-items

    // Metode til at slette
    public void removeAt(int position) {
        inviteList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, inviteList.size());
    }
}