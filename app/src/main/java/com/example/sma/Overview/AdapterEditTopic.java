package com.example.sma.Overview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.CreateMeeting.FragmentAddTopic;
import com.example.sma.Model.Topic;
import com.example.sma.R;

import java.util.List;

public class AdapterEditTopic extends RecyclerView.Adapter<AdapterEditTopic.TopicViewHolder> {

    private int meetingPos;
    private Context context;
    private List<Topic> topicList;

    public AdapterEditTopic(Context context, List<Topic> data, int meetingPos) {
        setHasStableIds(true);
        this.context = context;
        this.meetingPos = meetingPos;
        topicList = data;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.model_agenda, null);
        TopicViewHolder holder = new TopicViewHolder(view);
        return holder;
    }



    public void onBindViewHolder(TopicViewHolder holder, final int position) {
        final String title = topicList.get(position).getTopicName();
        final String desc = topicList.get(position).getTopicDescription();
        holder.topicTitle.setText(title);
        holder.topicDesc.setText(desc);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityEditTopic.class);
                intent.putExtra("title", title);
                intent.putExtra("desc",desc);
                intent.putExtra("MeetingPos", meetingPos);
                intent.putExtra("CardPos", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);

    }


    class TopicViewHolder extends RecyclerView.ViewHolder {
        TextView topicTitle, topicDesc;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            topicTitle = itemView.findViewById(R.id.topicTitle);
            topicDesc = itemView.findViewById(R.id.topic_description);
        }
    }
}