package rf.androidovshchik.vkadvancedposting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

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
        Gdx.app.log(TAG, "Sticker index: " + index);
    }

    public void setDragStarts(float x, float y) {
        startDragX = x;
        startDragY = y;
        Gdx.app.log(TAG, "setDragStarts startDragX: " + startDragX + " startDragY: " + startDragY);
    }

    public void setPinchStarts() {
        startPinchX = getX();
        startPinchY = getY();
        startScale = getScaleX();
        startRotation = getRotation();
        Gdx.app.log(TAG, "setPinchStarts startPinchX: " + startPinchX + " startPinchY: " +
                startPinchY + " startScale: " + startScale + " startRotation: " + startRotation);
    }

    public void move(float x, float y) {
        float diffX = x - startDragX;
        float diffY = y - startDragY;
        Gdx.app.log(TAG, "move getX: " + getX() + " getY: " + getY() + " x: " + x + " y: " +
                y + " diffX: " + diffX + " diffY: " + diffY);
        moveBy(diffX, diffY);
    }
}
