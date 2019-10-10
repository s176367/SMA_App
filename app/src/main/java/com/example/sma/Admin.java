package com.example.sma;

public class Admin extends User {

    public Admin (int id, String name, String password){
        setId(id);
        setUsername(name);
        setPassword(password);
    }


    @Override
    public String toString() {
        return "I am a administrator";
    }
}
