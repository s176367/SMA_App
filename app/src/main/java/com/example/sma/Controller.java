package com.example.sma;

public class Controller {



    public User createAdmin(int id, String name, String pass){
        User user = new Admin(id,name,pass);
        return user;
    }
    public User createMember(int id, String name, String pass){
        User user = new Member(id,name,pass);
          return user;
    }
    public User createGuest(int id, String name, String pass){
        User user = new Guest(id,name,pass);
        return user;
    }

    public Meeting createMeeting(){
        Meeting meeting = new Meeting(0,0, "Testmøde");
        return meeting;
    }









}
