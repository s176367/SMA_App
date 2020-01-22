package com.example.sma.Model;

import java.util.ArrayList;

public class MeetingObject {
// Model til de atributter en bruger skal kende til sine møder.

    private String id;
    private String title;
    private String time;
    private String location;
    private String date;
    private String duration;
    private ArrayList<String> participants = new ArrayList<>();
    public ArrayList<String> acceptedParticipants = new ArrayList<>();

    public ArrayList<String> getAcceptedParticipants() {
        return acceptedParticipants;
    }

    public int getAcceptedParticipantSize(){
       return getAcceptedParticipants().size();
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }



    public void addParticipant(String userID) {
        participants.add(userID);
    }
    public void deleteParticipant(String userID){
        for (int i = 0; i < participants.size() ; i++) {
            if (participants.get(i).equals(userID)){
                participants.remove(i);
            }
        }
    }

    public ArrayList<Topic> topics = new ArrayList<>();

    public ArrayList<Topic> getTopics() {
        return topics;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setTopics(ArrayList<Topic> topics) {
        this.topics = topics;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
