package kot.helena.watchtest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.VisibleForTesting;

import static android.content.Context.SENSOR_SERVICE;

public class DrawMotionPresenter implements SensorEventListener {
    private SensorManager sensorManager;
    private final float centerX, centerY;
    private float circleX, circleY, anticipatedX, anticipatedY;
    private final float circleRadius, circleMargin, screenRadius;
    private final static float SPEED_X = -1, SPEED_Y = 1;

    private DrawMotionUI ui;

    DrawMotionPresenter(Context context, float centerX, float centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.circleX = centerX;
        this.circleY = centerY;
        this.circleRadius = centerX / 2;
        this.circleMargin = centerX / 10;
        this.screenRadius = centerX;
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
            anticipatedX = circleX + sensorEvent.values[0] * SPEED_X;
            anticipatedY = circleY + sensorEvent.values[1] * SPEED_Y;
            if (inBounds(anticipatedX, anticipatedY)) {
                updatePosition(anticipatedX, anticipatedY);
            }
        }
    }

    private void updatePosition(float x, float y) {
        circleX = x;
        circleY = y;
        ui.setCirclePosition(circleX, circleY, circleRadius);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @VisibleForTesting
    protected boolean inBounds(float x, float y) {
        return lengthOfOppositeInRectTriangle(centerX - x, centerY - y) <= screenRadius - circleMargin;
    }

    @VisibleForTesting
    protected float lengthOfOppositeInRectTriangle(float lenX, float lenY) {
        return (float) Math.sqrt(lenX * lenX + lenY * lenY);
    }

    public interface DrawMotionUI {
        void setCirclePosition(float x, float y, float r);
    }
}
