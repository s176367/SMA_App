package com.example.sma.Overview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.Model.User;
import com.example.sma.R;

import java.util.ArrayList;
import java.util.List;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder> {

    // Denne adapter bruges til at lave views af brugerens kontakter.


    private Context mCtx;
    private ArrayList<User> participants;



    public ParticipantAdapter(Context mCtx, ArrayList<User> participants) {
        this.mCtx = mCtx;
        this.participants = participants;
    }

    @NonNull
    @Override
    public ParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.model_participant, null);
        ParticipantViewHolder holder = new ParticipantViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ParticipantViewHolder holder, final int position) {

        User participant = participants.get(position);
        holder.textViewName.setText(participant.getName());
        holder.textViewMail.setText(participant.getEmail());
        holder.textViewPhone.setText(participant.getPhone());
        holder.textViewCompany.setText(participant.getCompany());


    }

    @Override
    public int getItemCount() {
        return participants.size();
    }


    class ParticipantViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewMail, textViewPhone, textViewCompany;


        public ParticipantViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.participantName);
            textViewMail = itemView.findViewById(R.id.participantEmail);
            textViewPhone = itemView.findViewById(R.id.participantPhone);
            textViewCompany = itemView.findViewById(R.id.participantCompany);
        }
    }



}