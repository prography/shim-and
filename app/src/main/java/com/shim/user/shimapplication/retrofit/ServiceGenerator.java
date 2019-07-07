package com.shim.user.shimapplication.retrofit;

import com.shim.user.shimapplication.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static ShimService create() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ShimService.class);
    }
}
