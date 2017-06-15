package im.wangchao.catcher;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * <p>Description  : Catcher.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 2017/6/15.</p>
 * <p>Time         : 上午8:22.</p>
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

    public void init(@NonNull Context context){
        // nothing to do.
    }

}
