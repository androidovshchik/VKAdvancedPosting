package rf.androidovshchik.vkadvancedposting.themes;

import android.support.annotation.ArrayRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import rf.androidovshchik.vkadvancedposting.R;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;

public class AdapterThemes extends RecyclerView.Adapter<ViewHolderThemes> {

    public static final int ITEM_SIZE = ViewUtil.dp2px(78);

    @ArrayRes
    private final static int[] GRADIENT_THEME_IDS = new int[] {
            R.drawable.gradient_theme1,
            R.drawable.gradient_theme2,
            R.drawable.gradient_theme3,
            R.drawable.gradient_theme4,
            R.drawable.gradient_theme5
    };

    public AdapterThemes() {}

    @Override
    public ViewHolderThemes onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView itemView = new ImageView(parent.getContext());
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ITEM_SIZE, ITEM_SIZE));
        return new ViewHolderThemes(itemView);
    }

    @Override
    public int getItemCount() {
        return 1 + GRADIENT_THEME_IDS.length + 1;
    }

    @Override
    public void onBindViewHolder(ViewHolderThemes holder, int position) {

    }

    @Override
    public void onViewRecycled(ViewHolderThemes holder) {
        if (holder != null) {
            ((ImageView) holder.itemView).setImageDrawable(null);
        }
        super.onViewRecycled(holder);
    }
}
