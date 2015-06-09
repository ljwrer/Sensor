package com.example.accelerometersensortest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends Activity {
    private Sensor sensor;
    private SensorManager manager;
    private SensorEventListener listener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x=event.values[0];
            float y=event.values[1];
            float z=event.values[2];
            if(x>15||y>15||z>15)
            {
                Toast.makeText(MainActivity.this,"摇一摇",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor=manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.unregisterListener(listener);
    }
}
