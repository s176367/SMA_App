package com.example.sma.Model;

import java.util.ArrayList;

public class MeetingObject {
// Model til de atributter en bruger skal kende til sine møder.

    private String id;
    private String title;
    private String time;
    private String location;
    private int antalPersoner;
    private String date;
    private String duration;



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

    public int getAntalPersoner() {
        return antalPersoner;
    }

    public void setAntalPersoner(int antalPersoner) {
        this.antalPersoner = antalPersoner;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }









}
