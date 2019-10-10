package com.example.sma;

import java.util.ArrayList;

public class Agenda {

    ArrayList<Topic> topicList = new ArrayList<Topic>();

   public void addTopic(int id, String name, String description){

       Topic topic = new Topic(id,name, description);
       topicList.add(topic);
    };

   public void deleteTopic(int id){
       topicList.remove(id);
   };





























}
