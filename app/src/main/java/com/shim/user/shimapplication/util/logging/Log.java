package com.shim.user.shimapplication.util.logging;

public class Log {
    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;

    public static void v(LogEvent event, String... args) {
        LogPrinter.println(VERBOSE, event, args);
    }

    public static void d(LogEvent event, String... args) {
        LogPrinter.println(DEBUG, event, args);
    }

    public static void i(LogEvent event, String... args) {
        LogPrinter.println(INFO, event, args);
        LogSender.execute(event, args); // only for info log
    }

    public static void w(LogEvent event, String... args) {
        LogPrinter.println(WARN, event, args);
    }

    public static void e(LogEvent event, String... args) {
        LogPrinter.println(ERROR, event, args);
    }

    public static void a(LogEvent event, String... args) {
        LogPrinter.println(ASSERT, event, args);
    }
}
