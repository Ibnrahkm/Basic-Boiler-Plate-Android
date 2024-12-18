package com.ibt.ava.service.model.podcasts;

import java.util.ArrayList;

public class PodcastArrayData {
    private ArrayList<Podcast> podcast;
    private Channel channel;

    public ArrayList<Podcast> getPodcast() {
        return podcast;
    }

    public void setPodcast(ArrayList<Podcast> podcast) {
        this.podcast = podcast;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public PodcastArrayData(ArrayList<Podcast> podcast, Channel channel) {
        this.podcast = podcast;
        this.channel = channel;
    }
}
