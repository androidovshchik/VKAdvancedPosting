package rf.androidovshchik.vkadvancedposting.themes;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;

public class AdapterThemes extends RecyclerView.Adapter<ViewHolderThemes> {

    public static final int MIN_ITEM_SIZE = ViewUtil.dp2px(78);

    private int itemsCount;
    private int itemSize;

    public AdapterThemes(int itemsCount, int itemSize) {
        this.itemsCount = itemsCount;
        this.itemSize = itemSize;
    }

    @Override
    public ViewHolderThemes onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView itemView = new ImageView(parent.getContext());
        itemView.setLayoutParams(new ViewGroup.LayoutParams(itemSize, itemSize));
        return new ViewHolderThemes(itemView);
    }

    @Override
    public int getItemCount() {
        return itemsCount;
    }

    @Override
    public void onBindViewHolder(ViewHolderThemes holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(Uri.parse("file:///android_asset/stickers/" + (position + 1) + ".png"))
                .into((ImageView) holder.itemView);
    }

    @Override
    public void onViewRecycled(ViewHolderThemes holder) {
        if (holder != null) {
            ((ImageView) holder.itemView).setImageDrawable(null);
        }
        super.onViewRecycled(holder);
    }
}
