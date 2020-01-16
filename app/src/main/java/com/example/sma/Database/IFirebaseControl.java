package com.example.sma.Database;

import com.example.sma.Model.MeetingObject;
import com.example.sma.Model.User;

public interface IFirebaseControl {



    void createUser (User user,  SenderCallback senderCallback);
    void getUser (String userID,  ReceiverCallback receiverCallback);


    void createMeeting (MeetingObject meetingObject, SenderCallback senderCallback);
    void getMeeting (String meetingID,  ReceiverCallback receiverCallback);
    void insertMeetingID (String meetingID, SenderCallback senderCallback);













}
