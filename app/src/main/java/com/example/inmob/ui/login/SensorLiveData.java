// SensorLiveData.java
package com.example.inmob.ui.login;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.lifecycle.LiveData;

public class SensorLiveData extends LiveData<Boolean> implements SensorEventListener {

    private final SensorManager sensorManager;
    private float acelValue;
    private float acelLast;
    private float shake;

    public SensorLiveData(Context context) {
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.acelValue = SensorManager.GRAVITY_EARTH;
        this.acelLast = SensorManager.GRAVITY_EARTH;
        this.shake = 0.00f;
    }

    @Override
    protected void onActive() {
        super.onActive();
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        acelLast = acelValue;
        acelValue = (float) Math.sqrt((x * x + y * y + z * z));
        float delta = acelValue - acelLast;
        shake = shake * 0.9f + delta;

        if (shake > 12) {
            // Se detectó una sacudida, notifica a los observadores
            postValue(true);
            // Resetea el valor para evitar múltiples llamadas seguidas
            shake = 0.00f;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
