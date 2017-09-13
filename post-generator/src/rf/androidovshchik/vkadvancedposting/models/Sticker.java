package rf.androidovshchik.vkadvancedposting.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import rf.androidovshchik.vkadvancedposting.components.GdxLog;

public class Sticker extends Image {

    private static final String TAG = Sticker.class.getSimpleName();

    public static final int NONE = -1;

    public int index;

    public boolean isPinching = false;

    public float startDragX;
    public float startDragY;
    public float startPinchX;
    public float startPinchY;
    public float startScale;
    public float startRotation;

    public Sticker(int index, Texture texture) {
        super(texture);
        this.index = index;
        GdxLog.d(TAG, "Sticker index: %d", index);
    }

    public void setDragStarts(float x, float y) {
        startDragX = x;
        startDragY = y;
        GdxLog.f(TAG, "setDragStarts startDragX: %f startDragY: %f", startDragX, startDragY);
    }

    public void setPinchStarts(float x, float y) {
        startPinchX = x;
        startPinchY = y;
        startScale = getScaleX();
        startRotation = getRotation();
        GdxLog.f(TAG, "setPinchStarts startPinchX: %f startPinchY: %f startScale: %f startRotation: %f",
                startPinchX, startPinchY, startScale, startRotation);
    }
}
