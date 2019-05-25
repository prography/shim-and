package com.shim.user.shimapplication.data;

public class LogVideo {
    private String video_log_user_id;
    private int video_log_video_id;
    private int video_log_action;

    public LogVideo() {
    }

    public LogVideo(String video_log_user_id, int video_log_video_id, int video_log_action) {
        this.video_log_user_id = video_log_user_id;
        this.video_log_video_id = video_log_video_id;
        this.video_log_action = video_log_action;
    }

    public String getVideo_log_user_id() {
        return video_log_user_id;
    }

    public void setVideo_log_user_id(String video_log_user_id) {
        this.video_log_user_id = video_log_user_id;
    }

    public int getVideo_log_video_id() {
        return video_log_video_id;
    }

    public void setVideo_log_video_id(int video_log_video_id) {
        this.video_log_video_id = video_log_video_id;
    }

    public int getVideo_log_action() {
        return video_log_action;
    }

    public void setVideo_log_action(int video_log_action) {
        this.video_log_action = video_log_action;
    }
}
