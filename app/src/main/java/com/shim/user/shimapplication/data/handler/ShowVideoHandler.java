package com.shim.user.shimapplication.data.handler;

import com.shim.user.shimapplication.data.Video;

import java.util.List;

public interface ShowVideoHandler extends BaseHandler {
    void onSuccessShowVideo(List<Video> arr);
}
