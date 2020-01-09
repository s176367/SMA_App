package com.example.sma.Profile;

import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.example.sma.MainActivity.ActivityMain;
import com.example.sma.Model.MeetingObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
public class FakeMeetingDatabase {



    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
public FakeMeetingDatabase(){
    prefs = PreferenceManager.getDefaultSharedPreferences(ActivityMain.getContext());
    editor = prefs.edit();
}

    private Gson gson = new Gson();
    private String json;

    public static List<MeetingObject> meetingList = new ArrayList<>();

    public void addMeeting(MeetingObject meetingObject) {

        json = prefs.getString("Meetings","");
        meetingList = retriveMeetingList();


            meetingList.add(meetingObject);

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