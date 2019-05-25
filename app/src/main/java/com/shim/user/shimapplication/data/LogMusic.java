package com.shim.user.shimapplication.data;

public class LogMusic {
    private String music_log_user_id;
    private int music_log_music_id;
    private int music_log_action;

    public LogMusic() {
    }

    public LogMusic(String music_log_user_id, int music_log_music_id, int music_log_action) {
        this.music_log_user_id = music_log_user_id;
        this.music_log_music_id = music_log_music_id;
        this.music_log_action = music_log_action;
    }

    public String getMusic_log_user_id() {
        return music_log_user_id;
    }

    public void setMusic_log_user_id(String music_log_user_id) {
        this.music_log_user_id = music_log_user_id;
    }

    public int getMusic_log_music_id() {
        return music_log_music_id;
    }

    public void setMusic_log_music_id(int music_log_music_id) {
        this.music_log_music_id = music_log_music_id;
    }

    public int getMusic_log_action() {
        return music_log_action;
    }

    public void setMusic_log_action(int music_log_action) {
        this.music_log_action = music_log_action;
    }
}
