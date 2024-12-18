
package com.ibt.ava.service.model.search;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SearchResult {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
