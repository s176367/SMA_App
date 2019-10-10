package com.example.sma;

public class Main {


    public static void main(String[] args) {
        Meeting meeting = new Meeting(1, 1,"test", "DTU", 1, 12, 30);
        meeting.agenda.addTopic(0, "Tester", "Her Går det ned");
        meeting.agenda.addTopic(1, "MødeMAasd", "Her Går det godt");
        meeting.agenda.addTopic(2, "Madrads", "Her Går det fremad");


        User Gustav = new Admin(1,"Stavo", "123");
        User Mads = new Guest(2,"MadsG", "321");
        User Sercan = new Member(3, "Sercan", "452");


        for (int i = 0; i < meeting.agenda.topicList.size(); i++) {
            System.out.println(meeting.agenda.topicList.get(i).getId());
            System.out.println(meeting.agenda.topicList.get(i).getName());
            System.out.println(meeting.agenda.topicList.get(i).getDescription() + "\n");
        }



    }
}