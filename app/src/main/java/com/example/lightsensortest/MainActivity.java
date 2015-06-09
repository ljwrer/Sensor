package com.example.lightsensortest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends Activity {
    private Sensor sensor;
    private SensorManager manager;
    private TextView lightLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lightLevel=(TextView)findViewById(R.id.light_level);
        manager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor=manager.getDefaultSensor(Sensor.TYPE_LIGHT);
        manager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
    private SensorEventListener listener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            lightLevel.setText("Current light level is"+event.values[0]+" lx");
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(manager!=null)
        {
            manager.unregisterListener(listener);
        }
    }
}
