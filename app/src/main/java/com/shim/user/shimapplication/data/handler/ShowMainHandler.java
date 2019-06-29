package com.shim.user.shimapplication.data.handler;

import com.shim.user.shimapplication.data.Music;

import java.util.List;

public interface ShowMainHandler extends BaseHandler {
    void onSuccessShowMain(List<Music> arr);
}
