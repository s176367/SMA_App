package com.example.sma.CreateMeeting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sma.Model.Topic;
import com.example.sma.R;
import java.util.List;

// @Author Gutav Kristensen s180077
public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    private Context context;
    private List<Topic> topicList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public TopicAdapter(Context context, List<Topic> data) {
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

    // indsætter de rigtige titler og beskrivelser og laver onclick listner

    public void onBindViewHolder(TopicViewHolder holder, final int position) {

        final String title = topicList.get(position).getTopicName();
        final String desc = topicList.get(position).getTopicDescription();
        holder.topicTitle.setText(title);
        holder.topicDesc.setText(desc);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentAddTopic tempTopic = new FragmentAddTopic(title,desc,position);
                ((FragmentActivity)view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        tempTopic).addToBackStack(null).commit();
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