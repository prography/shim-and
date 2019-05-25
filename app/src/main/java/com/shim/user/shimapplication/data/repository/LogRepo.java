package com.shim.user.shimapplication.data.repository;

import com.shim.user.shimapplication.data.LogMain;
import com.shim.user.shimapplication.data.LogMusic;
import com.shim.user.shimapplication.data.LogResponse;
import com.shim.user.shimapplication.data.LogSleep;
import com.shim.user.shimapplication.data.LogVideo;
import com.shim.user.shimapplication.data.handler.LogMainHandler;
import com.shim.user.shimapplication.data.handler.LogMusicHandler;
import com.shim.user.shimapplication.data.handler.LogSleepHandler;
import com.shim.user.shimapplication.data.handler.LogVideoHandler;
import com.shim.user.shimapplication.data.retrofit.RetrofitClient;
import com.shim.user.shimapplication.data.retrofit.ShimService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogRepo {
    private ShimService shimService =
            RetrofitClient.create().create(ShimService.class);

    private LogMainHandler logMainHandler;
    private LogSleepHandler logSleepHandler;
    private LogVideoHandler logVideoHandler;
    private LogMusicHandler logMusicHandler;

    public LogRepo(LogMainHandler logMainHandler){
        this.logMainHandler = logMainHandler;
    }
    public LogRepo(LogSleepHandler logSleepHandler){
        this.logSleepHandler = logSleepHandler;
    }
    public LogRepo(LogVideoHandler logVideoHandler){
        this.logVideoHandler = logVideoHandler;
    }
    public LogRepo(LogMusicHandler logMusicHandler){
        this.logMusicHandler = logMusicHandler;
    }

    public void logMain(LogMain logMain){
        Call<LogResponse> call = shimService.logMain(logMain);
        call.enqueue(new Callback<LogResponse>() {
            @Override
            public void onResponse(Call<LogResponse> call, Response<LogResponse> response) {
                logMainHandler.onSuccessLogMain(response.body());
            }

            @Override
            public void onFailure(Call<LogResponse> call, Throwable t) {
                logMainHandler.onFailure(t);
            }
        });
    }

    public void logSleep(LogSleep logSleep){
        Call<LogResponse> call = shimService.logSleep(logSleep);
        call.enqueue(new Callback<LogResponse>() {
            @Override
            public void onResponse(Call<LogResponse> call, Response<LogResponse> response) {
                logSleepHandler.onSuccessLogSleep(response.body());
            }

            @Override
            public void onFailure(Call<LogResponse> call, Throwable t) {
                logSleepHandler.onFailure(t);
            }
        });
    }

    public void logVideo(LogVideo logVideo){
        Call<LogResponse> call = shimService.logVideo(logVideo);
        call.enqueue(new Callback<LogResponse>() {
            @Override
            public void onResponse(Call<LogResponse> call, Response<LogResponse> response) {
                logVideoHandler.onSuccessLogVideo(response.body());
            }

            @Override
            public void onFailure(Call<LogResponse> call, Throwable t) {
                logVideoHandler.onFailure(t);
            }
        });
    }

    public void logMusic(LogMusic logMusic){
        Call<LogResponse> call = shimService.logMusic(logMusic);
        call.enqueue(new Callback<LogResponse>() {
            @Override
            public void onResponse(Call<LogResponse> call, Response<LogResponse> response) {
                logMusicHandler.onSuccessLogMusic(response.body());
            }

            @Override
            public void onFailure(Call<LogResponse> call, Throwable t) {
                logMusicHandler.onFailure(t);
            }
        });
    }
}
