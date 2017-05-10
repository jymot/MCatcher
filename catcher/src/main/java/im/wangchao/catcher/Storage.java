package im.wangchao.catcher;

import android.content.Intent;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <p>Description  : Storage.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 17/5/9.</p>
 * <p>Time         : 上午11:20.</p>
 */
/*package*/ class Storage {
    //    private TextView stackTraceText, classNameText, methodNameText, LineNumberText, causeText;

    static final String KEY_CLS_NAME = "className";
    static final String KEY_STACK_TRACE = "stackTrace";
    static final String KEY_METHOD_NAME = "methodName";
    static final String KEY_LINE_NUMBER = "lineNumber";
    static final String KEY_CAUSE = "cause";

    static void packing(Intent intent, String clsName, String methodName, int line, String cause, String stackTrace){
        intent.putExtra(KEY_CLS_NAME, clsName);
        intent.putExtra(KEY_METHOD_NAME, methodName);
        intent.putExtra(KEY_LINE_NUMBER, line + "");
        intent.putExtra(KEY_CAUSE, cause);
        intent.putExtra(KEY_STACK_TRACE, stackTrace);
    }

    static void save(String content, Runnable callback) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.getDefault()).format(new Date());
        String logFileName = "Log-" + timeStamp + ".txt";
        File storageDir = Environment.getExternalStorageDirectory();
        try {
            File saveFile = createFile(new File(storageDir, "mcatcher/" + logFileName));
            FileOutputStream out = new FileOutputStream(saveFile);
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(content);
            writer.flush();
            writer.close();
            out.close();
            callback.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File createFile(File file) throws IOException {
        if (file == null){
            return null;
        }
        if (file.exists()){
            return file;
        }
        File parent = file.getParentFile();
        if (createDir(parent) != null && file.createNewFile()){
            return file;
        }
        return null;
    }

    private static File createDir(File file){
        if (file != null && (file.exists() || file.mkdirs())){
            return file;
        }
        return null;
    }
}
