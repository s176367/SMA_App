package com.example.sma.Overview;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sma.Database.CollectionReceiverCallback;
import com.example.sma.Database.FirebaseControl;
import com.example.sma.Database.LocalDatabase;
import com.example.sma.MainActivity.MeetingCardAdapter;
import com.example.sma.Model.MeetingObject;
import com.example.sma.Model.User;
import com.example.sma.R;
import com.example.sma.RefreshContext;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FragmentParticipants extends Fragment {


    String TAG = this.getClass().getName();
    RecyclerView participantsView;
    ArrayList<String> participantStringList;
    ArrayList<User> participantUserList;
    MeetingObject meeting;
    int position;
    ParticipantAdapter adapter;
    SwipeRefreshLayout swipe;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.overview_fragment_3,container,false);

        position = ((ActivityOverview)getActivity()).getPosition();
        meeting = LocalDatabase.LD.retriveMeetingList().get(position);
        swipe = view.findViewById(R.id.swipeParticipants);
        participantStringList = meeting.getAcceptedParticipants();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });


        refresh();


        participantsView = view.findViewById(R.id.recycler_participants);
        participantsView.setHasFixedSize(true);
        participantsView.setNestedScrollingEnabled(true);
        participantsView.setLayoutManager(new LinearLayoutManager(getContext()));




        return view;
     }


     public void refresh(){
         swipe.setRefreshing(true);
         FirebaseControl.fc.retriveAllAcceptedParticipants(participantStringList, new CollectionReceiverCallback() {
             @Override
             public void onSuccess(Task<QuerySnapshot> task) {
                 Log.d(TAG, "onSuccess: participants received");


                 Handler handler = new Handler();
                 Runnable run = new Runnable() {
                     @Override
                     public void run() {
                         participantUserList = LocalDatabase.LD.retriveParticipantList();
                         adapter = new ParticipantAdapter(getContext(), participantUserList);
                         participantsView.setAdapter(adapter);
                         swipe.setRefreshing(false);
                     }
                 };
                 // Lappe løsning fordi  der er timing issues og vi har ikke tid til at ændre :(
                 handler.postDelayed(run, 500);
             }

             @Override
             public void onFailure(Exception exception) {
                 Log.d(TAG, "onFailure: " + exception);
                 swipe.setRefreshing(false);

             }

             @Override
             public void noData() {
                 Log.d(TAG, "noData");
                 swipe.setRefreshing(false);
             }
         });

     }
}
