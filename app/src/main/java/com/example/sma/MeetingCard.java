package com.example.sma;

public class MeetingCard {


    private int id;
    private String title;
    private String time;
    private String location;
    private int antalPersoner;


    public MeetingCard(int id,String title, String time, String location, int antalPersoner) {
        this.id = id;
        this.time = time;
        this.location = location;
        this.antalPersoner = antalPersoner;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
