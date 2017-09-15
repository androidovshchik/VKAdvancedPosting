package rf.androidovshchik.vkadvancedposting.models;

import com.badlogic.gdx.graphics.Texture;

public abstract class Background extends Player {

    public Background(int index, Texture texture) {
        super(index, texture);
        setVisible(false);
    }

    public abstract void bind(int worldWidth, int worldHeight);
}