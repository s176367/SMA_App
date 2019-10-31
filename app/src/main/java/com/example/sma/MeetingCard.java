package com.example.sma;

import java.util.concurrent.atomic.AtomicInteger;

public class MeetingCard {


    private int id = 0;
    private String title;
    private String time;
    private String location;
    private int antalPersoner;
    private String date;


    public MeetingCard(String title, String time, String location, int peopleCount, String date) {
        id++;
        this.time = time;
        this.location = location;
        this.antalPersoner = peopleCount;
        this.title = title;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
