package com.example.sma;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Main {

    // Repræsenterer på nuværende tidspunkt databasen for brugere.
    static ArrayList<User> userList = new ArrayList<>();





    public static void main(String[] args) {



        Controller controller = new Controller();
        User user;

        user = controller.createAdmin(0,"Gustav","123");




        userList.add(user);
        System.out.println(FirebaseApp.getInstance("Gus").getName());




        userList.get(0).createMeeting(0, 0,"Møde");
        userList.get(0).addTopic(0,1,"Topic test", "Her ser vi på tingene.");





        userList.get(0).myMeetings();
        System.out.println(userList.get(0).toString());
        System.out.println(userList.get(0).toString());



    }





}