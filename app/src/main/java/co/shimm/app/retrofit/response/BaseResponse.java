package co.shimm.app.retrofit.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    private int status;
    @SerializedName("msg")
    private String message;

    public BaseResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
