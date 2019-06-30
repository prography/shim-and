package com.shim.user.shimapplication.util;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Logger {
    private static final int VERBOSE = 2;
    private static final int DEBUG = 3;
    private static final int INFO = 4;
    private static final int WARN = 5;
    private static final int ERROR = 6;
    private static final int ASSERT = 7;
    private static final String LOGGER_TAG = "Logger";
    private static final String LOGGER_URL = "http://52.78.106.14/";

    public static String SSAID = "anonymous";

    public static void v(String message) {
        log(VERBOSE, message);
    }

    public static void d(String message) {
        log(DEBUG, message);
    }

    public static void i(String message) {
        log(INFO, message);
    }

    public static void w(String message) {
        log(WARN, message);
    }

    public static void e(String message) {
        log(ERROR, message);
    }

    public static void a(String message) {
        log(ASSERT, message);
    }

    private static void log(int priority, String message) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("user", SSAID)
                .add("message", message)
                .build();
        Request request = new Request.Builder()
                .url(LOGGER_URL)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
        switch (priority) {
            case VERBOSE:
                Log.v(LOGGER_TAG, message);
                break;
            case DEBUG:
                Log.d(LOGGER_TAG, message);
                break;
            case INFO:
                Log.i(LOGGER_TAG, message);
                break;
            case WARN:
                Log.w(LOGGER_TAG, message);
                break;
            case ERROR:
                Log.e(LOGGER_TAG, message);
                break;
            case ASSERT:
                Log.wtf(LOGGER_TAG, message);
                break;
        }
    }
}
