package com.ibt.ava.service.model.news.categorynews;
import java.util.List;

import com.google.gson.annotations.SerializedName;

   
public class CategoryNews {

   @SerializedName("data")
   List<Data> data;

   @SerializedName("links")
   Links links;

   @SerializedName("meta")
   Meta meta;


    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }
    
    public void setLinks(Links links) {
        this.links = links;
    }
    public Links getLinks() {
        return links;
    }
    
    public void setMeta(Meta meta) {
        this.meta = meta;
    }
    public Meta getMeta() {
        return meta;
    }
    
}