package com.shim.user.shimapplication.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Asmr {
    @PrimaryKey
    @SerializedName("sleep_id")
    private int id;
    @SerializedName("sleep_name")
    private String title;
    @SerializedName("sleep_time")
    private int duration;
    @SerializedName("sleep_picture")
    private String thumbnail;
    @SerializedName("sleep_music")
    private String url;

    public Asmr(int id, String title, int duration, String thumbnail, String url) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.thumbnail = thumbnail;
        this.url = url;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
