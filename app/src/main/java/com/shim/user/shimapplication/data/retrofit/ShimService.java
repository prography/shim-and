package com.shim.user.shimapplication.data.retrofit;

import com.shim.user.shimapplication.data.FavoriteRequest;
import com.shim.user.shimapplication.data.FavoriteResponse;
import com.shim.user.shimapplication.data.Feedback;
import com.shim.user.shimapplication.data.FeedbackResponse;
import com.shim.user.shimapplication.data.LogMain;
import com.shim.user.shimapplication.data.LogMusic;
import com.shim.user.shimapplication.data.LogResponse;
import com.shim.user.shimapplication.data.LogSleep;
import com.shim.user.shimapplication.data.LogVideo;
import com.shim.user.shimapplication.data.ShowMainResponse;
import com.shim.user.shimapplication.data.ShowMusicResponse;
import com.shim.user.shimapplication.data.ShowSleepResponse;
import com.shim.user.shimapplication.data.ShowVideoResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ShimService {
    @GET("sleep")
    Call<ShowSleepResponse> showSleep();

    @GET("main")
    Call<ShowMainResponse> showMain();

    @GET("music/{music_category}")
    Call<ShowMusicResponse> showMusic(@Path("music_category") String music_category);

    @GET("video/{video_category}")
    Call<ShowVideoResponse> showVideo(@Path("video_category") String video_category);

    @POST("log/main")
    Call<LogResponse> logMain(@Body LogMain logMain);

    @POST("log/sleep")
    Call<LogResponse> logSleep(@Body LogSleep logSleep);

    @POST("log/video")
    Call<LogResponse> logVideo(@Body LogVideo logVideo);

    @POST("log/music")
    Call<LogResponse> logMusic(@Body LogMusic logMusic);

    @POST("etc/feedback")
    Call<FeedbackResponse> sendFeedback(@Body Feedback feedback);

    @POST("music")
    Call<FavoriteResponse> setMusicFavorite(@Body FavoriteRequest request);
}
