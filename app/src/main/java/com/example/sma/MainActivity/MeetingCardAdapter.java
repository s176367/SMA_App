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

// @Author Gustav Kristensen s180077
public class MeetingCardAdapter extends RecyclerView.Adapter<MeetingCardAdapter.MeetingViewHolder> {

    // Adapter der viser brugers møder udfra en møde liste.
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
        holder.textViewPeopleCount.setText(String.valueOf(meeting.getAcceptedParticipantSize()));
        holder.textViewDate.setText(String.valueOf(meeting.getDate()));

        // Onclick listener der sender bruger videre til overblikket over det valgte møde.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCtx, ActivityOverview.class);
                intent.putExtra("position", position);
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
}