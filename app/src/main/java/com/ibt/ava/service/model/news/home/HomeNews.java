package com.ibt.ava.service.model.news.home;

import com.google.gson.annotations.SerializedName;

   
public class HomeNews {

   @SerializedName("data")
   Data data;


    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }
    
}