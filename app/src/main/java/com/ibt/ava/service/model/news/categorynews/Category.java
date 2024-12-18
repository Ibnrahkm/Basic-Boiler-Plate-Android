package com.ibt.ava.service.model.news.categorynews;

import com.google.gson.annotations.SerializedName;

   
public class Category {

   @SerializedName("id")
   int id;

   @SerializedName("name")
   String name;

   @SerializedName("slug")
   String slug;


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
    
}