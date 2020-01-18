package com.example.sma.Database;

import com.example.sma.Model.MeetingObject;
import com.example.sma.Model.User;

public interface IFirebaseControl {



    void createUser (User user,  SenderCallback senderCallback);
    void getUser (String userID,  ReceiverCallback receiverCallback);


    void createMeeting (MeetingObject meetingObject, SenderCallback senderCallback);
    void getAllMeetings (ReceiverCallback receiverCallback);
    void getMeeting(String MeetingId, ReceiverCallback receiverCallback);
    void deleteMeeting(String MeetindId, SenderCallback senderCallback);
    void insertMeetingID (String meetingID, SenderCallback senderCallback);
    void contactRequest (String UserID, SenderCallback senderCallback);
    void retriveAllContacts(ReceiverCallback receiverCallback);














}
