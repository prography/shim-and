package co.shimm.app.retrofit.request;

import com.google.gson.annotations.SerializedName;

public class LogRequest {
    @SerializedName("user")
    private String userId;
    private String event;
    private String params;

    public LogRequest(String userId, String event, String params) {
        this.userId = userId;
        this.event = event;
        this.params = params;
    }

    public String getEvent() {
        return event;
    }

    public String getParams() {
        return params;
    }

    public String getUserId() {
        return userId;
    }
}
