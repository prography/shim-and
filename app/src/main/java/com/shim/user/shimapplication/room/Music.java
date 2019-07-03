package com.shim.user.shimapplication.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Music {
    @PrimaryKey
    private int id;
    private String title;
    private String artist;
    private String category;
    private boolean favorite;
    private String thumbnail;
    private String url;

    public Music(int id, String title, String artist, String category, boolean favorite, String thumbnail, String url) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.category = category;
        this.favorite = favorite;
        this.thumbnail = thumbnail;
        this.url = url;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
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
        return  thumbnail;
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
