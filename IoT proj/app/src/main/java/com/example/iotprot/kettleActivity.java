package com.example.iotprot;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class kettleActivity extends AppCompatActivity {

    private EditText editTextTime;
    private Button buttonStart;
    private TextView textViewCountdown;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kettle);

        editTextTime = findViewById(R.id.editTextTime);
        buttonStart = findViewById(R.id.timerButton);
        textViewCountdown = findViewById(R.id.textViewCountdown);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

    }

    private void startTimer() {
        String inputMinutes = editTextTime.getText().toString();

        if (!inputMinutes.isEmpty()) {
            long timeInMillis = Long.parseLong(inputMinutes) * 60 * 1000;

            countDownTimer = new CountDownTimer(timeInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long seconds = millisUntilFinished / 1000;
                    long minutes = seconds / 60;
                    seconds = seconds % 60;

                    textViewCountdown.setText(String.format("Time remaining: %02d:%02d", minutes, seconds));
                }

                @Override
                public void onFinish() {
                    textViewCountdown.setText("Timer finished!");
                    onMessage(buttonStart);
                }
            };

            countDownTimer.start();
        }
        else {
            Toast.makeText(this, "Please enter a valid time.", Toast.LENGTH_SHORT).show();
        }
    }


    public void onMessage(View view){
        TextView textview = findViewById(R.id.dT);

        if (textview.getVisibility() == View.GONE){
            textview.setText("Kettle Turned ON");
            textview.setVisibility(View.VISIBLE);
        }
        else {
            textview.setVisibility(View.GONE);
        }
    }

    public void offMessage(View view){
        TextView textview = findViewById(R.id.dT);

        if (textview.getVisibility() == View.GONE || textview.getText().equals("Kettle Turned ON")){
            textview.setText("Kettle Turned OFF");
            textview.setVisibility(View.VISIBLE);
        }
        else {
            textview.setVisibility(View.GONE);
        }

    }
}