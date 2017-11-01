package kot.helena.watchtest;

import android.content.Context;
import android.hardware.SensorManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static android.content.Context.SENSOR_SERVICE;

public class DrawMotionPresenterTest {
    private static int CENTER_X = 100, CENTER_Y = 100;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock Context context;
    @Mock SensorManager sensorManager;

    DrawMotionPresenter drawMotionPresenter;

    @Before
    public void init() {
        Mockito.when(context.getSystemService(SENSOR_SERVICE)).thenReturn(sensorManager);
        drawMotionPresenter = new DrawMotionPresenter(context, CENTER_X, CENTER_Y);
    }

    @Test
    public void shouldNotFailAtBasicMatch() {
        Assert.assertEquals(drawMotionPresenter.lengthOfOppositeInRectTriangle(3, 4), 5, 0.00001);
        Assert.assertEquals(drawMotionPresenter.lengthOfOppositeInRectTriangle(5, 12), 13, 0.00001);
        Assert.assertEquals(drawMotionPresenter.lengthOfOppositeInRectTriangle(7, 24), 25, 0.00001);
        Assert.assertEquals(drawMotionPresenter.lengthOfOppositeInRectTriangle(-6, -8), 10, 0.00001);
    }

   @Test
    public void shouldDetectInBounds() {
        Assert.assertEquals(drawMotionPresenter.inBounds(CENTER_X/2, CENTER_Y/2), true);
        Assert.assertEquals(drawMotionPresenter.inBounds(CENTER_X - 1, CENTER_Y - 1), true);
        Assert.assertEquals(drawMotionPresenter.inBounds(CENTER_X + 40, CENTER_Y + 40), false);
    }

}