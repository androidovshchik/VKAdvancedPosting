package rf.androidovshchik.vkadvancedposting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnTouch;
import butterknife.Unbinder;
import rf.androidovshchik.vkadvancedposting.events.text.TextTouchEvent;
import rf.androidovshchik.vkadvancedposting.utils.EventUtil;
import rf.androidovshchik.vkadvancedposting.views.PostEditText;

public class FragmentPostText extends Fragment {

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_text, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPostEditText().requestFocus();
    }

    @OnTouch(R.id.postText)
    public boolean onPopupShadowTouch() {
        EventUtil.post(new TextTouchEvent());
        return false;
    }

    public PostEditText getPostEditText() {
        return (PostEditText) getView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}