package rf.androidovshchik.vkadvancedposting;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Sticker extends Image {

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
    }

    public void setDragStarts(float x, float y) {
        startDragX = x;
        startDragY = y;
    }

    public void setPinchStarts() {
        startPinchX = getX();
        startPinchY = getY();
        startScale = getScaleX();
        startRotation = getRotation();
    }
}
