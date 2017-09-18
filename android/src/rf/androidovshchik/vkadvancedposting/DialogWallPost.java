package rf.androidovshchik.vkadvancedposting;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.api.photo.VKImageParameters;
import com.vk.sdk.api.photo.VKUploadImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rf.androidovshchik.vkadvancedposting.callbacks.VKRequestCallback;
import rf.androidovshchik.vkadvancedposting.events.VKResponseEvent;
import rf.androidovshchik.vkadvancedposting.utils.DiskUtil;
import rf.androidovshchik.vkadvancedposting.views.layout.WallPostLayout;

public class DialogWallPost extends DialogFragment {

    @BindView(R.id.wallPostLayout)
    protected WallPostLayout wallPostLayout;

    private Unbinder unbinder;

    private VKRequest requestUploadImage;
    private VKRequest requestWallPost;
    private VKRequestCallback vkRequestCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Dialog_WallPost);
        vkRequestCallback = new VKRequestCallback();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_wall_post, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        Window window = getDialog().getWindow();
        if (wallPostLayout.isFirstStart && window != null) {
            ObjectAnimator appearing = ObjectAnimator.ofFloat(window.getDecorView(),
                    "alpha", 0f, 1f);
            appearing.setRepeatCount(Animation.ABSOLUTE);
            appearing.setInterpolator(new AccelerateInterpolator());
            appearing.setDuration(300);
            appearing.start();
        }
        uploadImage();
    }

    @SuppressWarnings("unused")
    @Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
    public void onVKResponseEvent(VKResponseEvent event) {
        if (event.isSuccessful) {
            if (event.isPhotoUploadRequest) {
                wallPostLayout.onPublishFailed();
                //makeWallPost(new VKAttachments(((VKPhotoArray) event.parsedModel).get(0)));
            } else {
                wallPostLayout.onPublishFailed();
            }
        } else {
            wallPostLayout.onPublishFailed();
        }
    }

    @OnClick(R.id.actionCancel)
    protected void onCancel() {
        wallPostLayout.onPublishCancel();
        dismissAllowingStateLoss();
    }

    @OnClick(R.id.actionBackwards)
    protected void onBackwards() {
        dismissAllowingStateLoss();
    }

    private void uploadImage() {
        if (VKAccessToken.currentToken() == null) {
            return;
        }
        long userId = Long.parseLong(VKAccessToken.currentToken().userId);
        VKRequest request = VKApi.uploadWallPhotoRequest(new VKUploadImage(DiskUtil.getPhoto(getContext()),
                        VKImageParameters.pngImage()), userId, 0);
        request.attempts = 3;
        request.executeWithListener(vkRequestCallback);
    }

    private void makeWallPost(VKAttachments attachments) {
        VKRequest request = VKApi.wall()
                .post(VKParameters.from(VKApiConst.ATTACHMENTS, attachments));
        request.attempts = 3;
        request.executeWithListener(vkRequestCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}