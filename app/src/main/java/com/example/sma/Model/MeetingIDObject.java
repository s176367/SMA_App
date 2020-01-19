package com.example.sma.Model;

public class MeetingIDObject {

   private String meetingID;
   private String docID;


    public MeetingIDObject(String meetingID, String docID) {
        this.meetingID = meetingID;
        this.docID = docID;
    }

    public MeetingIDObject(String meetingID) {
        this.meetingID = meetingID;
    }

    public MeetingIDObject(){

   }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(String meetingID) {
        this.meetingID = meetingID;
    }
}
