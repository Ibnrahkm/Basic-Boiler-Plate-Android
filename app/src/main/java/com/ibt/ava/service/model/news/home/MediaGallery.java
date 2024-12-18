package com.ibt.ava.service.model.news.home;

import com.google.gson.annotations.SerializedName;


public class MediaGallery {

    @SerializedName("url")
    String url;

    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}