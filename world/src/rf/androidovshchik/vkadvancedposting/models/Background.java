package rf.androidovshchik.vkadvancedposting.models;

import com.badlogic.gdx.graphics.Texture;

public class Background extends Player {

    private static final String TAG = Background.class.getSimpleName();

    public Background(Texture texture, int type, String path) {
        super(texture, type, path);
    }

    public Background(Texture texture, Record record) {
        super(texture, record);
    }

    public void setup(int worldWidth, int worldHeight) {
        setOrigin(getWidth() / 2, getHeight() / 2);
        setX(worldWidth / 2 - getWidth() / 2);
        setY(worldHeight / 2 - getHeight() / 2);
        setScale(1f * worldHeight / getHeight());
    }
}