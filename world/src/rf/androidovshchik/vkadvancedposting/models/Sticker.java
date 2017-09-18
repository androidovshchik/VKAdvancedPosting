package rf.androidovshchik.vkadvancedposting.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;

import java.util.Random;

import rf.androidovshchik.vkadvancedposting.pools.ScaleToPool;

public class Sticker extends Player {

    private static final String TAG = Sticker.class.getSimpleName();

    private static final float ANIMATION_TIME_APPEAR = 0.15f;

    public static final int MAX_COUNT = 100;

    public static final float MIN_SCALE = 0.4f;
    public static final float MAX_SCALE = 2.0f;

    private static Random random = new Random();

    private static ScaleToPool scaleToPool = new ScaleToPool();

    public int index;

    public float startScale;
    public float startRotation;

    public Sticker(Texture texture, int type, String path, int worldWidth,
                   int worldHeight, int index) {
        super(texture, type, path);
        this.index = index;
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

    public void setPinchStarts() {
        this.startScale = getScaleX();
        this.startRotation = getRotation();
    }

    @Override
    public void setScale(float scale) {
        if (scale > MIN_SCALE && scale < MAX_SCALE) {
            super.setScale(scale);
        }
    }

    public void onAppear() {
        ScaleToAction scaleToAction = scaleToPool.obtain();
        scaleToAction.setPool(scaleToPool);
        scaleToAction.setScale(startScale);
        scaleToAction.setDuration(ANIMATION_TIME_APPEAR);
        addAction(scaleToAction);
    }
}
