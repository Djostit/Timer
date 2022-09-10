package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AnotherTimer extends AppCompatActivity {
    TextView textView;
    Spinner spinner;

    private int seconds;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_timer);
        textView = findViewById(R.id.TextViewClock);
        spinner = findViewById(R.id.spinner);

        runTimer();
    }

    private String GetTimeInt(int selectedItemPosition) {
        String[] stringArray = getResources().getStringArray(R.array.array_long_time);
        return stringArray[selectedItemPosition];
    }

    public void ButtonClickStart(View view) {
        seconds = Integer.parseInt(GetTimeInt(spinner.getSelectedItemPosition()));
        isRunning = true;
    }

    public void ButtonClickStop(View view) {
        isRunning = false;
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("Time", seconds);
        startActivity(intent);
    }

    private void runTimer() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (seconds == 0) { isRunning = false; }
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int sec = seconds % 60;


                String time = String.format("%02d:%02d:%02d", hours, minutes, sec);
                textView.setText(time);
                if(isRunning && seconds > 0 ) {
                    seconds--;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}