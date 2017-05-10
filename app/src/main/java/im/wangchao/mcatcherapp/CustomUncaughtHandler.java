package im.wangchao.mcatcherapp;

import android.util.Log;

/**
 * <p>Description  : CustomUncaughtHandler.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 17/5/9.</p>
 * <p>Time         : 上午8:50.</p>
 */
public class CustomUncaughtHandler implements Thread.UncaughtExceptionHandler{

    private Thread.UncaughtExceptionHandler mDefault;

    private CustomUncaughtHandler(){
        mDefault = Thread.getDefaultUncaughtExceptionHandler();
    }

    static CustomUncaughtHandler newInstance(){
        return new CustomUncaughtHandler();
    }

    @Override public void uncaughtException(Thread t, Throwable e) {
        Log.e("wcwcwc", "t: " + t.getName() + "\n" + "e: " + e.getMessage(), e);
        mDefault.uncaughtException(t, e);
    }

    void register(){
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
}
