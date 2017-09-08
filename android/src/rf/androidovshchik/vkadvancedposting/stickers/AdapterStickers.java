package rf.androidovshchik.vkadvancedposting.stickers;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;

public class AdapterStickers extends RecyclerView.Adapter<ViewHolderStickers> {

    public static final int ITEM_SIZE = ViewUtil.dp2px(78);

    private int itemsCount;

    public AdapterStickers(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    @Override
    public ViewHolderStickers onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView itemView = new ImageView(parent.getContext());
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ITEM_SIZE, ITEM_SIZE));
        return new ViewHolderStickers(itemView);
    }

    @Override
    public int getItemCount() {
        return itemsCount;
    }

    @Override
    public void onBindViewHolder(ViewHolderStickers holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(Uri.parse("file:///android_asset/stickers/" + (position + 1) + ".png"))
                .into((ImageView) holder.itemView);
    }

    @Override
    public void onViewRecycled(ViewHolderStickers holder) {
        if (holder != null) {
            ((ImageView) holder.itemView).setImageDrawable(null);
        }
        super.onViewRecycled(holder);
    }
}
