package im.wangchao.catcher;

import android.content.Intent;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

import static im.wangchao.catcher.Storage.KEY_CAUSE;
import static im.wangchao.catcher.Storage.KEY_CLS_NAME;
import static im.wangchao.catcher.Storage.KEY_LINE_NUMBER;
import static im.wangchao.catcher.Storage.KEY_METHOD_NAME;
import static im.wangchao.catcher.Storage.KEY_STACK_TRACE;


/**
 * <p>Description  : CrashHandler.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 17/5/9.</p>
 * <p>Time         : 上午8:13.</p>
 */
/*package*/ class CrashHandler implements Thread.UncaughtExceptionHandler{

    private Thread.UncaughtExceptionHandler mDefault;

    private CrashHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler){
        mDefault = uncaughtExceptionHandler;
    }

    static CrashHandler newInstance(Thread.UncaughtExceptionHandler uncaughtExceptionHandler){
        return new CrashHandler(uncaughtExceptionHandler);
    }

    @Override public void uncaughtException(Thread t, Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.flush();

        String stackTrace = sw.toString();
        String cause = e.getMessage();
        Throwable rootTr = e;
        while (e.getCause() != null) {
            e = e.getCause();
            if (e.getStackTrace() != null && e.getStackTrace().length > 0)
                rootTr = e;
            String msg = e.getMessage();
            if (!TextUtils.isEmpty(msg))
                cause = msg;
        }

        String exceptionType = rootTr.getClass().getName();

        String throwClassName;
        String throwMethodName;
        int throwLineNumber;

        if (rootTr.getStackTrace().length > 0) {
            StackTraceElement trace = rootTr.getStackTrace()[0];
            throwClassName = trace.getClassName();
            throwMethodName = trace.getMethodName();
            throwLineNumber = trace.getLineNumber();
        } else {
            throwClassName = "unknown";
            throwMethodName = "unknown";
            throwLineNumber = 0;
        }
        Log.e("wcwcwc", "cause: " + cause + "\n" +
                "stackTrace: " + stackTrace + "\n" +
                "throwClassName: " + throwClassName + "\n" +
                "throwMethodName: " + throwMethodName + "\n" +
                "throwLineNumber: " + throwLineNumber + "\n" +
                "exceptionType: " + exceptionType);
        if (Utils.isSystemDefaultUncaughtExceptionHandler(mDefault)){
            startActivity(throwClassName, throwMethodName, throwLineNumber, cause, stackTrace);
            Process.killProcess(Process.myPid());
        } else {
            if (mDefault == null){
                Process.killProcess(Process.myPid());
                return;
            }
            startActivity(throwClassName, throwMethodName, throwLineNumber, cause, stackTrace);
            mDefault.uncaughtException(t, e);
        }

    }

    void register(){
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private void startActivity(String clsName, String methodName, int line, String cause, String stackTrace) {
        Intent intent = new Intent();
        intent.setClass(Catcher.instance().getContext(), UncaughtExceptionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.putExtra(KEY_CLS_NAME, clsName);
        intent.putExtra(KEY_METHOD_NAME, methodName);
        intent.putExtra(KEY_LINE_NUMBER, String.valueOf(line));
        intent.putExtra(KEY_CAUSE, cause);
        intent.putExtra(KEY_STACK_TRACE, stackTrace);
//        Storage.packing(intent, clsName, methodName, line, cause, stackTrace);
        Catcher.instance().getContext().startActivity(intent);
    }
}
