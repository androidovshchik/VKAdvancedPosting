package rf.androidovshchik.vkadvancedposting.models.curtain;

import com.badlogic.gdx.graphics.Texture;

import rf.androidovshchik.vkadvancedposting.components.GdxLog;

public class WorldTopCurtain extends WorldCurtain {

    private static final String TAG = WorldTopCurtain.class.getSimpleName();

    public WorldTopCurtain(Texture texture) {
        super(texture);
        GdxLog.print(TAG, "WorldTopCurtain");
    }
}
