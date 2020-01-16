package com.example.sma.CreateMeeting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sma.Database.FirebaseControl;
import com.example.sma.Database.MeetingDAO;
import com.example.sma.Database.LocalDatabase;
import com.example.sma.Database.SenderCallback;
import com.example.sma.MainActivity.ActivityMain;
import com.example.sma.Model.MeetingObject;
import com.example.sma.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FragmentCreateAgenda extends Fragment{
    /*
    Dette fragment anvendes til at vise brugeren den agenda der oprettes,
    og muligheden for at tilføje flere emner til mødet.
     */

    CardView newTopicCard;
    ScrollView scroll;
    Button but_finishAgenda;
    LocalDatabase db = new LocalDatabase();
    private TopicAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private MeetingObject tempMeeting;
    private RecyclerView recyclerView;
    SenderCallback senderCallback;

    MeetingDAO meetingDAO = new MeetingDAO();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.createmeeting_fragment_2, container, false);
        recyclerView = view.findViewById(R.id.recycler_agenda);
        newTopicCard = view.findViewById(R.id.newAgendaCard);
        scroll = view.findViewById(R.id.scrollview);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        tempMeeting = ((ActivityCreateMeeting)getActivity()).getMeeting();
        adapter = new TopicAdapter(view.getContext(), tempMeeting.topics);

        // Tilføjer adapter, som håndtere hvis man vil ændre sit emne.
        recyclerView.setAdapter(adapter);
        but_finishAgenda = view.findViewById(R.id.finish_agenda);


        // Man kan ikke trykke videre, med mindre man har minimum et emne på sit møde
        // Måske skal man have lov til ikke at have nogle emner???
        but_finishAgenda.setVisibility(View.INVISIBLE);

        if (!tempMeeting.topics.isEmpty()){
            but_finishAgenda.setVisibility(View.VISIBLE);
        }

        // Tilføjer mødet med de tilføjede emner til brugerens møder i den lokale database, samt i firestore.
        but_finishAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tempMeeting.topics.isEmpty()) {
                    finishAgenda();




                    Intent intent = new Intent(getContext(), ActivityMain.class);
                    startActivity(intent);
                    getActivity().finish();
               }
                else {
                    Toast.makeText(getContext(), "Insert a topic", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Kort man kan trykke på få at tilføje flere emner.
        newTopicCard.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(container.getId(), new FragmentAddTopic()).addToBackStack(null).commit();
            }
        });

        // Scroller til bunden af ens emner så man nemmere kan tilføje flere hvis det er.

       scroll.post(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });
        return view;
    }


    public void finishAgenda () {
        final FirebaseFirestore dbFB = FirebaseFirestore.getInstance();
        FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        DocumentReference dr = dbFB.collection("users").document(fbAuth.getUid());
        LocalDatabase db = new LocalDatabase();
        db.addMeeting(tempMeeting);
        FirebaseControl.fc.createMeeting(tempMeeting, new SenderCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception exception) {

            }


        });
        Intent intent = new Intent(getContext(), ActivityMain.class);
        startActivity(intent);
        getActivity().finish();
    }

}

