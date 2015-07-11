package guriguri.android.common.log;

import android.test.InstrumentationTestCase;

import java.io.File;
import java.util.Date;

/**
 * Created by guriguri on 2015. 07. 10.
 */
public class LogcatToFileTest extends InstrumentationTestCase {
    private static final int ROTATE_CNT = 1;
    private static final int FILE_SIZE_KB = 1;
    private static final String FILE_NAME = "/sdcard/log/logcat-test.log";

    public void testLogcat() {
        File file = new File(FILE_NAME);
        if (file.exists() == true) {
            file.delete();
        }

        // check file.exists()
        LogcatToFile.getInstance().startLogcatToFile(LogcatToFile.LOGCAT_LOG_LEVEL_VERBOSE, ROTATE_CNT, FILE_SIZE_KB, FILE_NAME);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        file = new File(FILE_NAME);
        assertEquals("file.exists()", true, file.exists());

        // check lastModified
        LogcatToFile.getInstance().stopLogcatToFile();
        long lastModified = file.lastModified();

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Date now = new Date();

        assertEquals("lastModified < now", true, lastModified < now.getTime());
    }
}
