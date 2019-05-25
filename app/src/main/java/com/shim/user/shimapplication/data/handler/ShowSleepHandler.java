package com.shim.user.shimapplication.data.handler;

import com.shim.user.shimapplication.data.ShowSleepResponse;
import com.shim.user.shimapplication.data.Sleep;

import java.util.List;

public interface ShowSleepHandler extends BaseHandler{
    void onSuccessShowSleep(List<Sleep> arr);
}
