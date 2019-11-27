package com.example.sma.CreateMeeting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.MainActivity.MeetingAdapter;
import com.example.sma.Model.MeetingObject;
import com.example.sma.Model.Topic;
import com.example.sma.Overview.ActivityOverview;
import com.example.sma.R;

import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {


    private Context context;
    private List<Topic> testArray;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public TopicAdapter(Context context, List<Topic> data) {
        this.context = context;
        testArray = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.template_agenda, null);
        TopicViewHolder holder = new TopicViewHolder(view);
        return holder;
    }

    // binds the data to the TextView in each row

    public void onBindViewHolder(TopicViewHolder holder, int position) {

        String title = testArray.get(position).getTopicName();
        String desc = testArray.get(position).getTopicDescription();
        holder.topicTitle.setText(title);
        holder.topicDesc.setText(desc);


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return testArray.size();
    }



    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
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