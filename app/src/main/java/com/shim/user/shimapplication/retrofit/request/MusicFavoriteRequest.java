package com.shim.user.shimapplication.retrofit.request;

import com.google.gson.annotations.SerializedName;

public class MusicFavoriteRequest {
    @SerializedName("user_id")
    private String userId;
    @SerializedName("music_id")
    private int musicId;
    @SerializedName("my")
    private boolean favorite;

    public MusicFavoriteRequest(String userId, int musicId, boolean favorite) {
        this.userId = userId;
        this.musicId = musicId;
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public int getMusicId() {
        return musicId;
    }

    public String getUserId() {
        return userId;
    }
}
