package rf.androidovshchik.vkadvancedposting.pools;

import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Pool;

public class MoveToPool extends Pool<MoveToAction> {

    @Override
    protected MoveToAction newObject() {
        return new MoveToAction();
    }
}