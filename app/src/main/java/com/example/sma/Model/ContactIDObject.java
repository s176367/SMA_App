package com.example.sma.Model;

public class ContactIDObject {

    String UserId;

    String docID;



    public ContactIDObject(String userId) {
        this.UserId = userId;
    }

    public ContactIDObject( ) {
    }

    public String getUserID() {
        return UserId;
    }

    public void setUserID(String userId) {
        this.UserId = userId;
    }



    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }}