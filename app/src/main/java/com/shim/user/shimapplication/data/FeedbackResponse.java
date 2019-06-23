package com.shim.user.shimapplication.data;

public class FeedbackResponse {
    private int status;
    private String msg;

    public FeedbackResponse() {
    }

    public FeedbackResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
