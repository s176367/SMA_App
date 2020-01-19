package com.example.sma.Model;

public class ContactInvite {


    String userID;
    String docID;



    public ContactInvite(String userID) {
        this.userID = userID;
    }

    public ContactInvite() {
    };

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }
}
