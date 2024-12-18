package com.ibt.ava.service.model.news.home;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.ibt.ava.service.model.news.podcast.Podcast;


public class Data {

   @SerializedName("breakingnews")
   List<Featurednews> breakingnews;

   @SerializedName("fastnews")
   List<Featurednews> fastnews;

   @SerializedName("featurednews")
   List<Featurednews> featurednews;
    @SerializedName("mainnews")
    List<Featurednews> mainnews;

   @SerializedName("breakingnewsslider")
   List<Breakingnewsslider> breakingnewsslider;

   @SerializedName("choicenews")
   List<Choicenews> choicenews;

   @SerializedName("lastestnews")
   List<Featurednews> lastestnews;

   @SerializedName("reportnews")
   List<Featurednews> reportnews;

   @SerializedName("businessnews")
   List<Featurednews> businessnews;

   @SerializedName("culturenews")
   List<Featurednews> culturenews;

   @SerializedName("environmentnews")
   List<Featurednews> environmentnews;

   @SerializedName("healthnews")
   List<Featurednews> healthnews;

   @SerializedName("scoop")
   List<Featurednews> scoop;

   @SerializedName("kurdistannews")
   List<Featurednews> kurdistannews;

   @SerializedName("iraqnews")
   List<Featurednews> iraqnews;

   @SerializedName("worldnews")
   List<Featurednews> worldnews;

   @SerializedName("opinionnews")
   List<Featurednews> opinionnews;

   @SerializedName("multimedianews")
   List<Featurednews> multimedianews;

   @SerializedName("exchange")
   List<Exchange> exchange;

   @SerializedName("podcasts")
   List<Podcast> podcasts;

    public List<Featurednews> getMainnews() {
        return mainnews;
    }

    public void setMainnews(List<Featurednews> mainnews) {
        this.mainnews = mainnews;
    }

    public void setBreakingnews(List<Featurednews> breakingnews) {
        this.breakingnews = breakingnews;
    }
    public List<Featurednews> getBreakingnews() {
        return breakingnews;
    }
    
    public void setFastnews(List<Featurednews> fastnews) {
        this.fastnews = fastnews;
    }
    public List<Featurednews> getFastnews() {
        return fastnews;
    }
    
    public void setFeaturednews(List<Featurednews> featurednews) {
        this.featurednews = featurednews;
    }
    public List<Featurednews> getFeaturednews() {
        return featurednews;
    }
    
    public void setBreakingnewsslider(List<Breakingnewsslider> breakingnewsslider) {
        this.breakingnewsslider = breakingnewsslider;
    }
    public List<Breakingnewsslider> getBreakingnewsslider() {
        return breakingnewsslider;
    }
    
    public void setChoicenews(List<Choicenews> choicenews) {
        this.choicenews = choicenews;
    }
    public List<Choicenews> getChoicenews() {
        return choicenews;
    }
    
    public void setLastestnews(List<Featurednews> lastestnews) {
        this.lastestnews = lastestnews;
    }
    public List<Featurednews> getLastestnews() {
        return lastestnews;
    }
    
    public void setReportnews(List<Featurednews> reportnews) {
        this.reportnews = reportnews;
    }
    public List<Featurednews> getReportnews() {
        return reportnews;
    }
    
    public void setBusinessnews(List<Featurednews> businessnews) {
        this.businessnews = businessnews;
    }
    public List<Featurednews> getBusinessnews() {
        return businessnews;
    }
    
    public void setCulturenews(List<Featurednews> culturenews) {
        this.culturenews = culturenews;
    }
    public List<Featurednews> getCulturenews() {
        return culturenews;
    }
    
    public void setEnvironmentnews(List<Featurednews> environmentnews) {
        this.environmentnews = environmentnews;
    }
    public List<Featurednews> getEnvironmentnews() {
        return environmentnews;
    }
    
    public void setHealthnews(List<Featurednews> healthnews) {
        this.healthnews = healthnews;
    }
    public List<Featurednews> getHealthnews() {
        return healthnews;
    }
    
    public void setScoop(List<Featurednews> scoop) {
        this.scoop = scoop;
    }
    public List<Featurednews> getScoop() {
        return scoop;
    }
    
    public void setKurdistannews(List<Featurednews> kurdistannews) {
        this.kurdistannews = kurdistannews;
    }
    public List<Featurednews> getKurdistannews() {
        return kurdistannews;
    }
    
    public void setIraqnews(List<Featurednews> iraqnews) {
        this.iraqnews = iraqnews;
    }
    public List<Featurednews> getIraqnews() {
        return iraqnews;
    }
    
    public void setWorldnews(List<Featurednews> worldnews) {
        this.worldnews = worldnews;
    }
    public List<Featurednews> getWorldnews() {
        return worldnews;
    }
    
    public void setOpinionnews(List<Featurednews> opinionnews) {
        this.opinionnews = opinionnews;
    }
    public List<Featurednews> getOpinionnews() {
        return opinionnews;
    }
    
    public void setMultimedianews(List<Featurednews> multimedianews) {
        this.multimedianews = multimedianews;
    }
    public List<Featurednews> getMultimedianews() {
        return multimedianews;
    }
    
    public void setExchange(List<Exchange> exchange) {
        this.exchange = exchange;
    }
    public List<Exchange> getExchange() {
        return exchange;
    }
    
    public void setPodcasts(List<Podcast> podcasts) {
        this.podcasts = podcasts;
    }
    public List<Podcast> getPodcasts() {
        return podcasts;
    }
    
}