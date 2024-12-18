package com.ibt.ava.service.model.news.multimedia;

import com.google.gson.annotations.SerializedName;

   
public class MediaGallery {

   @SerializedName("url")
   String url;

   @SerializedName("title")
   String title;

   @SerializedName("description")
   String description;


    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    
}