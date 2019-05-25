package com.shim.user.shimapplication.data.handler;

import com.shim.user.shimapplication.data.Music;

import java.util.List;

public interface ShowMusicHandler extends BaseHandler {
    void onSuccessShowMusic(List<Music> arr);
}
