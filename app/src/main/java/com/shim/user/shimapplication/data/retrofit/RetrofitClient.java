package com.shim.user.shimapplication.data.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit create(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://52.78.106.14/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
