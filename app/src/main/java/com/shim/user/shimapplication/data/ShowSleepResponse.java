package com.shim.user.shimapplication.data;

import java.util.List;

public class ShowSleepResponse {
    private int status;
    private List<Sleep> arr;

    public ShowSleepResponse() {
    }

    public ShowSleepResponse(int status, List<Sleep> arr) {
        this.status = status;
        this.arr = arr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Sleep> getArr() {
        return arr;
    }

    public void setArr(List<Sleep> arr) {
        this.arr = arr;
    }
}
