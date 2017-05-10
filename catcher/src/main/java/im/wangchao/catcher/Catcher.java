package im.wangchao.catcher;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * <p>Description  : Catcher.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 17/5/9.</p>
 * <p>Time         : 上午8:25.</p>
 */
public class Catcher {
    private volatile static Catcher mCatcher;
    private Catcher(){}

    public static Catcher instance(){
        if (mCatcher == null){
            synchronized (Catcher.class){
                if (mCatcher == null){
                    mCatcher = new Catcher();
                }
            }
        }

        return mCatcher;
    }

    private Context mContext;

    public void init(@NonNull Context context){
        if (!Utils.isMainProcess(context)){
            return;
        }
        mContext = context.getApplicationContext();
        ((Application)mContext).registerActivityLifecycleCallbacks(new ActivityLifecycle());
        CrashHandler.newInstance(Thread.getDefaultUncaughtExceptionHandler()).register();
    }

    Context getContext(){
        return mContext;
    }
}
