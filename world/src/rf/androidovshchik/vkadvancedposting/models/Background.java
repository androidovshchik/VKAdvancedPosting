package rf.androidovshchik.vkadvancedposting.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public abstract class Background extends Player {

    public Background(int index) {
        super(index);
        setVisible(false);
    }

    public void setTexture(Texture texture, int worldWidth, int worldHeight) {
        TextureRegion textureRegion = new TextureRegion(texture);
        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion);
        //Sprite sprite = new Sprite(texture);
        //SpriteDrawable spriteDrawable = new SpriteDrawable(sprite);
        try {
            setDrawable(textureRegionDrawable);
            //bind(worldWidth, worldHeight);
        } finally {
            texture.dispose();
        }
    }

    protected abstract void bind(int worldWidth, int worldHeight);
}