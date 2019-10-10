package com.example.sma;

import java.util.ArrayList;

public abstract class User {

    private int id;
    private String username;
    private String password;


    private ArrayList<Meeting> meetingList = new ArrayList<Meeting>();















    public void createMeeting(int id,int userId, String title, String location, int date, int time, int duration){
        Meeting meeting = new Meeting(id,userId,title,location,date,time,duration);
        meetingList.add(meeting);
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
