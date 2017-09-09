package rf.androidovshchik.vkadvancedposting.photos;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;

public class AdapterPhotos extends RecyclerView.Adapter<ViewHolderPhotos> {

    public static final int MIN_ITEM_SIZE = ViewUtil.dp2px(78);

    private int itemsCount;
    private int itemSize;

    public AdapterPhotos(int itemsCount, int itemSize) {
        this.itemsCount = itemsCount;
        this.itemSize = itemSize;
    }

    @Override
    public ViewHolderPhotos onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView itemView = new ImageView(parent.getContext());
        itemView.setLayoutParams(new ViewGroup.LayoutParams(itemSize, itemSize));
        return new ViewHolderPhotos(itemView);
    }

    @Override
    public int getItemCount() {
        return itemsCount;
    }

    @Override
    public void onBindViewHolder(ViewHolderPhotos holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(Uri.parse("file:///android_asset/stickers/" + (position + 1) + ".png"))
                .into((ImageView) holder.itemView);
    }

    @Override
    public void onViewRecycled(ViewHolderPhotos holder) {
        if (holder != null) {
            ((ImageView) holder.itemView).setImageDrawable(null);
        }
        super.onViewRecycled(holder);
    }
}
