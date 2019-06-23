package com.shim.user.shimapplication.data.handler;

import com.shim.user.shimapplication.data.FeedbackResponse;

public interface FeedbackHandler extends BaseHandler {
    void onSuccessSendFeedback(FeedbackResponse response);
}
