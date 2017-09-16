package rf.androidovshchik.vkadvancedposting.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

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

    @Override
    public void onModeTranslation(boolean isPostMode, int worldHeight, int worldHalfDifference) {
        if (type == TYPE_CENTER_BACKGROUND) {
            return;
        }
        MoveToAction moveToAction = moveToPool.obtain();
        moveToAction.setPool(moveToPool);
        switch (type) {
            case TYPE_TOP_BACKGROUND:
                moveToAction.setPosition(getX(),
                        getY() + worldHalfDifference * (isPostMode ? - 1 : 1));
                break;
            case TYPE_BOTTOM_BACKGROUND:
                moveToAction.setPosition(getX(),
                        getY() + worldHalfDifference * (isPostMode ? 1 : - 1));
                break;
            default:
                break;
        }
        moveToAction.setDuration(ANIMATION_TIME_MODE_TRANSLATION);
        addAction(moveToAction);
    }
}