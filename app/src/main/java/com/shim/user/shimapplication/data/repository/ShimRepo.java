package com.shim.user.shimapplication.data.repository;

import com.shim.user.shimapplication.data.FavoriteRequest;
import com.shim.user.shimapplication.data.FavoriteResponse;
import com.shim.user.shimapplication.data.ShowMainResponse;
import com.shim.user.shimapplication.data.ShowMusicResponse;
import com.shim.user.shimapplication.data.ShowSleepResponse;
import com.shim.user.shimapplication.data.ShowVideoResponse;
import com.shim.user.shimapplication.data.handler.FavoriteHandler;
import com.shim.user.shimapplication.data.handler.ShowMainHandler;
import com.shim.user.shimapplication.data.handler.ShowMusicHandler;
import com.shim.user.shimapplication.data.handler.ShowSleepHandler;
import com.shim.user.shimapplication.data.handler.ShowVideoHandler;
import com.shim.user.shimapplication.data.retrofit.RetrofitClient;
import com.shim.user.shimapplication.data.retrofit.ShimService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShimRepo {
    private ShimService shimService =
            RetrofitClient.create().create(ShimService.class);

    private ShowSleepHandler showSleepHandler;
    private ShowMusicHandler showMusicHandler;
    private ShowVideoHandler showVideoHandler;
    private ShowMainHandler showMainHandler;
    private FavoriteHandler favoriteHandler;

    public ShimRepo(ShowSleepHandler showSleepHandler) {
        this.showSleepHandler = showSleepHandler;
    }

    public ShimRepo(ShowMusicHandler showMusicHandler){
        this.showMusicHandler = showMusicHandler;
    }

    public ShimRepo(ShowVideoHandler showVideoHandler){
        this.showVideoHandler = showVideoHandler;
    }

    public ShimRepo(ShowMainHandler showMainHandler){
        this.showMainHandler = showMainHandler;
    }

    public ShimRepo(FavoriteHandler favoriteHandler) {
        this.favoriteHandler = favoriteHandler;
    }

    public void showSleep(){
        Call<ShowSleepResponse> call = shimService.showSleep();
        call.enqueue(new Callback<ShowSleepResponse>() {
            @Override
            public void onResponse(Call<ShowSleepResponse> call, Response<ShowSleepResponse> response) {
                //if(response.body()!=null)
                    showSleepHandler.onSuccessShowSleep(response.body().getArr());
            }

            @Override
            public void onFailure(Call<ShowSleepResponse> call, Throwable t) {
                showSleepHandler.onFailure(t);
            }
        });
    }

    public void showMain(){
        Call<ShowMainResponse> call = shimService.showMain();
        call.enqueue(new Callback<ShowMainResponse>() {
            @Override
            public void onResponse(Call<ShowMainResponse> call, Response<ShowMainResponse> response) {
                showMainHandler.onSuccessShowMain(response.body().getArr());
            }

            @Override
            public void onFailure(Call<ShowMainResponse> call, Throwable t) {
                showMainHandler.onFailure(t);
            }
        });
    }

    public void showMusic(String category){
        Call<ShowMusicResponse> call = shimService.showMusic(category);
        call.enqueue(new Callback<ShowMusicResponse>() {
            @Override
            public void onResponse(Call<ShowMusicResponse> call, Response<ShowMusicResponse> response) {
                if(response.body()!=null)
                showMusicHandler.onSuccessShowMusic(response.body().getArr());
            }

            @Override
            public void onFailure(Call<ShowMusicResponse> call, Throwable t) {
                showMusicHandler.onFailure(t);
            }
        });
    }

    public void showVideo(String category){
        Call<ShowVideoResponse> call = shimService.showVideo(category);
        call.enqueue(new Callback<ShowVideoResponse>() {
            @Override
            public void onResponse(Call<ShowVideoResponse> call, Response<ShowVideoResponse> response) {
                //if(response.body()!=null)
                showVideoHandler.onSuccessShowVideo(response.body().getArr());
            }

            @Override
            public void onFailure(Call<ShowVideoResponse> call, Throwable t) {
                showVideoHandler.onFailure(t);
            }
        });
    }

    public void requestFavorite(FavoriteRequest request){
        Call<FavoriteResponse> call = shimService.setMusicFavorite(request);
        call.enqueue(new Callback<FavoriteResponse>() {
            @Override
            public void onResponse(Call<FavoriteResponse> call, Response<FavoriteResponse> response) {
                favoriteHandler.onSuccessSendFavorite(response.body());
            }

            @Override
            public void onFailure(Call<FavoriteResponse> call, Throwable t) {
                favoriteHandler.onFailure(t);
            }
        });
    }
}
