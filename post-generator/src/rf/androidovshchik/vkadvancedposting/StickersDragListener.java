package rf.androidovshchik.vkadvancedposting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class StickersDragListener extends InputListener {

    private static final String TAG = StickersDragListener.class.getSimpleName();

    public static final int NONE = -1;

    public int stickerIndex = NONE;

    private float startX, startY;

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (pointer == 0) {
            Gdx.app.log(TAG, "touchDown pointer0 x: " + x + " y: " + y);
            stickerIndex = (Integer) event.getListenerActor().getUserObject();
            startX = x;
            startY = y;
        }
        return true;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        if (pointer == 0) {
            Gdx.app.log(TAG, "touchDragged pointer0 x: " + x + " y: " + y);
            event.getListenerActor().moveBy(x - startX, y - startY);
        }
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (pointer == 0) {
            Gdx.app.log(TAG, "touchUp pointer0 x: " + x + " y: " + y);
            stickerIndex = NONE;
        }
    }
}
