
package com.ibt.ava.service.model.podcastchannels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("channel_category_id")
    @Expose
    private Integer channelCategoryId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("is_public")
    @Expose
    private Boolean isPublic;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("featured_image_url")
    @Expose
    private String featuredImageUrl;
    @SerializedName("featured_image_alt")
    @Expose
    private String featuredImageAlt;
    @SerializedName("featured_image_caption")
    @Expose
    private Object featuredImageCaption;
    @SerializedName("podcast_count")
    @Expose
    private Integer podcastCount;
    @SerializedName("channel_category")
    @Expose
    private ChannelCategory channelCategory;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChannelCategoryId() {
        return channelCategoryId;
    }

    public void setChannelCategoryId(Integer channelCategoryId) {
        this.channelCategoryId = channelCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getFeaturedImageUrl() {
        return featuredImageUrl;
    }

    public void setFeaturedImageUrl(String featuredImageUrl) {
        this.featuredImageUrl = featuredImageUrl;
    }

    public String getFeaturedImageAlt() {
        return featuredImageAlt;
    }

    public void setFeaturedImageAlt(String featuredImageAlt) {
        this.featuredImageAlt = featuredImageAlt;
    }

    public Object getFeaturedImageCaption() {
        return featuredImageCaption;
    }

    public void setFeaturedImageCaption(Object featuredImageCaption) {
        this.featuredImageCaption = featuredImageCaption;
    }

    public Integer getPodcastCount() {
        return podcastCount;
    }

    public void setPodcastCount(Integer podcastCount) {
        this.podcastCount = podcastCount;
    }

    public ChannelCategory getChannelCategory() {
        return channelCategory;
    }

    public void setChannelCategory(ChannelCategory channelCategory) {
        this.channelCategory = channelCategory;
    }

}
