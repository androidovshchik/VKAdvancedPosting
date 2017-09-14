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

    // may be larger or smaller due to keyboard height
    public static final int ITEM_SIZE = ViewUtil.dp2px(84);

    public ArrayList<File> photoPaths;

    public int photoPickersCount = 0;

    public AdapterPhotos() {
        super(0, ITEM_SIZE);
        photoPaths = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return photoPickersCount + photoPaths.size();
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
