package kot.helena.watchtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

public class DrawView extends View implements DrawMotionPresenter.DrawMotionUI {
    private Paint paint = new Paint();
    private DrawMotionPresenter drawMotionPresenter; //todo attach/detach lifecycle
    private float x, y, r;

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
        paint.setColor(Color.RED);
        final DrawView view = this;
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                drawMotionPresenter = new DrawMotionPresenter(view.getContext(), view.getWidth() / 2, view.getHeight() / 2);
                drawMotionPresenter.attachUi(view);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, r, paint);
    }

    @Override
    public void setCirclePosition(float x, float y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
        invalidate();
    }
}
