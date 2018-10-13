package cn.plou.web.common.importDataBatch;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class CleanOnTime {
    public static void cleanTempFile(String filename,int houres) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                deleteDir(new File(filename));
            }
            private void deleteDir(File file) {
                file.delete(); }
        }, houres * 60 * 60* 24 * 1000, houres * 60 * 60* 24 * 1000);// 每24小时执行一次
    }

}
