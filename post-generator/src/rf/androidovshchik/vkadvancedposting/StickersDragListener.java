package rf.androidovshchik.vkadvancedposting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class StickersDragListener extends InputListener {

    private static final String TAG = StickersDragListener.class.getSimpleName();

    public int index = Sticker.NONE;

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (pointer == 0) {
            Gdx.app.log(TAG, "touchDown pointer0 x: " + x + " y: " + y);
            Sticker sticker = (Sticker) event.getListenerActor();
            sticker.setDragStarts(x, y);
            index = sticker.index;
        }
        return true;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        if (pointer == 0) {
            Sticker sticker = (Sticker) event.getListenerActor();
            if (!sticker.isPinching) {
                Gdx.app.log(TAG, "touchDragged pointer0 x: " + x + " y: " + y);
                sticker.moveBy(x - sticker.startDragX, y - sticker.startDragY);
            }
        }
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (pointer == 0) {
            Gdx.app.log(TAG, "touchUp pointer0 x: " + x + " y: " + y);
            index = Sticker.NONE;
        }
    }
}
