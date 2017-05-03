package pvsys.mauro.sdk;

import android.util.Log;

public class Logger {
    private final String tag;
    private boolean debugEnabled = false;

    public Logger(String tag) {
        this.tag = tag;
    }

    Logger withDebug(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
        return this;
    }

    public void debug(String msg) {
        Log.d(tag, msg);
    }

    public void info(String msg) {
        Log.i(tag, msg);
    }

    public void warn(String msg) {
        Log.w(tag, msg);
    }

    public void warn(String msg, Throwable t) {
        Log.w(tag, msg, t);
    }

    public void error(String msg) {
        Log.e(tag, msg);
    }

    public void error(String msg, Throwable t) {
        Log.e(tag, msg, t);
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

}
