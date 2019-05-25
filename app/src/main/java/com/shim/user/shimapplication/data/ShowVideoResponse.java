package com.shim.user.shimapplication.data;

import java.util.List;

public class ShowVideoResponse {
    private int status;
    private List<Video> arr;

    public ShowVideoResponse() {
    }

    public ShowVideoResponse(int status, List<Video> arr) {
        this.status = status;
        this.arr = arr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Video> getArr() {
        return arr;
    }

    public void setArr(List<Video> arr) {
        this.arr = arr;
    }
}
