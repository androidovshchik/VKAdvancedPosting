package rf.androidovshchik.vkadvancedposting.themes;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewHolderThemes extends RecyclerView.ViewHolder {

    @IdRes
    private static final int VIEW_ID = 1;

    public ViewHolderThemes(View itemView) {
        super(itemView);
        itemView.setId(VIEW_ID);
        ButterKnife.bind(this, itemView);
    }

    @OnClick(VIEW_ID)
    public void onItemClicked() {

    }
}