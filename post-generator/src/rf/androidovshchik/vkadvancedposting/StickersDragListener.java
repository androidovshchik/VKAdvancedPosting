package rf.androidovshchik.vkadvancedposting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class StickersDragListener extends InputListener {

    private static final String TAG = StickersDragListener.class.getSimpleName();

    private float startX, startY;

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (pointer == 0) {
            Gdx.app.log(TAG, "touchDown x: " + x + " y: " + y);
            startX = x;
            startY = y;
        }
        return true;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        if (pointer == 0) {
            Gdx.app.log(TAG, "touchDragged x: " + x + " y: " + y);
            Actor actor = event.getListenerActor();
            actor.setPosition(actor.getX() + (x - startX), actor.getY() + (y - startY));
        }
    }
}
