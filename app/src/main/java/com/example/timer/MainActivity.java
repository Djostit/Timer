package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Spinner spinner_interval;
    private Spinner spinner_start;

    private int temp;
    private boolean isCheck = false;
    private int seconds = 0;
    private boolean isRunning = false;

    private final static String TAG = "MainActivity";
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        prefs = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);

        textView = findViewById(R.id.textView);
        spinner_interval = findViewById(R.id.spinner_interval);
        spinner_start = findViewById(R.id.spinner_start);
        runTimer();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("seconds", seconds);
        outState.putBoolean("isRunning", isRunning);
        outState.putBoolean("isCheck", isCheck);
        outState.putInt("temp", temp);

    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        seconds = savedInstanceState.getInt("seconds");
        isRunning = savedInstanceState.getBoolean("isRunning");
        isCheck = savedInstanceState.getBoolean("isCheck");
        temp = savedInstanceState.getInt("temp");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
    @Override
    protected void onStop(){
        super.onStop();

        prefs.edit().putInt("seconds", seconds).apply();

        Log.d(TAG, "onStop");
    }
    @Override
    protected void onStart(){
        super.onStart();

        seconds = prefs.getInt("seconds", 0);

        Log.d(TAG, "onStart");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    private void runTimer() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int sec = seconds % 60;

                if(!isRunning){
                    temp = Integer.parseInt(GetTimeInt(spinner_interval.getSelectedItemPosition()));
                }

                String time = String.format("%02d:%02d:%02d", hours, minutes, sec);
                textView.setText(time);
                if(isRunning) {
                    seconds+= temp;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private String GetTimeInt(int selectedItemPosition) {
        String[] stringArray = getResources().getStringArray(R.array.array_time);
        return stringArray[selectedItemPosition];
    }


    public void ClickButtonStart(View view) {
        if(!isCheck && !isRunning){
            seconds += Integer.parseInt(GetTimeInt(spinner_start.getSelectedItemPosition()));
        }
        isRunning = true;
    }

    public void ClickButtonStop(View view) {
        isCheck = true;
        isRunning = false;
    }

    public void ClickButtonReset(View view) {
        isRunning = false;
        isCheck = false;
        seconds = 0;
    }

    public void ClickButtonAnother(View view) {
        isRunning = false;
        Intent intent = new Intent(this, AnotherTimer.class);
        startActivity(intent);
    }
}