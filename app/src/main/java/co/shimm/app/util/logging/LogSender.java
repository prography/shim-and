package co.shimm.app.util.logging;

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
        service.sendLog(new LogRequest(SSAID, event.toString(), Arrays.toString(args).replaceAll("^\\[|\\]$", "")))
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
