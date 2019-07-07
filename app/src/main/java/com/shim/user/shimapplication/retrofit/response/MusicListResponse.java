package com.shim.user.shimapplication.retrofit.response;

import com.google.gson.annotations.SerializedName;
import com.shim.user.shimapplication.room.Music;

import java.util.List;

public class MusicListResponse {
    private int status;
    @SerializedName("arr")
    private List<Music> data;

    public MusicListResponse(int status, List<Music> data) {
        this.status = status;
        this.data = data;
    }

    public List<Music> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
