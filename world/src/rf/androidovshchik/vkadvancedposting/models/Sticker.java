package rf.androidovshchik.vkadvancedposting.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;

import java.util.Random;

import rf.androidovshchik.vkadvancedposting.components.GdxLog;
import rf.androidovshchik.vkadvancedposting.pools.RotateToPool;
import rf.androidovshchik.vkadvancedposting.pools.ScaleToPool;

public class Sticker extends Player {

    private static final String TAG = Sticker.class.getSimpleName();

    private static final float ANIMATION_TIME_APPEAR = 0.15f;

    public static final int MAX_COUNT = 100;

    public static final float MIN_SCALE = 0.4f;
    public static final float MAX_SCALE = 2.0f;

    private static Random random = new Random();

    private static ScaleToPool scaleToPool = new ScaleToPool();
    private static RotateToPool rotateToPool = new RotateToPool();

    public boolean isPinching = false;

    public float startDragX;
    public float startDragY;
    public float startPinchX;
    public float startPinchY;

    public float startScale;
    public float startRotation;

    public Sticker(Texture texture, int type, String path, int worldWidth, int worldHeight) {
        super(texture, type, path);
        setOrigin(getWidth() / 2, getHeight() / 2);
        setScale(MIN_SCALE);
        // 0.6f < scale < 0.8f
        startScale = 1f * random.nextInt(20) / 100 + 0.6f;
        float realWidth = getWidth() * startScale;
        float realHeight= getHeight() * startScale;
        float halfWidthDifference = getWidth() * (1f - startScale) / 2;
        float halfHeightDifference = getHeight() * (1f - startScale) / 2;
        float x, y;
        boolean leftPosition;
        if (random.nextFloat() > 0.5f) {
            leftPosition = false;
            x = worldWidth - halfWidthDifference - realWidth * (1f + random.nextFloat() / 3);
        } else {
            leftPosition = true;
            x = random.nextFloat() * realWidth / 3 - halfWidthDifference;
        }
        setX(x);
        if (random.nextFloat() > 0.5f) {
            startRotation = leftPosition ? - random.nextInt(45) : random.nextInt(45);
            y = worldHeight - halfHeightDifference - realHeight * (1f + random.nextFloat() / 3);
        } else {
            startRotation = leftPosition ? random.nextInt(45) : - random.nextInt(45);
            y = random.nextFloat() * realHeight / 3 - halfHeightDifference;
        }
        setRotation(startRotation);
        setY(y);
        onAppear();
    }

    public void setDragStarts(float x, float y) {
        startDragX = x;
        startDragY = y;
        GdxLog.f(TAG, "setDragStarts startDragX: %f startDragY: %f", startDragX, startDragY);
    }

    public void setPinchStarts(float x, float y) {
        startPinchX = x;
        startPinchY = y;
        GdxLog.f(TAG, "setPinchStarts startPinchX: %f startPinchY: %f startScale: %f startRotation: %f",
                startPinchX, startPinchY, startScale, startRotation);
    }

    public void onAppear() {
        ScaleToAction scaleToAction = scaleToPool.obtain();
        scaleToAction.setPool(scaleToPool);
        scaleToAction.setScale(startScale);
        scaleToAction.setDuration(ANIMATION_TIME_APPEAR);
        addAction(scaleToAction);
    }

    public void onPinch(float x, float y, float scale, float rotation) {
        /*MoveToAction moveToAction = moveToPool.obtain();
        moveToAction.setPool(moveToPool);
        moveToAction.setPosition(x, y);
        ScaleToAction scaleToAction = scaleToPool.obtain();
        scaleToAction.setPool(scaleToPool);
        scaleToAction.setScale(scale);
        RotateToAction rotateToAction = rotateToPool.obtain();
        rotateToAction.setPool(rotateToPool);
        rotateToAction.setRotation(rotation);
        addAction(parallel(moveToAction, scaleToAction, rotateToAction));*/
    }
}
