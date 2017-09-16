package rf.androidovshchik.vkadvancedposting.callbacks;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import rf.androidovshchik.vkadvancedposting.components.GdxLog;
import rf.androidovshchik.vkadvancedposting.models.Sticker;

public class StickersDragCallback extends InputListener {

    private static final String TAG = StickersDragCallback.class.getSimpleName();

    private static final int FIRST_FINGER = 0;

    public int index = -1;

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (pointer == FIRST_FINGER) {
            GdxLog.print(TAG, "touchDown pointer0");
            Sticker sticker = (Sticker) event.getListenerActor();
            index = -1;
            sticker.setDragStarts(x, y);
        }
        return true;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        if (pointer == FIRST_FINGER) {
            Sticker sticker = (Sticker) event.getListenerActor();
            if (!sticker.isPinching) {
                GdxLog.f(TAG, "move x: %f y: %f", x, y);
                sticker.moveBy(x - sticker.startDragX, y - sticker.startDragY);
            }
        }
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (pointer == FIRST_FINGER) {
            GdxLog.f(TAG, "touchDown pointer0 x: %f y: %f", x, y);
            index = -1;
        }
    }
}
