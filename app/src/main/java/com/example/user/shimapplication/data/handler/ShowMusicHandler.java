package com.example.user.shimapplication.data.handler;

import com.example.user.shimapplication.data.Music;

import java.util.List;

public interface ShowMusicHandler extends BaseHandler {
    void onSuccessShowMusic(List<Music> arr);
}
