package com.example.campasstest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;


public class MainActivity extends Activity {
    private ImageView compassImg;
    private Sensor acc,mag;
    private SensorManager manager;
    private float lastRotateDegree;
    private SensorEventListener listener=new SensorEventListener() {
        float []accValue=new float[3];
        float []magValue=new float[3];
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
            {
                accValue=event.values.clone();
            }
            else if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD)
            {
                magValue=event.values.clone();
            }
            float []R=new float[9];
            float []values=new float[3];
            manager.getRotationMatrix(R, null, accValue, magValue);
            manager.getOrientation(R, values);
            float rotateDegree=-(float)Math.toDegrees(values[0]);
            if (Math.abs(rotateDegree-lastRotateDegree)>0.01)
            {
                RotateAnimation animation=new RotateAnimation(lastRotateDegree,rotateDegree, RotateAnimation.RELATIVE_TO_SELF,0.5f, RotateAnimation.RELATIVE_TO_SELF,0.5f);
                animation.setFillAfter(true);
                compassImg.setAnimation(animation);
                lastRotateDegree=rotateDegree;
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
        compassImg=(ImageView)findViewById(R.id.compass_image);
        manager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        acc=manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mag=manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        manager.registerListener(listener,acc,SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(listener,mag, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(manager!=null)
        {
            manager.unregisterListener(listener);
        }
    }
}
