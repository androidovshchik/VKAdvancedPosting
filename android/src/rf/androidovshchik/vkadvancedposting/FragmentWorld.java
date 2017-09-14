package rf.androidovshchik.vkadvancedposting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

public class FragmentWorld extends AndroidFragmentApplication {

    public World world;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        world = new World();
        return initializeForView(world);
    }
}