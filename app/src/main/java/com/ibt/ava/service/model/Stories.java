/*
 * SPDX-License-Identifier: Apache-2.0
 */

package com.ibt.ava.service.model;

import android.graphics.Bitmap;

import com.ibt.ava.util.Url;

import java.util.ArrayList;
import java.util.List;

import omari.hamza.storyview.model.MyStory;


public final class Stories {


    private String id;


    private String storiesName;


    private Object storiesImage;
    private ArrayList<MyStory> storyLink;

    public void setStoriesImage(Object storiesImage) {
        this.storiesImage = storiesImage;
    }

    public ArrayList<MyStory> getStoryLink() {
        return storyLink;
    }

    public void setStoryLink(ArrayList<MyStory> storyLink) {
        this.storyLink = storyLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoriesName() {
        return storiesName;
    }

    public void setStoriesName(String storiesName) {
        this.storiesName = storiesName;
    }

    public Object getStoriesImage() {
        return storiesImage;
    }

    public void setStoriesImage(String storiesImage) {
        this.storiesImage = storiesImage;
    }

    public Stories(String id, String storiesName, Object storiesImage, ArrayList<MyStory> storyLink) {
        this.id = id;
        this.storiesName = storiesName;
        this.storiesImage = storiesImage;
        this.storyLink = storyLink;
    }
}
