package rf.androidovshchik.vkadvancedposting.views.recyclerview.themes;

import android.graphics.Color;
import android.support.annotation.ArrayRes;
import android.support.v4.content.ContextCompat;

import rf.androidovshchik.vkadvancedposting.R;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
import rf.androidovshchik.vkadvancedposting.views.ThemeImageView;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.AdapterBase;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.ViewHolderBase;

public class AdapterThemes extends AdapterBase {

    public static final int MIN_ITEM_SIZE = ViewUtil.dp2px(32);

    private static final int COLOR_EMPTY = Color.parseColor("#ebebeb");
    private static final int COLOR_ADD = Color.parseColor("#d2e3f1");

    @ArrayRes
    private final static int[] GRADIENT_THEME_IDS = new int[] {
            R.drawable.gradient_theme1,
            R.drawable.gradient_theme2,
            R.drawable.gradient_theme3,
            R.drawable.gradient_theme4,
            R.drawable.gradient_theme5
    };

    public AdapterThemes(int itemSize) {
        super(1 + GRADIENT_THEME_IDS.length + 1, itemSize);
    }

    @Override
    public void onBindViewHolder(ViewHolderBase holder, int position) {
        if (position == 0) {
            holder.itemView.setBackgroundColor(COLOR_EMPTY);
            ((ThemeImageView) holder.itemView).setImageResource(0);
        } else if (position == itemsCount - 1) {
            holder.itemView.setBackgroundColor(COLOR_ADD);
            ((ThemeImageView) holder.itemView).setImageResource(R.drawable.ic_toolbar_new);
        } else {
            holder.itemView.setBackground(ContextCompat.getDrawable(holder.getApplicationContext(),
                    GRADIENT_THEME_IDS[position - 1]));
            ((ThemeImageView) holder.itemView).setImageResource(0);
        }
    }
}