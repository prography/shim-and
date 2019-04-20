package com.example.user.shimapplication.data.handler;

import com.example.user.shimapplication.data.Video;

import java.util.List;

public interface ShowVideoHandler extends BaseHandler {
    void onSuccessShowVideo(List<Video> arr);
}
