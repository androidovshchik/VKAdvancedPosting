package rf.androidovshchik.vkadvancedposting;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Sticker extends Image {

    public boolean isScaling = false;
    public boolean isRotating = false;

    public float startScale = 1f;

    public Sticker(Texture texture) {
        super(texture);
    }
}
