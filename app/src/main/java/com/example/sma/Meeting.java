package com.example.sma;


import java.util.ArrayList;

public class Meeting {


    public Meeting(int id,int creatorId, String title) {
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
    }


    private int id;
    private String title;
    private int creatorId;
    ArrayList<Topic> agenda = new ArrayList<>();
    ArrayList<User> participantList = new ArrayList<>();






    public void addParticipant(int userId){
        participantList.add(Main.userList.get(userId));
    }

    public void deleteParticipant(int userId){
        participantList.remove(Main.userList.get(userId));
    }


    public void addTopic(int id, String name, String description){
        Topic topic = new Topic(id,name, description);
        agenda.add(topic);
    };

    public void deleteTopic(int id){
        agenda.remove(id);}








    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
