package com.shim.user.shimapplication.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Music {
    @PrimaryKey
    @SerializedName(value = "music_id", alternate = {"main_id"})
    private int id;
    @SerializedName(value = "music_name", alternate = {"main_name"})
    private String title;
    @SerializedName(value = "music_author", alternate = {"main_author"})
    private String artist;
    @SerializedName(value = "music_category", alternate = {"main_category"})
    private String category;
    @SerializedName("my")
    private boolean favorite;
    @SerializedName("music_msec")
    private int duration;
    @SerializedName(value = "music_picture", alternate = {"main_picture"})
    private String thumbnail;
    @SerializedName(value = "music_music", alternate = {"main_music"})
    private String url;

    public Music(int id, String title, String artist, String category, boolean favorite, int duration, String thumbnail, String url) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.category = category;
        this.favorite = favorite;
        this.duration = duration;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    public static class Builder {
        private int id = 0;
        private String title = "Untitled";
        private String artist = "Unknown";
        private String category = "unknown";
        private boolean favorite = false;
        private int duration = 0;
        private String thumbnail = null;
        private String url = null;

        public Builder() {
        }

        public Music build() {
            return new Music(id, title, artist, category, favorite, duration, thumbnail, url);
        }

        public Builder setArtist(String artist) {
            this.artist = artist;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder setFavorite(boolean favorite) {
            this.favorite = favorite;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }
    }
}
