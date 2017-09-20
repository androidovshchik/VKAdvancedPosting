package rf.androidovshchik.vkadvancedposting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

import rf.androidovshchik.vkadvancedposting.callbacks.StickersPressListener;
import rf.androidovshchik.vkadvancedposting.events.world.StickerPressEvent;
import rf.androidovshchik.vkadvancedposting.utils.EventUtil;

public class FragmentWorld extends AndroidFragmentApplication {

    public World world;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int worldWidth = getResources().getDimensionPixelSize(R.dimen.world_width);
        world = new World(BuildConfig.DEBUG, worldWidth, worldWidth, new StickersPressListener() {
            @Override
            public void onStickerLongPress() {
                EventUtil.post(new StickerPressEvent());
            }
        });
        return initializeForView(world);
    }
}