
package com.ibt.ava.service.model.search;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Link {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("active")
    @Expose
    private Boolean active;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}
