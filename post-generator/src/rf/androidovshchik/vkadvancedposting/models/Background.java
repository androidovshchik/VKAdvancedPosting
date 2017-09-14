package rf.androidovshchik.vkadvancedposting.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import rf.androidovshchik.vkadvancedposting.components.GdxLog;
import rf.androidovshchik.vkadvancedposting.pools.MoveToPool;
import rf.androidovshchik.vkadvancedposting.pools.RotateToPool;
import rf.androidovshchik.vkadvancedposting.pools.ScaleToPool;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;

public class Background extends Image {

    private static final String TAG = Background.class.getSimpleName();

    public static final int NONE = -1;

    public int index;

    public boolean isPinching = false;

    public float startDragX;
    public float startDragY;
    public float startPinchX;
    public float startPinchY;
    public float startScale;
    public float startRotation;

    private MoveToPool moveToPool;
    private ScaleToPool scaleToPool;
    private RotateToPool rotateToPool;

    public Background(int index, Texture texture) {
        super(texture);
        GdxLog.d(TAG, "Sticker index: %d", index);
        this.index = index;
        moveToPool = new MoveToPool();
        scaleToPool = new ScaleToPool();
        rotateToPool = new RotateToPool();
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

    public void onPinch(float x, float y, float scale, float rotation) {
        MoveToAction moveToAction = moveToPool.obtain();
        moveToAction.setPool(moveToPool);
        moveToAction.setPosition(x, y);
        ScaleToAction scaleToAction = scaleToPool.obtain();
        scaleToAction.setPool(scaleToPool);
        scaleToAction.setScale(scale);
        RotateToAction rotateToAction = rotateToPool.obtain();
        rotateToAction.setPool(rotateToPool);
        rotateToAction.setRotation(rotation);
        addAction(parallel(moveToAction, scaleToAction, rotateToAction));
        act(0);
    }
}
