package im.wangchao.catcher;

import android.app.ActivityManager;
import android.content.Context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * <p>Description  : Utils.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 17/5/9.</p>
 * <p>Time         : 上午10:17.</p>
 */
public class Utils {
    public static <T> T checkNotNull(T t){
        if (t == null){
            throw new NullPointerException();
        }

        return t;
    }

    public static boolean isMainProcess(Context context) {
        try {
            ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
            List<ActivityManager.RunningAppProcessInfo> processInfo = am.getRunningAppProcesses();
            String mainProcessName = context.getPackageName();
            int myPid = android.os.Process.myPid();
            for (ActivityManager.RunningAppProcessInfo info : processInfo) {
                if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                    return true;
                }
            }
        } catch (Exception ignore) {}
        return false;
    }

    public static boolean isSystemDefaultUncaughtExceptionHandler(Thread.UncaughtExceptionHandler handler) {
        if (handler == null)
            return false;

        Thread.UncaughtExceptionHandler defHandler = null;
        try {
            Class cls = Class.forName("com.android.internal.os.RuntimeInit$UncaughtHandler");
            Constructor constructor = cls.getDeclaredConstructor();
            if (constructor != null){
                constructor.setAccessible(true);
                defHandler = (Thread.UncaughtExceptionHandler) constructor.newInstance();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return defHandler != null && defHandler.getClass().isInstance(handler);
    }

}
