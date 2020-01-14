package com.example.sma.Database;

import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.example.sma.MainActivity.ActivityMain;
import com.example.sma.Model.Contact;
import com.example.sma.Model.MeetingObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
public class LocalDatabase {

/*
Lokal database der virker vha. shared preferences til at gemme lokalt på brugers mobil.
 */

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public LocalDatabase(){
        prefs = PreferenceManager.getDefaultSharedPreferences(ActivityMain.getContext());
        editor = prefs.edit();
    }
    private Gson gson = new Gson();
    private String json;

    private List<MeetingObject> meetingList = new ArrayList<>();
    private List<Contact> contactList = new ArrayList<>();


    public void addMeeting(MeetingObject meetingObject) {
        json = prefs.getString("Meetings","");
        meetingList = retriveMeetingList();
        meetingList.add(meetingObject);
        json = gson.toJson(meetingList);
        editor.putString("Meetings", json);
        editor.commit();
    }

    public void addMeeting(int pos, MeetingObject meetingObject) {
        json = prefs.getString("Meetings","");
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

    public void updateMeeting(int position, MeetingObject newMeeting){
        deleteMeeting(position);
        addMeeting(position, newMeeting);

    }

    public ArrayList<MeetingObject> retriveMeetingList() {
        json = prefs.getString("Meetings","");
        ArrayList<MeetingObject> returnList;
        if (!json.isEmpty()) {
            Type type = new TypeToken<List<MeetingObject>>() {
            }.getType();
            returnList = gson.fromJson(json, type);
        }
        else returnList = new ArrayList<MeetingObject>();

        return returnList;
    }
}