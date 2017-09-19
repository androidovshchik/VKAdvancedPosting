package rf.androidovshchik.vkadvancedposting.views.recyclerview.photos;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import rf.androidovshchik.vkadvancedposting.R;
import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
import rf.androidovshchik.vkadvancedposting.views.SelectableImageView;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.AdapterBase;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.ViewHolderBase;

public class AdapterPhotos extends AdapterBase {

    // may be larger or smaller due to keyboard height
    public static final int PHOTO_NONE = -1;
    public static final int ITEM_SIZE = ViewUtil.dp2px(84);

    public ArrayList<File> photoPaths;

    public int itemSize = ITEM_SIZE;

    public int photoPickersCount = 0;

    public int currentPhoto = PHOTO_NONE;

    public AdapterPhotos() {
        super(0);
        photoPaths = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return photoPickersCount + photoPaths.size();
    }

    @Override
    public ViewHolderBase onCreateViewHolder(ViewGroup parent, int viewType) {
        SelectableImageView itemView = new SelectableImageView(parent.getContext(), true);
        itemView.setId(ViewHolderBase.PHOTO_VIEW_ID);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(itemSize, itemSize));
        return new ViewHolderBase(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderBase holder, int position) {
        holder.itemView.getLayoutParams().height = itemSize;
        holder.itemView.getLayoutParams().width = itemSize;
        if (position <= 1) {
            holder.itemView.setBackgroundColor(0xffd2e3f1);
            ((SelectableImageView) holder.itemView).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Glide.with(holder.getApplicationContext())
                    .load(position == 0 ? R.drawable.ic_photopicker_albums :
                            R.drawable.ic_photopicker_camera)
                    .into((ImageView) holder.itemView);
        } else {
            // position > 1
            holder.itemView.setBackgroundColor(0xffffffff);
            ((SelectableImageView) holder.itemView).setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(holder.getApplicationContext())
                    .load(photoPaths.get(position - 2))
                    .into((ImageView) holder.itemView);
        }
        ((SelectableImageView) holder.itemView).isSelected = position == currentPhoto;
    }
}
