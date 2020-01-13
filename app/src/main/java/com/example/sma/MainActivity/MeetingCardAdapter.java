package com.example.sma.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.CreateMeeting.TopicAdapter;
import com.example.sma.Model.MeetingObject;
import com.example.sma.Overview.ActivityOverview;
import com.example.sma.R;

import java.util.List;

public class MeetingCardAdapter extends RecyclerView.Adapter<MeetingCardAdapter.MeetingViewHolder> {


    private Context mCtx;
    private List<MeetingObject> meetingList;
    private TopicAdapter.ItemClickListener mClickListener;


    public MeetingCardAdapter(Context mCtx, List<MeetingObject> meetingList) {
        this.mCtx = mCtx;
        this.meetingList = meetingList;
    }

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.model_meeting, null);
        MeetingViewHolder holder = new MeetingViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MeetingViewHolder holder, final int position) {

        MeetingObject meeting = meetingList.get(position);
        holder.textViewTitle.setText(meeting.getTitle());
        holder.textViewTime.setText(meeting.getTime());
        holder.textViewLocation.setText(meeting.getLocation());
        holder.textViewPeopleCount.setText(String.valueOf(meeting.getAntalPersoner()));
        holder.textViewDate.setText(String.valueOf(meeting.getDate()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCtx, ActivityOverview.class);
                intent.putExtra("position", position);
                System.out.println("card pos" + position);
                mCtx.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }


    class MeetingViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewTime, textViewLocation, textViewPeopleCount,textViewDate;


        public MeetingViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.meetingTitle);
            textViewTime = itemView.findViewById(R.id.meetingTime);
            textViewLocation = itemView.findViewById(R.id.location);
            textViewPeopleCount = itemView.findViewById(R.id.meetingCount);
            textViewDate = itemView.findViewById(R.id.date);
        }
    }




    // allows clicks events to be caught
    void setClickListener(TopicAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}