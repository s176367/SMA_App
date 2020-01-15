package com.example.sma.Model;

public class User {

    private String name;
    private String company;
    private String email;
    private String phoneNr;
    private String userID;

    public User(){};


    public User(String name, String company, String email, String phoneNr) {
        this.name = name;
        this.company = company;
        this.email = email;
        this.phoneNr = phoneNr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }













}
