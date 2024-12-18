
package com.ibt.ava.service.model.podcasts;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PodcastList {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("podcasts")
    @Expose
    private List<Podcast> podcasts;
    @SerializedName("channel")
    @Expose
    private Channel channel;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Podcast> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(List<Podcast> podcasts) {
        this.podcasts = podcasts;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

}
