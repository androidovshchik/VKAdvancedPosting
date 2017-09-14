package rf.androidovshchik.vkadvancedposting.views.recyclerview.base;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import rf.androidovshchik.vkadvancedposting.views.SelectableImageView;

public abstract class AdapterBase extends RecyclerView.Adapter<ViewHolderBase> {

    protected static final ColorDrawable VK_BACKGROUND = new ColorDrawable(0xffd2e3f1);

    public int itemSize;
    public int itemsCount;

    public AdapterBase(int itemsCount, int itemSize) {
        this.itemsCount = itemsCount;
        this.itemSize = itemSize;
    }

    @Override
    public ViewHolderBase onCreateViewHolder(ViewGroup parent, int viewType) {
        SelectableImageView itemView = new SelectableImageView(parent.getContext());
        itemView.setLayoutParams(new ViewGroup.LayoutParams(itemSize, itemSize));
        return new ViewHolderBase(itemView);
    }

    @Override
    public int getItemCount() {
        return itemsCount;
    }

    @Override
    public void onViewRecycled(ViewHolderBase holder) {
        if (holder != null) {
            ((SelectableImageView) holder.itemView).setImageDrawable(null);
        }
        super.onViewRecycled(holder);
    }
}
