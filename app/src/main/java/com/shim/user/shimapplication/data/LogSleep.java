package com.shim.user.shimapplication.data;

public class LogSleep {
    private String sleep_log_user_id;
    private int sleep_log_sleep_id;
    private int sleep_log_action;

    public LogSleep() {
    }

    public LogSleep(String sleep_log_user_id, int sleep_log_sleep_id, int sleep_log_action) {
        this.sleep_log_user_id = sleep_log_user_id;
        this.sleep_log_sleep_id = sleep_log_sleep_id;
        this.sleep_log_action = sleep_log_action;
    }

    public String getSleep_log_user_id() {
        return sleep_log_user_id;
    }

    public void setSleep_log_user_id(String sleep_log_user_id) {
        this.sleep_log_user_id = sleep_log_user_id;
    }

    public int getSleep_log_sleep_id() {
        return sleep_log_sleep_id;
    }

    public void setSleep_log_sleep_id(int sleep_log_sleep_id) {
        this.sleep_log_sleep_id = sleep_log_sleep_id;
    }

    public int getSleep_log_action() {
        return sleep_log_action;
    }

    public void setSleep_log_action(int sleep_log_action) {
        this.sleep_log_action = sleep_log_action;
    }
}
