package rf.androidovshchik.vkadvancedposting.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;

import java.util.Random;

import rf.androidovshchik.vkadvancedposting.components.GdxLog;
import rf.androidovshchik.vkadvancedposting.pools.MoveToPool;
import rf.androidovshchik.vkadvancedposting.pools.RotateToPool;
import rf.androidovshchik.vkadvancedposting.pools.ScaleToPool;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;

public class Sticker extends Player {

    private static final String TAG = Sticker.class.getSimpleName();

    private static Random random = new Random();

    public static final int NONE = -1;

    public static final float MAX_SCALE = 2.0f;

    public boolean isPinching = false;

    public float baseRotation = 0.0f;
    public float cropAspectRatio;
    public float defaultScale = 1.0f;

    public float startDragX;
    public float startDragY;
    public float startPinchX;
    public float startPinchY;
    public float startScale;
    public float startRotation;

    private MoveToPool moveToPool;
    private ScaleToPool scaleToPool;
    private RotateToPool rotateToPool;

    public Sticker(int index, Texture texture, int worldWidth, int worldHeight) {
        super(index, texture);
        GdxLog.d(TAG, "%d Sticker description", index);
        moveToPool = new MoveToPool();
        scaleToPool = new ScaleToPool();
        rotateToPool = new RotateToPool();
        setOrigin(getWidth() / 2, getHeight() / 2);
        // scale < 0.8f & > 0.4f
        float scale = random.nextFloat() * 0.8f;
        if (scale < 0.4f) {
            scale = 0.4f;
        }
        setScale(scale);
        GdxLog.print(TAG, "scale: " + scale);
        float realWidth = getWidth() * scale;
        float realHeight= getHeight() * scale;
        float halfWidthDifference = getWidth() * (1f - scale) / 2;
        float halfHeightDifference = getHeight() * (1f - scale) / 2;
        float x;
        float y;
        float rotation;
        // x
        if (random.nextFloat() > 0.5f) {
            x = worldWidth - (realWidth + halfWidthDifference) * (1.5f - random.nextFloat() / 3);
            GdxLog.f(TAG, "centerX > 0.5f: %f", x);
        } else {
            x = random.nextFloat() * realWidth / 3 - halfWidthDifference;
            GdxLog.f(TAG, "centerX < 0.5f: %f", x);
        }
        // y
        if (random.nextFloat() > 0.5f) {
            y = worldHeight - (realHeight + halfHeightDifference) * (1.5f - random.nextFloat() / 3);
            GdxLog.f(TAG, "centerY > 0.5f: %f", y);
        } else {
            y = random.nextFloat() * realHeight / 3 - halfHeightDifference;
            GdxLog.f(TAG, "centerY < 0.5f: %f", y);
        }
        setPosition(x, y);
        //setRotation(rotation);
        GdxLog.f(TAG, "x: %f", x);
        GdxLog.f(TAG, "y: %f", y);
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
