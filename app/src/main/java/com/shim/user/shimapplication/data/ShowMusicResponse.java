package com.shim.user.shimapplication.data;

import java.util.List;

public class ShowMusicResponse {
    private int status;
    private List<Music> arr;

    public ShowMusicResponse() {
    }

    public ShowMusicResponse(int status, List<Music> arr) {
        this.status = status;
        this.arr = arr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Music> getArr() {
        return arr;
    }

    public void setArr(List<Music> arr) {
        this.arr = arr;
    }
}
