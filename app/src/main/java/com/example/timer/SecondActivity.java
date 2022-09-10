package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textView = findViewById(R.id.textViewTime);

        Bundle arguments = getIntent().getExtras();
        int time_num = (int) arguments.get("Time");

        int hours = time_num / 3600;
        int minutes = (time_num % 3600) / 60;
        int sec = time_num % 60;

        String time = String.format("%02d:%02d:%02d", hours, minutes, sec);
        textView.setText(time);
    }
}