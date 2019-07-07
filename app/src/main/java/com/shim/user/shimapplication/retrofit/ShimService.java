package com.shim.user.shimapplication.retrofit;

import com.shim.user.shimapplication.retrofit.request.FeedbackRequest;
import com.shim.user.shimapplication.retrofit.request.MusicFavoriteRequest;
import com.shim.user.shimapplication.retrofit.response.AsmrListResponse;
import com.shim.user.shimapplication.retrofit.response.BaseResponse;
import com.shim.user.shimapplication.retrofit.response.MusicListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ShimService {
    @GET("sleep")
    Call<AsmrListResponse> getAsmrList();

    @GET("main")
    Call<MusicListResponse> getHomeMusicList();

    @GET("music/all")
    Call<MusicListResponse> getMusicList(@Query("id") String userId);

    @POST("etc/feedback")
    Call<BaseResponse> sendFeedback(@Body FeedbackRequest request);

    @POST("music")
    Call<BaseResponse> setMusicFavorite(@Body MusicFavoriteRequest request);
}
