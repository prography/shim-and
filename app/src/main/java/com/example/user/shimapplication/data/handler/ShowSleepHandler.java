package com.example.user.shimapplication.data.handler;

import com.example.user.shimapplication.data.ShowSleepResponse;
import com.example.user.shimapplication.data.Sleep;

import java.util.List;

public interface ShowSleepHandler extends BaseHandler{
    void onSuccessShowSleep(List<Sleep> arr);
}
