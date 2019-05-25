package com.shim.user.shimapplication.data;

public class LogMain {
    private String main_log_user_id;
    private int main_log_pic_id;
    private int main_log_action;

    public LogMain() {
    }

    public LogMain(String main_log_user_id, int main_log_pic_id, int main_log_action) {
        this.main_log_user_id = main_log_user_id;
        this.main_log_pic_id = main_log_pic_id;
        this.main_log_action = main_log_action;
    }

    public String getMain_log_user_id() {
        return main_log_user_id;
    }

    public void setMain_log_user_id(String main_log_user_id) {
        this.main_log_user_id = main_log_user_id;
    }

    public int getMain_log_pic_id() {
        return main_log_pic_id;
    }

    public void setMain_log_pic_id(int main_log_pic_id) {
        this.main_log_pic_id = main_log_pic_id;
    }

    public int getMain_log_action() {
        return main_log_action;
    }

    public void setMain_log_action(int main_log_action) {
        this.main_log_action = main_log_action;
    }
}
