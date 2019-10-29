package com.example.sma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingViewHolder> {


    private Context mCtx;
    private List<MeetingCard> meetingList;

    public MeetingAdapter(Context mCtx, List<MeetingCard> meetingList) {
        this.mCtx = mCtx;
        this.meetingList = meetingList;
    }

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.meeting_layout, null);
        MeetingViewHolder holder = new MeetingViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {

        MeetingCard meeting = meetingList.get(position);
        holder.textViewTitle.setText(meeting.getTitle());
        holder.textViewTime.setText(meeting.getTime());
        holder.textViewLocation.setText(meeting.getLocation());
        holder.textViewPeopleCount.setText(String.valueOf(meeting.getAntalPersoner()));
    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }

    class MeetingViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewTitle, textViewTime, textViewLocation, textViewPeopleCount;


        public MeetingViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.meetingTitle);
            textViewTime = itemView.findViewById(R.id.meetingTime);
            textViewLocation = itemView.findViewById(R.id.meetingLocation);
            textViewPeopleCount = itemView.findViewById(R.id.meetingCount);
        }
    }
}