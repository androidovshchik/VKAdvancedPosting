package rf.androidovshchik.vkadvancedposting;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class StickersDragListener extends InputListener {

    private static final String TAG = StickersDragListener.class.getSimpleName();

    private static final int FIRST_FINGER = 0;

    public int index = Sticker.NONE;

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (pointer == FIRST_FINGER) {
            GdxLog.print(TAG, "touchDown pointer0");
            Sticker sticker = (Sticker) event.getListenerActor();
            index = sticker.index;
            sticker.setDragStarts(x, y);
        }
        return true;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        if (pointer == FIRST_FINGER) {
            Sticker sticker = (Sticker) event.getListenerActor();
            if (!sticker.isPinching) {
                sticker.move(x, y);
            }
        }
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (pointer == FIRST_FINGER) {
            GdxLog.f(TAG, "touchDown pointer0 x: %f y: %f", x, y);
            index = Sticker.NONE;
        }
    }
}
