package com.shim.user.shimapplication.retrofit;

import com.shim.user.shimapplication.data.FavoriteRequest;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ShimService {
    @GET("sleep")
    Call<Map> getAsmrList();

    @GET("main")
    Call<Map> getHomeMusicList();

    @GET("music/all")
    Call<Map> getMusicList(@Query("id") String userId);

//    @FormUrlEncoded
//    @POST("music/my")
//    Call<Map> setMusicFavorite(@Field("user_id") String userId, @Field("music_id") int musicId, @Field("my") boolean favorite);

}
