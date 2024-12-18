package com.ibt.ava.service.model.news.home;
import java.util.List;

import com.google.gson.annotations.SerializedName;

   
public class Multimedianews {

   @SerializedName("id")
   int id;

   @SerializedName("title")
   String title;

   @SerializedName("slug")
   String slug;

   @SerializedName("primary_category")
   PrimaryCategory primaryCategory;

   @SerializedName("category")
   List<Category> category;

   @SerializedName("tag")
   List<Tag> tag;

   @SerializedName("author")
   List<Author> author;

   @SerializedName("type")
   String type;

   @SerializedName("summary")
   String summary;

   @SerializedName("featured_image_url")
   String featuredImageUrl;

   @SerializedName("featured_image_alt")
   String featuredImageAlt;

   @SerializedName("featured_image_caption")
   String featuredImageCaption;

   @SerializedName("square_image_url")
   String squareImageUrl;

   @SerializedName("meta_title")
   String metaTitle;

   @SerializedName("meta_image_url")
   String metaImageUrl;

   @SerializedName("kwikmotion_key")
   String kwikmotionKey;

   @SerializedName("youtube_key")
   String youtubeKey;

   @SerializedName("media_gallery")
   List<MediaGallery> mediaGallery;

   @SerializedName("is_active")
   boolean isActive;

   @SerializedName("is_featured")
   boolean isFeatured;

   @SerializedName("is_special_featured")
   boolean isSpecialFeatured;

   @SerializedName("is_breaking_news")
   boolean isBreakingNews;

   @SerializedName("is_breaking_news_slider")
   boolean isBreakingNewsSlider;

   @SerializedName("is_choices_for_you")
   boolean isChoicesForYou;

   @SerializedName("is_latest_news")
   boolean isLatestNews;

   @SerializedName("is_highlighted_breaking_news")
   boolean isHighlightedBreakingNews;

   @SerializedName("is_highlighted_opinion")
   boolean isHighlightedOpinion;

   @SerializedName("is_special_to_ava")
   boolean isSpecialToAva;

   @SerializedName("is_fast_news")
   boolean isFastNews;

   @SerializedName("live_title")
   String liveTitle;

   @SerializedName("published_at")
   String publishedAt;


    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    
    public void setSlug(String slug) {
        this.slug = slug;
    }
    public String getSlug() {
        return slug;
    }
    
    public void setPrimaryCategory(PrimaryCategory primaryCategory) {
        this.primaryCategory = primaryCategory;
    }
    public PrimaryCategory getPrimaryCategory() {
        return primaryCategory;
    }
    
    public void setCategory(List<Category> category) {
        this.category = category;
    }
    public List<Category> getCategory() {
        return category;
    }
    
    public void setTag(List<Tag> tag) {
        this.tag = tag;
    }
    public List<Tag> getTag() {
        return tag;
    }
    
    public void setAuthor(List<Author> author) {
        this.author = author;
    }
    public List<Author> getAuthor() {
        return author;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getSummary() {
        return summary;
    }
    
    public void setFeaturedImageUrl(String featuredImageUrl) {
        this.featuredImageUrl = featuredImageUrl;
    }
    public String getFeaturedImageUrl() {
        return featuredImageUrl;
    }
    
    public void setFeaturedImageAlt(String featuredImageAlt) {
        this.featuredImageAlt = featuredImageAlt;
    }
    public String getFeaturedImageAlt() {
        return featuredImageAlt;
    }
    
    public void setFeaturedImageCaption(String featuredImageCaption) {
        this.featuredImageCaption = featuredImageCaption;
    }
    public String getFeaturedImageCaption() {
        return featuredImageCaption;
    }
    
    public void setSquareImageUrl(String squareImageUrl) {
        this.squareImageUrl = squareImageUrl;
    }
    public String getSquareImageUrl() {
        return squareImageUrl;
    }
    
    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }
    public String getMetaTitle() {
        return metaTitle;
    }
    
    public void setMetaImageUrl(String metaImageUrl) {
        this.metaImageUrl = metaImageUrl;
    }
    public String getMetaImageUrl() {
        return metaImageUrl;
    }
    
    public void setKwikmotionKey(String kwikmotionKey) {
        this.kwikmotionKey = kwikmotionKey;
    }
    public String getKwikmotionKey() {
        return kwikmotionKey;
    }
    
    public void setYoutubeKey(String youtubeKey) {
        this.youtubeKey = youtubeKey;
    }
    public String getYoutubeKey() {
        return youtubeKey;
    }
    
    public void setMediaGallery(List<MediaGallery> mediaGallery) {
        this.mediaGallery = mediaGallery;
    }
    public List<MediaGallery> getMediaGallery() {
        return mediaGallery;
    }
    
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    public boolean getIsActive() {
        return isActive;
    }
    
    public void setIsFeatured(boolean isFeatured) {
        this.isFeatured = isFeatured;
    }
    public boolean getIsFeatured() {
        return isFeatured;
    }
    
    public void setIsSpecialFeatured(boolean isSpecialFeatured) {
        this.isSpecialFeatured = isSpecialFeatured;
    }
    public boolean getIsSpecialFeatured() {
        return isSpecialFeatured;
    }
    
    public void setIsBreakingNews(boolean isBreakingNews) {
        this.isBreakingNews = isBreakingNews;
    }
    public boolean getIsBreakingNews() {
        return isBreakingNews;
    }
    
    public void setIsBreakingNewsSlider(boolean isBreakingNewsSlider) {
        this.isBreakingNewsSlider = isBreakingNewsSlider;
    }
    public boolean getIsBreakingNewsSlider() {
        return isBreakingNewsSlider;
    }
    
    public void setIsChoicesForYou(boolean isChoicesForYou) {
        this.isChoicesForYou = isChoicesForYou;
    }
    public boolean getIsChoicesForYou() {
        return isChoicesForYou;
    }
    
    public void setIsLatestNews(boolean isLatestNews) {
        this.isLatestNews = isLatestNews;
    }
    public boolean getIsLatestNews() {
        return isLatestNews;
    }
    
    public void setIsHighlightedBreakingNews(boolean isHighlightedBreakingNews) {
        this.isHighlightedBreakingNews = isHighlightedBreakingNews;
    }
    public boolean getIsHighlightedBreakingNews() {
        return isHighlightedBreakingNews;
    }
    
    public void setIsHighlightedOpinion(boolean isHighlightedOpinion) {
        this.isHighlightedOpinion = isHighlightedOpinion;
    }
    public boolean getIsHighlightedOpinion() {
        return isHighlightedOpinion;
    }
    
    public void setIsSpecialToAva(boolean isSpecialToAva) {
        this.isSpecialToAva = isSpecialToAva;
    }
    public boolean getIsSpecialToAva() {
        return isSpecialToAva;
    }
    
    public void setIsFastNews(boolean isFastNews) {
        this.isFastNews = isFastNews;
    }
    public boolean getIsFastNews() {
        return isFastNews;
    }
    
    public void setLiveTitle(String liveTitle) {
        this.liveTitle = liveTitle;
    }
    public String getLiveTitle() {
        return liveTitle;
    }
    
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
    public String getPublishedAt() {
        return publishedAt;
    }
    
}