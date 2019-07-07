package com.shim.user.shimapplication.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static ShimService create() {
        return new Retrofit.Builder()
                .baseUrl("http://13.124.230.126/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ShimService.class);
    }
}