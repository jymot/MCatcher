package im.wangchao.mcatcherapp;

import android.app.Application;

import im.wangchao.catcher.Catcher;

/**
 * <p>Description  : App.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 17/5/9.</p>
 * <p>Time         : 上午8:46.</p>
 */
public class App extends Application {

    @Override public void onCreate() {
        super.onCreate();
        Catcher.instance().init(this);
        CustomUncaughtHandler.newInstance().register();
    }
}
