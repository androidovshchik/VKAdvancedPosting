package rf.androidovshchik.vkadvancedposting.models.curtain;

import com.badlogic.gdx.graphics.Texture;

import rf.androidovshchik.vkadvancedposting.components.GdxLog;

public class WorldBottomCurtain extends WorldCurtain {

    private static final String TAG = WorldBottomCurtain.class.getSimpleName();

    public WorldBottomCurtain(Texture texture) {
        super(texture);
        GdxLog.print(TAG, "WorldBottomCurtain");
    }
}
