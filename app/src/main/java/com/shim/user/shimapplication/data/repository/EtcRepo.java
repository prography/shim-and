package com.shim.user.shimapplication.data.repository;

import com.shim.user.shimapplication.data.Feedback;
import com.shim.user.shimapplication.data.FeedbackResponse;
import com.shim.user.shimapplication.data.handler.FeedbackHandler;
import com.shim.user.shimapplication.data.retrofit.RetrofitClient;
import com.shim.user.shimapplication.data.retrofit.ShimService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EtcRepo {
    private ShimService shimService =
            RetrofitClient.create().create(ShimService.class);

    private FeedbackHandler feedbackHandler;

    public EtcRepo(FeedbackHandler feedbackHandler){
        this.feedbackHandler = feedbackHandler;
    }

    public void sendFeedback(Feedback feedback){
        Call<FeedbackResponse> call = shimService.sendFeedback(feedback);
        call.enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(Call<FeedbackResponse> call, Response<FeedbackResponse> response) {
                feedbackHandler.onSuccessSendFeedback(response.body());
            }

            @Override
            public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                feedbackHandler.onFailure(t);
            }
        });
    }
}
