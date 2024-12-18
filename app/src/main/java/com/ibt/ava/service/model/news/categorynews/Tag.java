package com.ibt.ava.service.model.news.categorynews;

import com.google.gson.annotations.SerializedName;

   
public class Tag {

   @SerializedName("id")
   int id;

   @SerializedName("name")
   String name;


    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    
}