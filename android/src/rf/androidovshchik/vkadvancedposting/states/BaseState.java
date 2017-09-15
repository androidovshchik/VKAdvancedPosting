package rf.androidovshchik.vkadvancedposting.states;

import android.os.Parcelable;

public abstract class BaseState implements Parcelable {

    protected static final int TRUE = 1;
    protected static final int FALSE = 0;

    @Override
    public int describeContents() {
        return 0;
    }
}