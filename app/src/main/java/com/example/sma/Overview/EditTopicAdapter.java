package com.example.sma.Overview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.CreateMeeting.ActivityCreateMeeting;
import com.example.sma.CreateMeeting.FragmentAddTopic;
import com.example.sma.Model.MeetingObject;
import com.example.sma.Model.Topic;
import com.example.sma.R;

import java.util.ArrayList;
import java.util.List;

public class EditTopicAdapter extends RecyclerView.Adapter<EditTopicAdapter.TopicViewHolder> {


    private Context context;
    private List<Topic> topicList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public EditTopicAdapter(Context context, List<Topic> data) {
        this.context = context;
        topicList = data;
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

    public void onBindViewHolder(TopicViewHolder holder, final int position) {

        final String title = topicList.get(position).getTopicName();
        final String desc = topicList.get(position).getTopicDescription();
        holder.topicTitle.setText(title);
        holder.topicDesc.setText(desc);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               MeetingObject tempMeeting =  ((ActivityOverview)context).getMeeting();
               ArrayList<Topic> topicList =tempMeeting.getTopics();


                        topicList.remove(position);
                        FragmentEditTopic tempTopic = new FragmentEditTopic();
                        tempTopic.openTopic(title,desc);
                        ((FragmentActivity)view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,tempTopic).commit();
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return topicList.size();
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