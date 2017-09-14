package rf.androidovshchik.vkadvancedposting.views.recyclerview.themes;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ArrayRes;
import android.support.v4.content.ContextCompat;

import rf.androidovshchik.vkadvancedposting.R;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
import rf.androidovshchik.vkadvancedposting.views.SelectableImageView;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.AdapterBase;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.ViewHolderBase;

public class AdapterThemes extends AdapterBase {

    public static final int ITEM_SIZE = ViewUtil.dp2px(32);
    // item size minus resource height and extra margin in image
    public static final int MAX_PADDING = ViewUtil.dp2px((32 - 14 - 8) / 2);

    private static final ColorDrawable EMPTY_BACKGROUND = new ColorDrawable(0xffebebeb);

    @ArrayRes
    private final static int[] GRADIENT_IDS = new int[] {
            R.drawable.gradient_theme1,
            R.drawable.gradient_theme2,
            R.drawable.gradient_theme3,
            R.drawable.gradient_theme4,
            R.drawable.gradient_theme5
    };

    @ArrayRes
    private final static int[] THUMB_IDS = new int[] {
            R.drawable.thumb_beach,
            R.drawable.thumb_stars
    };

    public AdapterThemes() {
        super(1 + GRADIENT_IDS.length + THUMB_IDS.length + 1, ITEM_SIZE);
    }

    @Override
    public void onBindViewHolder(ViewHolderBase holder, int position) {
        if (position == 0) {
            holder.itemView.setPadding(0, 0, 0, 0);
            holder.itemView.setBackground(EMPTY_BACKGROUND);
            ((SelectableImageView) holder.itemView).setImageResource(0);
        } else if (position < GRADIENT_IDS.length + 1) {
            holder.itemView.setPadding(0, 0, 0, 0);
            holder.itemView.setBackground(ContextCompat.getDrawable(holder.getApplicationContext(),
                    GRADIENT_IDS[position - 1]));
            ((SelectableImageView) holder.itemView).setImageResource(0);
        } else if (position < itemsCount - 1) {
            holder.itemView.setPadding(0, 0, 0, 0);
            holder.itemView.setBackground(null);
            ((SelectableImageView) holder.itemView).setImageResource(THUMB_IDS[position -
                    GRADIENT_IDS.length - 1]);
        } else {
            // position == itemsCount - 1
            holder.itemView.setPadding(MAX_PADDING, MAX_PADDING, MAX_PADDING, MAX_PADDING);
            holder.itemView.setBackground(VK_BACKGROUND);
            ((SelectableImageView) holder.itemView).setImageResource(R.drawable.ic_toolbar_new);
        }
    }
}
