package rf.androidovshchik.vkadvancedposting.views.recyclerview.photos;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import rf.androidovshchik.vkadvancedposting.R;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.AdapterBase;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.ViewHolderBase;

public class AdapterPhotos extends AdapterBase {

    public static final int MIN_ITEM_SIZE = ViewUtil.dp2px(84);

    public ArrayList<File> photoPaths;

    public AdapterPhotos() {
        super(2, MIN_ITEM_SIZE);
        photoPaths = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return 2 + photoPaths.size();
    }

    @Override
    public void onBindViewHolder(ViewHolderBase holder, int position) {
        if (position <= 1) {
            holder.itemView.setBackground(VK_BACKGROUND);
            Glide.with(holder.getApplicationContext())
                    .load(position == 0 ? R.drawable.ic_photopicker_albums :
                            R.drawable.ic_photopicker_camera)
                    .into((ImageView) holder.itemView);
        } else {
            // position > 1
            holder.itemView.setBackground(null);
            Glide.with(holder.getApplicationContext())
                    .load(photoPaths.get(position - 2))
                    .into((ImageView) holder.itemView);
        }
    }
}
