package com.shim.user.shimapplication.retrofit.request;

import com.google.gson.annotations.SerializedName;

public class FeedbackRequest {
    @SerializedName("feedback_userid")
    private String userId;
    @SerializedName("feedback_contact")
    private String contact;
    @SerializedName("feedback_title")
    private String title;
    @SerializedName("feedback_contents")
    private String content;

    public FeedbackRequest(String userId, String contact, String title, String content) {
        this.userId = userId;
        this.contact = contact;
        this.title = title;
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getUserId() {
        return userId;
    }
}
