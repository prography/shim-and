package com.shim.user.shimapplication.util.logging;

public class LogPrinter {
    public static final String TAG = "Shim";

    public static void println(int priority, LogEvent event, String... args) {
        switch (priority) {
            case Log.VERBOSE:
                android.util.Log.v(TAG, event.toStringWith(args));
                break;
            case Log.DEBUG:
                android.util.Log.d(TAG, event.toStringWith(args));
                break;
            case Log.INFO:
                android.util.Log.i(TAG, event.toStringWith(args));
                break;
            case Log.WARN:
                android.util.Log.w(TAG, event.toStringWith(args));
                break;
            case Log.ERROR:
                android.util.Log.e(TAG, event.toStringWith(args));
                break;
            case Log.ASSERT:
                android.util.Log.wtf(TAG, event.toStringWith(args));
                break;
        }
    }
}
