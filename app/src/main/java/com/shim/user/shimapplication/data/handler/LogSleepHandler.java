package com.shim.user.shimapplication.data.handler;

import com.shim.user.shimapplication.data.LogResponse;

public interface LogSleepHandler extends BaseHandler {
    void onSuccessLogSleep(LogResponse response);
}
