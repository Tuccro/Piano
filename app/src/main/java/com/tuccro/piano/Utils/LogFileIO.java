package com.tuccro.piano.Utils;

import android.os.Environment;
import android.util.Log;

import com.tuccro.piano.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LogFileIO {


    public static final String LOG_PATH = "Piano";
    public static final String LOG_FILE = "piano.log";

    public static String readLogFile() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.e("SD Card", "Not Detected " + Environment.getExternalStorageState());
            return null;
        }
        // получаем путь к SD
        File logPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        logPath = new File(logPath.getAbsolutePath() + "/" + LOG_PATH);
        if (!logPath.exists()) {
            logPath.mkdirs();
        }
        // формируем объект File, который содержит путь к файлу
        File logFile = new File(logPath, LOG_FILE);

        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            StringBuilder sbLog = new StringBuilder();
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new FileReader(logFile));
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                sbLog.append(str + MainActivity.NEW_LINE);
            }

            if (sbLog.capacity() != 0) {
                return sbLog.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return MainActivity.NEW_LINE;
    }

    public static void appendLogFile(String log) {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + LOG_PATH);

        // формируем объект File, который содержит путь к файлу
        File logFile = new File(sdPath, LOG_FILE);
        try {
            // открываем поток для записи
            BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true));
            // пишем данные
            bw.write(log + MainActivity.NEW_LINE);
            // закрываем поток
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
