package com.example.sma;

import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FakeMeetingDatabase {



    SharedPreferences prefs;
    SharedPreferences.Editor editor;
FakeMeetingDatabase (){



    prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.getContext());
    editor = prefs.edit();
}

    Gson gson = new Gson();
    String json;

    static List<MeetingCard> meetingList = new ArrayList<MeetingCard>();

    public void addMeeting(String title, String time, String location, int peopleCount, String date) {

        json = prefs.getString("Meetings","");
        meetingList = retriveMeetingList();

            MeetingCard mCard = new MeetingCard(title, time, location, peopleCount, date);
            meetingList.add(mCard);
            json = gson.toJson(meetingList);
            editor.putString("Meetings", json);
            editor.commit();
        }




    public void deleteMeeting(String title) {
        meetingList = retriveMeetingList();

        for (int i = 0; i < meetingList.size(); i++) {
            if (meetingList.get(i).getTitle().equals(title)) {
                meetingList.remove(i);
                i--;
            }
        }
            json = gson.toJson(meetingList);
            editor.putString("Meetings", json);
            editor.commit();

    }

    public ArrayList<MeetingCard> retriveMeetingList() {

        json = prefs.getString("Meetings","");
        ArrayList<MeetingCard> returnList = null;
        if (!json.isEmpty()) {
            Type type = new TypeToken<List<MeetingCard>>() {
            }.getType();
            returnList = gson.fromJson(json, type);
        }

        else returnList = new ArrayList<MeetingCard>();
        return returnList;
    }
}