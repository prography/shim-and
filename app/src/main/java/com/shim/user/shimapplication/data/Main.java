package com.shim.user.shimapplication.data;

public class Main {
    int main_id;
    String main_name;
    String main_music;
    String main_author;
    String main_picture;

    public Main() {
    }

    public Main(int main_id, String main_name, String main_music, String main_author, String main_picture) {
        this.main_id = main_id;
        this.main_name = main_name;
        this.main_music = main_music;
        this.main_author = main_author;
        this.main_picture = main_picture;
    }

    public int getMain_id() {
        return main_id;
    }

    public void setMain_id(int main_id) {
        this.main_id = main_id;
    }

    public String getMain_name() {
        return main_name;
    }

    public void setMain_name(String main_name) {
        this.main_name = main_name;
    }

    public String getMain_music() {
        return main_music;
    }

    public void setMain_music(String main_music) {
        this.main_music = main_music;
    }

    public String getMain_author() {
        return main_author;
    }

    public void setMain_author(String main_author) {
        this.main_author = main_author;
    }

    public String getMain_picture() {
        return main_picture;
    }

    public void setMain_picture(String main_picture) {
        this.main_picture = main_picture;
    }
}
