package com.tuccro.piano;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.tuccro.piano.Utils.Utils.getCurrentDate;
import static com.tuccro.piano.Utils.Utils.getCurrentTime;


public class MainActivity extends Activity {

    public static final String LOG_MESSAGE = "log";
    public static final String PRESSED_KEY = "Pressed key is ";
    public static final String SPACE = " ";
    public static final String NEW_LINE = "\n";
    public static final String LOG_PATH = "Piano";
    public static final String LOG_FILE = "piano.log";

    List<Button> buttons;
    private TextView tvLog;
    private StringBuilder currentLog = null;
    boolean isLogEmpty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttons = new ArrayList<Button>();

        buttons.add((Button) findViewById(R.id.bt_c));
        buttons.add((Button) findViewById(R.id.bt_d));
        buttons.add((Button) findViewById(R.id.bt_e));
        buttons.add((Button) findViewById(R.id.bt_f));
        buttons.add((Button) findViewById(R.id.bt_g));
        buttons.add((Button) findViewById(R.id.bt_a));
        buttons.add((Button) findViewById(R.id.bt_b));

        for (Button button : buttons) button.setOnClickListener(onButtonClick);

        tvLog = (TextView) findViewById(R.id.tv_log);
        tvLog.setMovementMethod(new ScrollingMovementMethod());

        ReadFromFile readFromFile = new ReadFromFile(getApplicationContext());
        readFromFile.execute();

    }

    @Override
    protected void onResume() {
        super.onResume();

        ReadFromFile readFromFile = new ReadFromFile(getApplicationContext());
        readFromFile.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();

        WriteToFile writeToFile = new WriteToFile(getApplicationContext(), currentLog.toString());
        writeToFile.execute();
    }

    View.OnClickListener onButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Bundle bundle = new Bundle();
            Message message = new Message();
            String note;

            switch (v.getId()) {
                case R.id.bt_c:
                    note = "C";
                    //Log.e("Click", "Button " + note);
                    bundle.putString(LOG_MESSAGE, PRESSED_KEY + note);
                    message.setData(bundle);
                    addItemToLog.handleMessage(message);
                    break;
                case R.id.bt_d:
                    note = "D";
                    //Log.e("Click", "Button " + note);
                    bundle.putString(LOG_MESSAGE, PRESSED_KEY + note);
                    message.setData(bundle);
                    addItemToLog.handleMessage(message);
                    break;
                case R.id.bt_e:
                    note = "E";
                    //Log.e("Click", "Button " + note);
                    bundle.putString(LOG_MESSAGE, PRESSED_KEY + note);
                    message.setData(bundle);
                    addItemToLog.handleMessage(message);
                    break;
                case R.id.bt_f:
                    note = "F";
                    //Log.e("Click", "Button " + note);
                    bundle.putString(LOG_MESSAGE, PRESSED_KEY + note);
                    message.setData(bundle);
                    addItemToLog.handleMessage(message);
                    break;
                case R.id.bt_g:
                    note = "G";
                    //Log.e("Click", "Button " + note);
                    bundle.putString(LOG_MESSAGE, PRESSED_KEY + note);
                    message.setData(bundle);
                    addItemToLog.handleMessage(message);
                    break;
                case R.id.bt_a:
                    note = "A";
                    //Log.e("Click", "Button " + note);
                    bundle.putString(LOG_MESSAGE, PRESSED_KEY + note);
                    message.setData(bundle);
                    addItemToLog.handleMessage(message);
                    break;
                case R.id.bt_b:
                    note = "B";
                    //Log.e("Click", "Button " + note);
                    bundle.putString(LOG_MESSAGE, PRESSED_KEY + note);
                    message.setData(bundle);
                    addItemToLog.handleMessage(message);
                    break;
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler addItemToLog = new Handler() {

        public void handleMessage(android.os.Message msg) {

            if (isLogEmpty) {

                String dateLogMessage = SPACE + getCurrentDate() + NEW_LINE;
                currentLog = new StringBuilder(dateLogMessage);
                tvLog.append(dateLogMessage);
                isLogEmpty = false;
            }

            Bundle bundle = msg.getData();
            String message = getCurrentTime() + SPACE + bundle.getString(LOG_MESSAGE) + NEW_LINE;

            tvLog.append(message);
            currentLog.append(message);
        }
    };


    class ReadFromFile extends AsyncTask {

        Context context;
        String log = null;

        public ReadFromFile(Context context) {
            this.context = context;
        }

        @Override
        protected Object doInBackground(Object[] params) {

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
                    sbLog.append(str + NEW_LINE);
                }

                if (sbLog.capacity() != 0) {
                    log = sbLog.toString();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (log != null) {
                tvLog.setText(log);
            }
        }

    }

    class WriteToFile extends AsyncTask {

        Context context;
        String log;

        public WriteToFile(Context context, String sessionLog){
            this.context = context;
            this.log = sessionLog;
        }

        @Override
        protected Object doInBackground(Object[] params) {

            // проверяем доступность SD
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                Log.d("32325435363", "SD-карта не доступна: " + Environment.getExternalStorageState());
                return null;
            }
            // получаем путь к SD
            File sdPath = Environment.getExternalStorageDirectory();
            // добавляем свой каталог к пути
            sdPath = new File(sdPath.getAbsolutePath() + "/" + LOG_PATH);

            // формируем объект File, который содержит путь к файлу
            File sdFile = new File(sdPath, LOG_FILE);
            try {
                // открываем поток для записи
                BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
                // пишем данные
                bw.write(currentLog.toString() + NEW_LINE);
                // закрываем поток
                bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
