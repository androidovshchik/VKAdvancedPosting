package rf.androidovshchik.vkadvancedposting.views.recyclerview.photos;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;

import rf.androidovshchik.vkadvancedposting.utils.ViewUtil;
import rf.androidovshchik.vkadvancedposting.views.SelectableImageView;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.AdapterBase;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.ViewHolderBase;

public class AdapterPhotos extends AdapterBase {

    // may be larger or smaller due to keyboard height
    public static final int ITEM_SIZE = ViewUtil.dp2px(84);

    private RequestOptions options;

    public ArrayList<File> photoPaths;

    public boolean hasUiPrepared = false;

    public int itemSize = ITEM_SIZE;

    public int currentPhoto = 0;

    public AdapterPhotos() {
        super(0);
        photoPaths = new ArrayList<>();
        options = new RequestOptions();
        options.centerCrop();
    }

    @Override
    public int getItemCount() {
        return photoPaths.size();
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
        Glide.with(holder.getApplicationContext())
                .load(photoPaths.get(position))
                .apply(options)
                .into((ImageView) holder.itemView);
        ((SelectableImageView) holder.itemView).isSelected = position == currentPhoto;
    }
}
