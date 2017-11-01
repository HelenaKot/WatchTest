package kot.helena.watchtest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import static android.content.Context.SENSOR_SERVICE;

public class DrawMotionPresenter implements SensorEventListener {
    private SensorManager sensorManager;
    private final float centerX, centerY;
    private float translationX, translationY;
    private final float radious, margin = 20, speed = 1;//todo

    private DrawMotionUI ui;

    DrawMotionPresenter(Context context, float centerX, float centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radious = centerX / 2;
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
    }

    public void attachUi(DrawMotionUI ui) {
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        this.ui = ui;
    }

    public void detachUi() {
        sensorManager.unregisterListener(this);
        ui = null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//            if (inBounds(translationX + sensorEvent.values[0] * speed, translationY + sensorEvent.values[1] * speed)) {
//                translationX += sensorEvent.values[0] * speed;
//                translationY += sensorEvent.values[1] * speed;
//            }
            ui.updatePosition(translationX, translationY, radious);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private boolean inBounds(float x, float y) {
        return length(centerX + x, centerY + y) <= radious + margin;
    }

    private float length(float lenX, float lenY) {
        return (float) Math.sqrt(lenX * lenX + lenY * lenY);
    }

    public interface DrawMotionUI {
        void updatePosition(float x, float y, float r);
    }
}
