package rf.androidovshchik.vkadvancedposting.views.recyclerview.stickers;

import android.net.Uri;

import com.bumptech.glide.Glide;

import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
import rf.androidovshchik.vkadvancedposting.views.SelectableImageView;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.AdapterBase;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.ViewHolderBase;

public class AdapterStickers extends AdapterBase {

    public static final int MIN_ITEM_SIZE = ViewUtil.dp2px(78);

    public AdapterStickers(int itemsCount, int itemSize) {
        super(itemsCount, itemSize, false);
    }

    @Override
    public void onBindViewHolder(ViewHolderBase holder, int position) {
        Glide.with(holder.getApplicationContext())
                .load(Uri.parse("file:///android_asset/stickers/" + (position + 1) + ".png"))
                .into((SelectableImageView) holder.itemView);
    }
}
