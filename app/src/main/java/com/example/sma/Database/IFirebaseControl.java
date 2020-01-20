package com.example.sma.Database;

import com.example.sma.Model.MeetingObject;
import com.example.sma.Model.User;

import java.util.ArrayList;

public interface IFirebaseControl {



    void createUser (User user,  SenderCallback senderCallback);
    void createMeeting (MeetingObject meetingObject, SenderCallback senderCallback);

    void getUser (String userID,  ReceiverCallback receiverCallback);
    void getMeeting(String MeetingId, ReceiverCallback receiverCallback);
    void getInvite (String inviteUserID, ReceiverCallback receiverCallback);
    void getMeetingInvite(String meetingID, ReceiverCallback receiverCallback);



    void deleteMeeting(String MeetindId, SenderCallback senderCallback);

    void insertMeetingID (String meetingID, SenderCallback senderCallback);
    void inviteParticipant (String userID, String meetingId, SenderCallback senderCallback);

    void contactRequest (String UserID, SenderCallback senderCallback);


    void retriveAllContacts(CollectionReceiverCallback receiverCallback);
    void retrieveAllMeetings(CollectionReceiverCallback receiverCallback);
    void retriveAllInvites(CollectionReceiverCallback receiverCallback);
    void retrieveAllMeetingInvites(CollectionReceiverCallback receiverCallback);


    void acceptMeetingRequest(String meetingID, SenderCallback senderCallback);
    void deleteMeetingRequest(String meetingID, SenderCallback senderCallback);



    void acceptContactRequest(String senderID, String ReceiverID, SenderCallback senderCallback);
    void deleteContactRequest (String ReceiverID, SenderCallback senderCallback);












}
