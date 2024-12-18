
package com.ibt.ava.service.model.podcasts;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Podcast {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("channel_id")
    @Expose
    private Integer channelId;
    @SerializedName("host_ids")
    @Expose
    private List<String> hostIds;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("slug")
    @Expose
    private String slug;
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
    private Object featuredImageAlt;
    @SerializedName("featured_image_caption")
    @Expose
    private Object featuredImageCaption;
    @SerializedName("meta_title")
    @Expose
    private Object metaTitle;
    @SerializedName("meta_image_url")
    @Expose
    private Object metaImageUrl;
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("video_url")
    @Expose
    private Object videoUrl;
    @SerializedName("kwikmotion_key")
    @Expose
    private Object kwikmotionKey;
    @SerializedName("youtube_key")
    @Expose
    private Object youtubeKey;
    @SerializedName("custom_video_url")
    @Expose
    private Object customVideoUrl;
    @SerializedName("media_duration")
    @Expose
    private String mediaDuration;
    @SerializedName("audio_url")
    @Expose
    private String audioUrl;
    @SerializedName("audio_caption")
    @Expose
    private Object audioCaption;
    @SerializedName("audio_duration")
    @Expose
    private Object audioDuration;
    @SerializedName("is_live")
    @Expose
    private Boolean isLive;
    @SerializedName("is_featured")
    @Expose
    private Boolean isFeatured;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public List<String> getHostIds() {
        return hostIds;
    }

    public void setHostIds(List<String> hostIds) {
        this.hostIds = hostIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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

    public Object getFeaturedImageAlt() {
        return featuredImageAlt;
    }

    public void setFeaturedImageAlt(Object featuredImageAlt) {
        this.featuredImageAlt = featuredImageAlt;
    }

    public Object getFeaturedImageCaption() {
        return featuredImageCaption;
    }

    public void setFeaturedImageCaption(Object featuredImageCaption) {
        this.featuredImageCaption = featuredImageCaption;
    }

    public Object getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(Object metaTitle) {
        this.metaTitle = metaTitle;
    }

    public Object getMetaImageUrl() {
        return metaImageUrl;
    }

    public void setMetaImageUrl(Object metaImageUrl) {
        this.metaImageUrl = metaImageUrl;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Object getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(Object videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Object getKwikmotionKey() {
        return kwikmotionKey;
    }

    public void setKwikmotionKey(Object kwikmotionKey) {
        this.kwikmotionKey = kwikmotionKey;
    }

    public Object getYoutubeKey() {
        return youtubeKey;
    }

    public void setYoutubeKey(Object youtubeKey) {
        this.youtubeKey = youtubeKey;
    }

    public Object getCustomVideoUrl() {
        return customVideoUrl;
    }

    public void setCustomVideoUrl(Object customVideoUrl) {
        this.customVideoUrl = customVideoUrl;
    }

    public String getMediaDuration() {
        return mediaDuration;
    }

    public void setMediaDuration(String mediaDuration) {
        this.mediaDuration = mediaDuration;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public Object getAudioCaption() {
        return audioCaption;
    }

    public void setAudioCaption(Object audioCaption) {
        this.audioCaption = audioCaption;
    }

    public Object getAudioDuration() {
        return audioDuration;
    }

    public void setAudioDuration(Object audioDuration) {
        this.audioDuration = audioDuration;
    }

    public Boolean getIsLive() {
        return isLive;
    }

    public void setIsLive(Boolean isLive) {
        this.isLive = isLive;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

}
