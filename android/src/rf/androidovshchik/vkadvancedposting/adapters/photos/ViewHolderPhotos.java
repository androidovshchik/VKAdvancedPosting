package rf.androidovshchik.vkadvancedposting.adapters.photos;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewHolderPhotos extends RecyclerView.ViewHolder {

    @IdRes
    private static final int VIEW_ID = 1;

    public ViewHolderPhotos(View itemView) {
        super(itemView);
        itemView.setId(VIEW_ID);
        ButterKnife.bind(this, itemView);
    }

    @OnClick(VIEW_ID)
    public void onItemClicked() {

    }
}