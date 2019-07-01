package com.shim.user.shimapplication.data;

public class Music {
    private int music_id;
    private String music_name;
    private String music_music;
    private String music_author;
    private String music_picture;
    private boolean music_my;


    public Music(int music_id, String music_name, String music_music, String music_author, String music_picture, boolean music_my) {
        this.music_id = music_id;
        this.music_name = music_name;
        this.music_music = music_music;
        this.music_author = music_author;
        this.music_picture = music_picture;
        this.music_my = music_my;
    }

    public Music(int music_id, String music_name, String music_music, String music_author, String music_picture) {
        this.music_id = music_id;
        this.music_name = music_name;
        this.music_music = music_music;
        this.music_author = music_author;
        this.music_picture = music_picture;
        this.music_my = false;
    }

    public Music() {

    }

    public int getMusic_id() {
        return music_id;
    }

    public void setMusic_id(int music_id) {
        this.music_id = music_id;
    }

    public String getMusic_name() {
        return music_name;
    }

    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }

    public String getMusic_music() {
        return music_music;
    }

    public void setMusic_music(String music_music) {
        this.music_music = music_music;
    }

    public String getMusic_picture() {
        return music_picture;
    }

    public void setMusic_picture(String music_picture) {
        this.music_picture = music_picture;
    }

    public boolean isMusic_my() {
        return music_my;
    }

    public void setMusic_my(boolean music_my) {
        this.music_my = music_my;
    }
}

