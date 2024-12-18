package com.ibt.ava.service.model.news.details;

import com.google.gson.annotations.SerializedName;

   
public class Author {

   @SerializedName("id")
   int id;

   @SerializedName("name")
   String name;

   @SerializedName("slug")
   String slug;

   @SerializedName("avatar")
   String avatar;

   @SerializedName("position")
   String position;

   @SerializedName("bio")
   String bio;


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
    
    public void setSlug(String slug) {
        this.slug = slug;
    }
    public String getSlug() {
        return slug;
    }
    
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getAvatar() {
        return avatar;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    public String getPosition() {
        return position;
    }
    
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getBio() {
        return bio;
    }
    
}