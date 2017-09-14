package rf.androidovshchik.vkadvancedposting.pools;

import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.utils.Pool;

public class ScaleToPool extends Pool<ScaleToAction> {

    @Override
    protected ScaleToAction newObject() {
        return new ScaleToAction();
    }
}