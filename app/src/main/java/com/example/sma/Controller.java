package com.example.sma;

public class Controller {



    public void createAdmin(int id, String name, String pass){
        User user = new Admin(id,name,pass);
        Main.userList.add(user);
    }
    public void createMember(int id, String name, String pass){
        User user = new Member(id,name,pass);
        Main.userList.add(user);
    }
    public void createGuest(int id, String name, String pass){
        User user = new Guest(id,name,pass);
        Main.userList.add(user);
    }










}
