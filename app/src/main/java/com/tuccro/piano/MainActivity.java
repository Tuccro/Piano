package com.tuccro.piano;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tuccro.piano.Utils.LogFileIO;

import java.util.ArrayList;
import java.util.List;

import static com.tuccro.piano.Utils.Utils.getCurrentDate;
import static com.tuccro.piano.Utils.Utils.getCurrentTime;


public class MainActivity extends Activity {

    public static final String LOG_MESSAGE = "log";
    public static final String PRESSED_KEY = "Pressed key is ";
    public static final String SPACE = " ";
    public static final String NEW_LINE = "\n";

    List<Button> buttons;
    private TextView tvLog;
    private StringBuilder currentLog = null;
    boolean isLogEmpty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttons = new ArrayList<>();

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

        ReadFromFile readFromFile = new ReadFromFile();
        readFromFile.execute();

    }

    @Override
    protected void onResume() {
        super.onResume();

        ReadFromFile readFromFile = new ReadFromFile();
        readFromFile.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!isLogEmpty && currentLog.capacity() > 0) {
            WriteToFile writeToFile = new WriteToFile(currentLog.toString());
            writeToFile.execute();
            currentLog.delete(0, currentLog.capacity());
        }
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
                    bundle.putString(LOG_MESSAGE, PRESSED_KEY + note);
                    message.setData(bundle);
                    addItemToLog.handleMessage(message);
                    break;
                case R.id.bt_d:
                    note = "D";
                    bundle.putString(LOG_MESSAGE, PRESSED_KEY + note);
                    message.setData(bundle);
                    addItemToLog.handleMessage(message);
                    break;
                case R.id.bt_e:
                    note = "E";
                    bundle.putString(LOG_MESSAGE, PRESSED_KEY + note);
                    message.setData(bundle);
                    addItemToLog.handleMessage(message);
                    break;
                case R.id.bt_f:
                    note = "F";
                    bundle.putString(LOG_MESSAGE, PRESSED_KEY + note);
                    message.setData(bundle);
                    addItemToLog.handleMessage(message);
                    break;
                case R.id.bt_g:
                    note = "G";
                    bundle.putString(LOG_MESSAGE, PRESSED_KEY + note);
                    message.setData(bundle);
                    addItemToLog.handleMessage(message);
                    break;
                case R.id.bt_a:
                    note = "A";
                    bundle.putString(LOG_MESSAGE, PRESSED_KEY + note);
                    message.setData(bundle);
                    addItemToLog.handleMessage(message);
                    break;
                case R.id.bt_b:
                    note = "B";
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

        String log = null;

        @Override
        protected Object doInBackground(Object[] params) {

            log = LogFileIO.readLogFile();
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

        String log;

        public WriteToFile(String sessionLog) {
            this.log = sessionLog;
        }

        @Override
        protected Object doInBackground(Object[] params) {

            LogFileIO.appendLogFile(log);
            return null;
        }
    }
}
