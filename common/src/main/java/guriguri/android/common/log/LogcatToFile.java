package guriguri.android.common.log;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by guriguri on 2015. 07. 10..
 */
public class LogcatToFile {
    private static final Logger log = LoggerFactory.getLogger(LogcatToFile.class);

    // "*:s > *:f > *:e > *:w > *:i > *:d > *:v
    public static final String LOGCAT_LOG_LEVEL_SILENT = "*:s";
    public static final String LOGCAT_LOG_LEVEL_FATAL = "*:f";
    public static final String LOGCAT_LOG_LEVEL_ERROR = "*:e";
    public static final String LOGCAT_LOG_LEVEL_WARN = "*:w";
    public static final String LOGCAT_LOG_LEVEL_INFO = "*:i";
    public static final String LOGCAT_LOG_LEVEL_DEBUG = "*:d";
    public static final String LOGCAT_LOG_LEVEL_VERBOSE = "*:v";

    private static final int MIN_ROTATE_CNT = 1;
    private static final int MAX_FILE_SIZE_KB = 51200;

    public static final String DEFAULT_FILE_NAME = "/sdcard/log/logcat.log";
    public static final String DEFAULT_LOG_LEVEL = LOGCAT_LOG_LEVEL_WARN;
    public static final int DEFAULT_ROTATE_CNT = 5;
    public static final int DEFAULT_FILE_SIZE_KB = MAX_FILE_SIZE_KB;

    private static final String FORMAT_LOGCAT_CMD = "logcat -v time -n %d -r %d -f %s -s %s";

    private static final String THREAD_NAME = "LOGCAT";

    private static LogcatToFile logcatToFile = new LogcatToFile();
    private static Thread thread;

    private LogcatToFile() {
    }

    public static LogcatToFile getInstance() {
        return logcatToFile;
    }

    public Thread startLogcatToFile(String logLevel, int rotateCnt, int fileSizeKB, String fileName) {
        if (thread == null) {
            if (StringUtils.isEmpty(logLevel) == true) {
                logLevel = DEFAULT_LOG_LEVEL;
            }

            if (rotateCnt < MIN_ROTATE_CNT) {
                rotateCnt = MIN_ROTATE_CNT;
            }

            if (fileSizeKB > MAX_FILE_SIZE_KB) {
                fileSizeKB = MAX_FILE_SIZE_KB;
            }

            if (StringUtils.isEmpty(fileName) == true) {
                fileName = DEFAULT_FILE_NAME;
            }

            final String finalLogLevel = logLevel;
            final int finalRotateCnt = rotateCnt;
            final int finalFileSizeKB = fileSizeKB;
            final String finalFileName = fileName;

            thread = new Thread(new Runnable() {
                public void run() {
                    String cmd = null;

                    try {
                        cmd = String.format(FORMAT_LOGCAT_CMD, finalRotateCnt, finalFileSizeKB,
                                finalFileName, finalLogLevel);
                        log.info("{}", cmd);

                        Process process = Runtime.getRuntime().exec(cmd);
                        log.info("{}, exitValue={}", cmd, process.waitFor());
                    } catch (InterruptedException e) {
                        log.info("cmd={}, Interrupt", cmd);
                    } catch (Exception e) {
                        log.error("cmd={}, {}", cmd, e.getMessage(), e);
                    }
                }
            }, THREAD_NAME);
            thread.start();
        }

        log.debug("tId={}, name={}", thread.getId(), thread.getName());

        return thread;
    }

    public Thread startLogcatToFile() {
        return startLogcatToFile(DEFAULT_LOG_LEVEL, DEFAULT_ROTATE_CNT, DEFAULT_FILE_SIZE_KB,
                DEFAULT_FILE_NAME);
    }

    public void stopLogcatToFile() {
        if (thread.isAlive() == true) {
            thread.interrupt();
            thread = null;
        }
    }
}
