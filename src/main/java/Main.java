import java.awt.*;
import java.io.IOException;

import Sr.SrManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

    private static boolean isRun = false;
    private static final int READ_INTERVAL = 1000;

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                isRun = false;
            }
        }));

        new Thread(new Runnable() {
            @Override
            public void run() {
                SrManager srMgr = new SrManager();
                isRun = true;
                while (isRun) {
                    srMgr.reservateSRT();
                    try {
                        Thread.sleep(READ_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
