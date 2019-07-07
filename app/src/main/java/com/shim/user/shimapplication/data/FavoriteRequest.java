package com.shim.user.shimapplication.data;

public class FavoriteRequest {
    String user_id;
    int music_id;
    String my;

    public FavoriteRequest() {
    }

    public FavoriteRequest(String user_id, int music_id, String my) {
        this.user_id = user_id;
        this.music_id = music_id;
        this.my = my;
    }

    public FavoriteRequest(String user_id, int music_id, boolean my) {
        this.user_id = user_id;
        this.music_id = music_id;
        this.my = String.valueOf(my);
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getMusic_id() {
        return music_id;
    }

    public void setMusic_id(int music_id) {
        this.music_id = music_id;
    }

    public String getMy() {
        return my;
    }

    public void setMy(String my) {
        this.my = my;
    }
}
