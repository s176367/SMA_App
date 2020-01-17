package com.example.sma.Database;

import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.example.sma.MainActivity.ActivityMain;
import com.example.sma.Model.Contact;
import com.example.sma.Model.MeetingObject;
import com.example.sma.Model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LocalDatabase {

/*
Lokal database der virker vha. shared preferences til at gemme lokalt på brugers mobil.
 */

    public static LocalDatabase LD = new LocalDatabase();


    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public LocalDatabase() {
        prefs = PreferenceManager.getDefaultSharedPreferences(ActivityMain.getContext());
        editor = prefs.edit();
    }

    private Gson gson = new Gson();
    private String json;

    private List<MeetingObject> meetingList = new ArrayList<>();
    private List<Contact> contactList = new ArrayList<>();
    private static User user = new User();


    public void addMeeting(MeetingObject meetingObject) {
        json = prefs.getString("Meetings", "");
        meetingList = retriveMeetingList();
        meetingList.add(meetingObject);
        json = gson.toJson(meetingList);
        editor.putString("Meetings", json);
        editor.commit();
    }
    // Bruges for at opdatere topic
    public void addMeeting(int pos, MeetingObject meetingObject) {
        json = prefs.getString("Meetings", "");
        meetingList = retriveMeetingList();
        meetingList.add(pos, meetingObject);
        json = gson.toJson(meetingList);
        editor.putString("Meetings", json);
        editor.commit();
    }

    public void deleteMeeting(int position) {
        meetingList = retriveMeetingList();
        meetingList.remove(position);
        json = gson.toJson(meetingList);
        editor.putString("Meetings", json);
        editor.commit();
    }


    public User getUser() {
        json = prefs.getString("User", "");
        if (!json.isEmpty()) {
            user = gson.fromJson(json, User.class);
            return user;
        } else return new User();
    }

    public void setUser(User user) {
        this.user = user;
        json = gson.toJson(user);
        editor.putString("User", json);
        editor.commit();
        this.user = user;
    }

    public void updateMeeting(int position, MeetingObject newMeeting) {
        deleteMeeting(position);
        addMeeting(position, newMeeting);

    }

    public ArrayList<MeetingObject> retriveMeetingList() {
        json = prefs.getString("Meetings", "");
        ArrayList<MeetingObject> returnList;
        if (!json.isEmpty()) {
            Type type = new TypeToken<List<MeetingObject>>() {
            }.getType();
            returnList = gson.fromJson(json, type);
        } else returnList = new ArrayList<MeetingObject>();

        return returnList;
    }

    public void deleteMeetingList() {
        editor.remove("Meetings").commit();


    }
}