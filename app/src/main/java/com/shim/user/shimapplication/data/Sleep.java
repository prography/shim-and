package com.shim.user.shimapplication.data;

public class Sleep {
    private int sleep_id;
    private String sleep_music;
    private String sleep_name;

    public Sleep() {
    }

    public Sleep(int sleep_id, String sleep_music, String sleep_name) {
        this.sleep_id = sleep_id;
        this.sleep_music = sleep_music;
        this.sleep_name = sleep_name;
    }

    public int getSleep_id() {
        return sleep_id;
    }

    public void setSleep_id(int sleep_id) {
        this.sleep_id = sleep_id;
    }

    public String getSleep_music() {
        return sleep_music;
    }

    public void setSleep_music(String sleep_music) {
        this.sleep_music = sleep_music;
    }

    public String getSleep_name() {
        return sleep_name;
    }

    public void setSleep_name(String sleep_name) {
        this.sleep_name = sleep_name;
    }
}