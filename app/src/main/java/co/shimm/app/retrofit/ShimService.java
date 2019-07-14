package co.shimm.app.retrofit;

import co.shimm.app.retrofit.request.FeedbackRequest;
import co.shimm.app.retrofit.request.LogRequest;
import co.shimm.app.retrofit.request.MusicFavoriteRequest;
import co.shimm.app.retrofit.response.AsmrListResponse;
import co.shimm.app.retrofit.response.BaseResponse;
import co.shimm.app.retrofit.response.MusicListResponse;
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

    @POST("log")
    Call<BaseResponse> sendLog(@Body LogRequest request);

    @POST("music")
    Call<BaseResponse> setMusicFavorite(@Body MusicFavoriteRequest request);
}
