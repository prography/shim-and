package com.shim.user.shimapplication.data;

public class Video {
    private int video_id;
    private String video_url;
    private String video_title;
    private String video_creator;

    public Video() {
    }

    public Video(int video_id, String video_url, String video_title, String video_creator) {
        this.video_id = video_id;
        this.video_url = video_url;
        this.video_title = video_title;
        this.video_creator = video_creator;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getVideo_creator() {
        return video_creator;
    }

    public void setVideo_creator(String video_creator) {
        this.video_creator = video_creator;
    }
}
