package rf.androidovshchik.vkadvancedposting;

import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;

public class FragmentWorld extends AndroidFragmentApplication {

    public World world;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Point window = ViewUtil.getWindow(getContext().getApplicationContext());
        int worldWidth = getResources().getDimensionPixelSize(R.dimen.world_width);
        int worldHeight;
        if (window.y > window.x) {
            worldHeight = Math.round(1f * window.y * worldWidth / window.x);
        } else {
            worldHeight = Math.round(1f * window.x * worldWidth / window.y);
        }
        world = new World(window.y, worldWidth, worldHeight);
        return initializeForView(world);
    }
}