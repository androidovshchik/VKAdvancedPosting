package rf.androidovshchik.vkadvancedposting.pools;

import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.utils.Pool;

public class RotateToPool extends Pool<RotateToAction> {

    @Override
    protected RotateToAction newObject() {
        return new RotateToAction();
    }
}