package com.example.sma.Overview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.Model.Topic;
import com.example.sma.R;

import java.util.List;

public class AdapterEditTopic extends RecyclerView.Adapter<AdapterEditTopic.TopicViewHolder> {


    private Context context;
    private List<Topic> topicList;

    public AdapterEditTopic(Context context, List<Topic> data) {
        this.context = context;
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

    // binds the data to the TextView in each row

    public void onBindViewHolder(TopicViewHolder holder, final int position) {

        final String title = topicList.get(position).getTopicName();
        final String desc = topicList.get(position).getTopicDescription();
        holder.topicTitle.setText(title);
        holder.topicDesc.setText(desc);

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