package com.shim.user.shimapplication.data;

import java.util.List;

public class ShowMainResponse {
    private int status;
    private List<Main> arr;

    public ShowMainResponse() {
    }

    public ShowMainResponse(int status, List<Main> arr) {
        this.status = status;
        this.arr = arr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Main> getArr() {
        return arr;
    }

    public void setArr(List<Main> arr) {
        this.arr = arr;
    }
}
