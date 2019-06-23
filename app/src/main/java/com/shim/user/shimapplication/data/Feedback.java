package com.shim.user.shimapplication.data;

public class Feedback {
    private String feedback_userid;
    private String feedback_contact;
    private String feedback_title;
    private String feedback_contents;

    public Feedback() {
    }

    public Feedback(String feedback_userid, String feedback_contact, String feedback_title, String feedback_contents) {
        this.feedback_userid = feedback_userid;
        this.feedback_contact = feedback_contact;
        this.feedback_title = feedback_title;
        this.feedback_contents = feedback_contents;
    }

    public String getFeedback_userid() {
        return feedback_userid;
    }

    public void setFeedback_userid(String feedback_userid) {
        this.feedback_userid = feedback_userid;
    }

    public String getFeedback_contact() {
        return feedback_contact;
    }

    public void setFeedback_contact(String feedback_contact) {
        this.feedback_contact = feedback_contact;
    }

    public String getFeedback_title() {
        return feedback_title;
    }

    public void setFeedback_title(String feedback_title) {
        this.feedback_title = feedback_title;
    }

    public String getFeedback_contents() {
        return feedback_contents;
    }

    public void setFeedback_contents(String feedback_contents) {
        this.feedback_contents = feedback_contents;
    }
}
