package rf.androidovshchik.vkadvancedposting.models;

import com.badlogic.gdx.graphics.Texture;

public class Background extends Player {

    private static final String TAG = Background.class.getSimpleName();

    public Background(Texture texture, int type, String path) {
        super(texture, type, path);
        setOrigin(getWidth() / 2, getHeight() / 2);
    }

    public Background(Texture texture, Record record) {
        super(texture, record);
    }

    public void top(int worldWidth, int worldHeight) {
        float scale = 1f * worldWidth / getWidth();
        float halfWidthDifference = getWidth() * (1f - scale) / 2;
        float halfHeightDifference = getHeight() * (1f - scale) / 2;
        setX(- halfWidthDifference);
        setY(worldHeight - getHeight() + halfHeightDifference);
        setScale(scale);
    }

    public void center(int worldWidth, int worldHeight, int rotation) {
        setRotation(- rotation);
        setX(worldWidth / 2 - getWidth() / 2);
        setY(worldHeight / 2 - getHeight() / 2);
        float minSize, scale;
        minSize = getHeight() > getWidth() ? getWidth() : getHeight();
        scale = 1f * worldHeight / minSize;
        setScale(scale);
    }

    public void bottom(int worldWidth, int worldHeight) {
        float scale = 1f * worldWidth / getWidth();
        float halfWidthDifference = getWidth() * (1f - scale) / 2;
        float halfHeightDifference = getHeight() * (1f - scale) / 2;
        setX(- halfWidthDifference);
        setY(- halfHeightDifference);
        setScale(scale);
    }
}