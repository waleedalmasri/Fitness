package com.example.fitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class Counter extends AppCompatActivity {
    private int seconds;
    private boolean isRunning;
    EditText h, m, s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        h = findViewById(R.id.editTextHours);
        m = findViewById(R.id.editTextMinutes);
        s = findViewById(R.id.editTextSeconds);


        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("running");
        }
        runTimer();


    }


    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("seconds", seconds);
        bundle.putBoolean("running", isRunning);
    }

    public void btnStartOnClick(View view) {
        int hours;
        int min;
        int sec;

        if (h.getText().toString().equals("")) {
            hours = 0;
        } else {
            hours = Integer.parseInt(this.h.getText().toString()) * 3600;
        }
        if (m.getText().toString().equals("")) {
            min = 0;
        } else {
            min = Integer.parseInt(this.m.getText().toString()) * 60;
        }
        if (s.getText().toString().equals("")) {
            sec = 0;
        } else {
            sec = Integer.parseInt(this.s.getText().toString());
        }
        seconds = (hours) +
                (min) +
                (sec);
        this.isRunning = true;
        h.setEnabled(false);
        m.setEnabled(false);
        s.setEnabled(false);
        this.runTimer();


    }

    public void btnStopOnClick(View view) {
        isRunning = false;
    }

    public void btnResetOnClick(View view) {
        isRunning = false;
        seconds = 0;
        h.setText("");
        m.setText("");
        s.setText("");
        h.setEnabled(true);
        m.setEnabled(true);
        s.setEnabled(true);
    }

    private void runTimer() {
        final EditText timer = findViewById(R.id.editTextTime);


        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                if (seconds == 0) {
                    isRunning = false;
                    h.setEnabled(true);
                    m.setEnabled(true);
                    s.setEnabled(true);
                }
                int hours = seconds / 3600;
                int minutes = seconds % 3600 / 60;
                int snds = seconds % 60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, snds);
                timer.setText(time);
                if (isRunning) {
                    --seconds;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void btnBmiOnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}