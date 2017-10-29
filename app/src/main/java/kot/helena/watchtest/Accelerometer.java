package kot.helena.watchtest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import static android.content.Context.SENSOR_SERVICE;

public class Accelerometer implements SensorEventListener {
    private SensorManager sensorManager;
    private TextView textView;
    SensorEvent sensorEvent;

    public Accelerometer(Context root) {
        sensorManager = (SensorManager) root.getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//            textView.setText(sensorEvent.values[0] + "\n" + sensorEvent.values[1] + "\n" + sensorEvent.values[2]);
            this.sensorEvent = sensorEvent;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public float getX() {
        return sensorEvent.values[0];
    }

    public float getZ() {
        return sensorEvent.values[2];
    }

    public float getY() {
        return sensorEvent.values[1];
    }
}
