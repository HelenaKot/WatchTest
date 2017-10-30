package kot.helena.watchtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import static android.content.Context.SENSOR_SERVICE;

public class DrawView extends View implements SensorEventListener {
    private Paint paint = new Paint();
    private float centerX, centerY;
    private float movementX, movementY;
    private float lenght = 6;

    public DrawView(Context context) {
        super(context);
        init(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        paint.setColor(Color.RED);
        final View view = this;
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                centerX = view.getWidth() / 2;
                centerY = view.getHeight() / 2;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(centerX, centerY, centerX + movementX * -lenght, centerY + movementY * lenght, paint);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            movementX = sensorEvent.values[0];
            movementY = sensorEvent.values[1];
            this.invalidate();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
