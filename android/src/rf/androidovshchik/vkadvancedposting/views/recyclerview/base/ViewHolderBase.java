package rf.androidovshchik.vkadvancedposting.views.recyclerview.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import rf.androidovshchik.vkadvancedposting.events.clicks.PhotoClickEvent;
import rf.androidovshchik.vkadvancedposting.events.clicks.StickerClickEvent;
import rf.androidovshchik.vkadvancedposting.events.clicks.ThemeClickEvent;
import rf.androidovshchik.vkadvancedposting.utils.EventUtil;

public class ViewHolderBase extends RecyclerView.ViewHolder {

    @IdRes
    public static final int THEME_VIEW_ID = 1;
    @IdRes
    public static final int STICKER_VIEW_ID = 2;
    @IdRes
    public static final int PHOTO_VIEW_ID = 3;

    public ViewHolderBase(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Optional
    @SuppressWarnings("unused")
    @OnClick(THEME_VIEW_ID)
    public void onThemeClicked() {
        EventUtil.postSticky(new ThemeClickEvent(getAdapterPosition()));
    }

    @Optional
    @SuppressWarnings("unused")
    @OnClick(STICKER_VIEW_ID)
    public void onStickerClicked() {
        EventUtil.postSticky(new StickerClickEvent(getAdapterPosition()));
    }

    @Optional
    @SuppressWarnings("unused")
    @OnClick(PHOTO_VIEW_ID)
    public void onPhotoClicked() {
        EventUtil.postSticky(new PhotoClickEvent(getAdapterPosition()));
    }

    public Context getApplicationContext() {
        return itemView.getContext().getApplicationContext();
    }
}