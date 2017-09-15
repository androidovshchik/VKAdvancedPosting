package rf.androidovshchik.vkadvancedposting.views.recyclerview.base;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;

import rf.androidovshchik.vkadvancedposting.views.SelectableImageView;

public abstract class AdapterBase extends RecyclerView.Adapter<ViewHolderBase> {

    protected static final ColorDrawable VK_BACKGROUND = new ColorDrawable(0xffd2e3f1);

    public int itemsCount;

    public AdapterBase(int itemsCount) {
        this.itemsCount = itemsCount;
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
