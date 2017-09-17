package rf.androidovshchik.vkadvancedposting.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import rf.androidovshchik.vkadvancedposting.pools.MoveToPool;

public abstract class Player extends Image {

    public static final int TYPE_STICKER = 1;

    public static final int TYPE_TOP_BACKGROUND = 20;
    public static final int TYPE_CENTER_BACKGROUND = 21;
    public static final int TYPE_BOTTOM_BACKGROUND = 22;

    protected static final float ANIMATION_TIME_MODE_TRANSLATION = 0.3f;

    protected static MoveToPool moveToPool = new MoveToPool();

    public int type;

    public String path;

    public Player(Texture texture, int type, String path) {
        super(texture);
        this.type = type;
        this.path = path;
    }

    public Player(Texture texture, Record record) {
        super(texture);
        this.type = record.type;
        this.path = record.path;
    }

    public Record save2Record() {
        Record record = new Record();
        record.type = type;
        record.path = path;
        record.x = getX();
        record.y = getY();
        record.scale = getScaleX();
        record.rotation = getRotation();
        return record;
    }
}
