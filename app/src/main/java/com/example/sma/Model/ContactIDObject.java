package com.example.sma.Model;

public class ContactIDObject {

    String UserId;

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
}
