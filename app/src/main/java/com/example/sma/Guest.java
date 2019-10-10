package com.example.sma;

public class Guest extends User {


    public Guest (int id, String name, String password){
        setId(id);
        setUsername(name);
        setPassword(password);
    }


    @Override
    public String toString() {
        return "I am a guest user";
    }
}
