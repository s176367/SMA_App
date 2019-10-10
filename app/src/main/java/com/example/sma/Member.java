package com.example.sma;

public class Member extends User {

    public Member (int id, String name, String password){
        setId(id);
        setUsername(name);
        setPassword(password);
    }


    @Override
    public String toString() {
        return "I am a member";
    }
}

