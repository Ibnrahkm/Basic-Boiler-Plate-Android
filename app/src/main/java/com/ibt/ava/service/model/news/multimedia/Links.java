package com.ibt.ava.service.model.news.multimedia;

import com.google.gson.annotations.SerializedName;

   
public class Links {

   @SerializedName("url")
   String url;

   @SerializedName("label")
   String label;

   @SerializedName("active")
   boolean active;


    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    public boolean getActive() {
        return active;
    }
    
}