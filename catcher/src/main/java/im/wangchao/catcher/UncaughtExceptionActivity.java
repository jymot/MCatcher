package im.wangchao.catcher;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <p>Description  : UncaughtExceptionActivity.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 17/5/9.</p>
 * <p>Time         : 上午8:20.</p>
 */
public class UncaughtExceptionActivity extends AppCompatActivity implements View.OnClickListener{

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uncaught_exception);
        setTitle("UncaughtException");

        TextView classNameText = (TextView) findViewById(R.id.classNameText);
        TextView methodNameText = (TextView) findViewById(R.id.methodNameText);
        TextView LineNumberText = (TextView) findViewById(R.id.LineNumberText);
        TextView causeText = (TextView) findViewById(R.id.causeText);
        TextView stackTraceText = (TextView) findViewById(R.id.stackTraceText);
        findViewById(R.id.restartBtn).setOnClickListener(this);
        findViewById(R.id.saveBtn).setOnClickListener(this);

        Intent intent = getIntent();

        classNameText.setText(intent.getStringExtra(Storage.KEY_CLS_NAME));
        methodNameText.setText(intent.getStringExtra(Storage.KEY_METHOD_NAME));
        LineNumberText.setText(intent.getStringExtra(Storage.KEY_LINE_NUMBER));
        causeText.setText(intent.getStringExtra(Storage.KEY_CAUSE));
        stackTraceText.setText(intent.getStringExtra(Storage.KEY_STACK_TRACE));
    }

    private void restart() {
        Intent launchIntent = getApplication().getPackageManager().getLaunchIntentForPackage(this.getPackageName());
        if (launchIntent != null) {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(launchIntent);
        }
        finish();
    }

    @Override public void onClick(View v) {
        if (v.getId() == R.id.restartBtn){
            restart();
        } else if (v.getId() == R.id.saveBtn){
            Storage.save(getIntent().getStringExtra(Storage.KEY_STACK_TRACE), new Runnable() {
                @Override public void run() {
                    findViewById(R.id.saveBtn).post(new Runnable() {
                        @Override public void run() {
                            Toast.makeText(UncaughtExceptionActivity.this, "Save Success! ../mcatcher/..", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    @Override protected void onStop() {
        super.onStop();
        finish();
    }

    @Override public void finish() {
        super.finish();
        Process.killProcess(Process.myPid());
    }
}
