package com.example.sma.MainActivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.Database.FirebaseControl;
import com.example.sma.Database.SenderCallback;
import com.example.sma.Model.MeetingObject;
import com.example.sma.Model.User;
import com.example.sma.R;
import com.example.sma.RefreshContext;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MeetingInviteAdapter extends RecyclerView.Adapter<MeetingInviteAdapter.ContactViewHolder> {

    // Denne adapter bruges til at lave views af brugerens kontakter.

    private String TAG = this.getClass().getName();
    private Context mCtx;
    private List<MeetingObject> inviteList;



    public MeetingInviteAdapter(Context mCtx, List<MeetingObject> inviteList) {
        this.mCtx = mCtx;
        this.inviteList = inviteList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.model_invite, null);
        ContactViewHolder holder = new ContactViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactViewHolder holder, final int position) {

        holder.textViewTitle.setText(inviteList.get(position).getTitle());
        holder.textViewDate.setText(inviteList.get(position).getDate());
        holder.textViewTime.setText(inviteList.get(position).getTime());
        holder.textViewLocation.setText(inviteList.get(position).getLocation());
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RefreshContext.setHome(true);
                RefreshContext.setInvites(true);
                FirebaseControl.fc.acceptMeetingRequest(inviteList.get(position).getId(), new SenderCallback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Meeting has been accepted");
                    }

                    @Override
                    public void onFailure(Exception exception) {
                    Log.d(TAG, "Meeting has not been accepted, something went wrong: " +exception );
                    }
                });
                removeAt(position);
            }
        });

        holder.deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseControl.fc.deleteMeetingRequest(inviteList.get(position).getId(), new SenderCallback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Meeting has been rejected");
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        Log.d(TAG, "Meeting rejection failed");
                    }
                });
                removeAt(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return inviteList.size();
    }


    class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewDate, textViewTime, textViewLocation;
        ImageButton accept, deny;


        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.meeting_invite_name);
            textViewDate = itemView.findViewById(R.id.meeting_invite_date);
            textViewTime = itemView.findViewById(R.id.meeting_invite_time);
            textViewLocation = itemView.findViewById(R.id.meeting_invite_location);
            accept = itemView.findViewById(R.id.accept_btn_invite);
            deny = itemView.findViewById(R.id.decline_btn_invite);
        }
    }

    public void removeAt(int position) {
        inviteList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, inviteList.size());

    }





}