package rf.androidovshchik.vkadvancedposting.models.curtain;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class WorldCurtain extends Image {

    public WorldCurtain(Texture texture) {
        super(texture);
    }

    public static Texture createTexture(int worldWidth, int worldHalfDifference) {
        Pixmap pixmap = new Pixmap(worldWidth, worldHalfDifference, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fillRectangle(0, 0, worldWidth, worldHalfDifference);
        try {
            return new Texture(pixmap);
        } finally {
            pixmap.dispose();
        }
    }
}
