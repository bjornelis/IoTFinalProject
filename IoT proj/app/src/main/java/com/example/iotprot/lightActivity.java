package com.example.iotprot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class lightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
    }



    public void onMessage(View view){
        TextView textview = findViewById(R.id.dT2);

        if (textview.getVisibility() == View.GONE){
            textview.setText("Light Turned ON");
            textview.setVisibility(View.VISIBLE);
        }
        else {
            textview.setVisibility(View.GONE);
        }
    }

    public void offMessage(View view){
        TextView textview = findViewById(R.id.dT2);

        if (textview.getVisibility() == View.GONE || textview.getText().equals("Light Turned ON")){
            textview.setText("Light Turned OFF");
            textview.setVisibility(View.VISIBLE);
        }
        else {
            textview.setVisibility(View.GONE);
        }
    }

    public void showLogs(View view){
        TextView textview = findViewById(R.id.logText);
        if (textview.getVisibility() == View.GONE) {
            textview.setVisibility(View.VISIBLE);
        } else {
            textview.setVisibility(View.GONE);
        }
    }

}