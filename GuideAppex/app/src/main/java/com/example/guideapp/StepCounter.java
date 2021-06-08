package com.example.guideapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class StepCounter extends AppCompatActivity implements SensorEventListener {
    TextView tv_steps;

    SensorManager sensorManager;
    boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        tv_steps = (TextView) findViewById(R.id.tv_steps);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
       Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
       if (countSensor != null){
           sensorManager.registerListener((SensorEventListener) this, countSensor, SensorManager.SENSOR_DELAY_UI);
       }else{
           Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
       }
    }
    @Override
    protected void onPause() {
        super.onPause();
        running = false;
        //if you unregister
        //sensorManager.unregisterListener(this);
    }
    public void onSensorChanged(SensorEvent event){
        if (running){
            tv_steps.setText(String.valueOf(event.values[0]));
        }
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }
}