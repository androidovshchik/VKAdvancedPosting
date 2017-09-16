package rf.androidovshchik.vkadvancedposting.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class Curtain extends Player {

    private static final String TAG = Curtain.class.getSimpleName();

    public static final int TOP = 0;
    public static final int BOTTOM = 1;

    public Curtain(Texture texture, int type, String path) {
        super(texture, type, path);
    }

    @Override
    public void onModeTranslation(boolean isPostMode, int worldHeight, int worldHalfDifference) {
        MoveToAction moveToAction = moveToPool.obtain();
        moveToAction.setPool(moveToPool);
        switch (type) {
            case TYPE_TOP_CURTAIN:
                moveToAction.setPosition(getX(),
                        getY() + worldHalfDifference * (isPostMode ? - 1 : 1));
                break;
            case TYPE_BOTTOM_CURTAIN:
                moveToAction.setPosition(getX(),
                        getY() + worldHalfDifference * (isPostMode ? 1 : - 1));
                break;
            default:
                break;
        }
        moveToAction.setDuration(ANIMATION_TIME_MODE_TRANSLATION);
        addAction(moveToAction);
    }

    public static Texture createTexture(int worldWidth, int worldHalfDifference) {
        Pixmap pixmap = new Pixmap(worldWidth, worldHalfDifference, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fillRectangle(0, 0, worldWidth, worldHalfDifference);
        try {
            return new Texture(pixmap);
        } finally {
            pixmap.dispose();
        }
    }
}
