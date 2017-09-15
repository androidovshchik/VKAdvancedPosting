package rf.androidovshchik.vkadvancedposting.views.recyclerview.stickers;

import android.net.Uri;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
import rf.androidovshchik.vkadvancedposting.views.SelectableImageView;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.AdapterBase;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.ViewHolderBase;

public class AdapterStickers extends AdapterBase {

    public static final int MIN_ITEM_SIZE = ViewUtil.dp2px(78);

    public int itemSize;

    public AdapterStickers(int itemsCount, int itemSize) {
        super(itemsCount);
        this.itemSize = itemSize;
    }

    @Override
    public ViewHolderBase onCreateViewHolder(ViewGroup parent, int viewType) {
        SelectableImageView itemView = new SelectableImageView(parent.getContext(), false);
        itemView.setId(ViewHolderBase.STICKER_VIEW_ID);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(itemSize, itemSize));
        return new ViewHolderBase(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderBase holder, int position) {
        Glide.with(holder.getApplicationContext())
                .load(Uri.parse("file:///android_asset/stickers/" + (position + 1) + ".png"))
                .into((SelectableImageView) holder.itemView);
    }
}
