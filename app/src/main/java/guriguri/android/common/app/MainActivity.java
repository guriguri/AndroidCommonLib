package guriguri.android.common.app;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import guriguri.android.common.log.LogcatToFile;

public class MainActivity extends Activity {
    private static final Logger log = LoggerFactory.getLogger(MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = null;
//      thread =   LogcatToFile.getInstance().startLogcatToFile();
        thread = LogcatToFile.getInstance().startLogcatToFile(LogcatToFile.LOGCAT_LOG_LEVEL_DEBUG,
                LogcatToFile.DEFAULT_ROTATE_CNT, LogcatToFile.DEFAULT_FILE_SIZE_KB,
                LogcatToFile.DEFAULT_FILE_NAME);

        TextView threadInfo = (TextView)findViewById(R.id.thread_info);
        threadInfo.setText(thread.getId() + " " + thread.getName());

        log.info("tId={}, tName={}", thread.getId(), thread.getName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
