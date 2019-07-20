package co.shimm.app.util.logging;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import co.shimm.app.retrofit.ServiceGenerator;
import co.shimm.app.retrofit.ShimService;
import co.shimm.app.retrofit.request.LogRequest;
import co.shimm.app.retrofit.response.BaseResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogSender {
    public static String SSAID = "anonymous";

    public static void execute(LogEvent event, String... args) {
        ShimService service = ServiceGenerator.create();
        String eventString = event.toString();
        String paramsString = Arrays.toString(args).replaceAll("^\\[|\\]$", "");
        Log.d("tag", SSAID + " " + eventString + " " + paramsString);
        service.sendLog(new LogRequest(SSAID, eventString, paramsString))
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<BaseResponse> call, @NotNull Response<BaseResponse> response) {

                    }

                    @Override
                    public void onFailure(@NotNull Call<BaseResponse> call, @NotNull Throwable t) {

                    }
                });
    }
}
