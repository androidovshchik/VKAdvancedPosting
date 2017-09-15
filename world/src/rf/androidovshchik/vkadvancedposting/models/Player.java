package rf.androidovshchik.vkadvancedposting.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Player extends Image {

    public static final int NONE = -1;

    public int index;

    public Player(int index) {
        super(new TextureRegionDrawable());
        this.index = index;
    }

    public Player(int index, Texture texture) {
        super(texture);
        this.index = index;
    }
}
