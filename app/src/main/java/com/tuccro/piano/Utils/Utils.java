package com.tuccro.piano.Utils;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getCurrentTime() {
        long curTime = System.currentTimeMillis();
        Date curDate = new Date(curTime);
        String curStringTime = new SimpleDateFormat("HH:mm:ss").format(curTime);
        return curStringTime;
    }

    public static String getCurrentDate() {
        long curTime = System.currentTimeMillis();
        Date curDate = new Date(curTime);
        String curStringDate = new SimpleDateFormat("dd MM yyyy").format(curTime);
        return curStringDate;
    }

}
