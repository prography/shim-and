package com.shim.user.shimapplication.retrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ShimService {
    @GET("sleep")
    Call<Map> getAsmrList();

    @GET("main")
    Call<Map> getHomeMusicList();

    @GET("music/all")
    Call<Map> getMusicList();
}
