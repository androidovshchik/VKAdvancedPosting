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
        Point screen = ViewUtil.getScreen(getContext().getApplicationContext());
        Point window = ViewUtil.getWindow(getContext().getApplicationContext());
        int worldWidth = getResources().getDimensionPixelSize(R.dimen.world_width);
        int worldHeight;
        if (screen.y > screen.x) {
            worldHeight = Math.round(1f * screen.y * worldWidth / screen.x);
        } else {
            worldHeight = Math.round(1f * screen.x * worldWidth / screen.y);
        }
        world = new World(window.y, worldWidth, worldHeight);
        return initializeForView(world);
    }
}