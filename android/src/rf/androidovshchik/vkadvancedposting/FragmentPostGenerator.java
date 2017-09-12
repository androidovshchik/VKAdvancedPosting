package rf.androidovshchik.vkadvancedposting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

public class FragmentPostGenerator extends AndroidFragmentApplication {

    public PostGenerator postGenerator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        postGenerator = new PostGenerator();
        return initializeForView(postGenerator);
    }
}