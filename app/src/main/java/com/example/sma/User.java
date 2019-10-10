package com.example.sma;

import java.util.ArrayList;

public abstract class User {

    private int id;
    private String username;
    private String password;

    private ArrayList<Meeting> meetingList = new ArrayList<Meeting>();

    public void createMeeting(int id,int userId, String title){
        Meeting meeting = new Meeting(id,userId,title);
        meetingList.add(meeting);
    }


    public void deleteMeeting(int id){
        for (int i = 0; i < meetingList.size(); i++) {
            if (meetingList.get(i).getId() == id){
                meetingList.remove(i);
            }
        }

    }

    public void addTopic(int meetingId, int topicId, String title, String description) {
        meetingList.get(meetingId).addTopic(topicId, title, description);

    }


   public void deleteTopic(int meetingId, int topicId){
        meetingList.get(meetingId).deleteTopic(topicId);
   }


    public String myMeetings(){

        String returnMe = "";
        for (int i = 0; i <meetingList.size() ; i++) {
            returnMe += meetingList.get(i).getTitle() + "\n";
        }

        return returnMe;

    }











    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public abstract String toString();
}
