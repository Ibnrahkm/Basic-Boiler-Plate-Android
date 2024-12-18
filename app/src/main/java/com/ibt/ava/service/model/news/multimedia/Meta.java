package com.ibt.ava.service.model.news.multimedia;
import java.util.List;

import com.google.gson.annotations.SerializedName;

   
public class Meta {

   @SerializedName("current_page")
   int currentPage;

   @SerializedName("from")
   int from;

   @SerializedName("last_page")
   int lastPage;

   @SerializedName("links")
   List<Links> links;

   @SerializedName("path")
   String path;

   @SerializedName("per_page")
   int perPage;

   @SerializedName("to")
   int to;

   @SerializedName("total")
   int total;


    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    
    public void setFrom(int from) {
        this.from = from;
    }
    public int getFrom() {
        return from;
    }
    
    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }
    public int getLastPage() {
        return lastPage;
    }
    
    public void setLinks(List<Links> links) {
        this.links = links;
    }
    public List<Links> getLinks() {
        return links;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    public String getPath() {
        return path;
    }
    
    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }
    public int getPerPage() {
        return perPage;
    }
    
    public void setTo(int to) {
        this.to = to;
    }
    public int getTo() {
        return to;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }
    public int getTotal() {
        return total;
    }
    
}