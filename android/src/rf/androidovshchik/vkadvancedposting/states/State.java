package rf.androidovshchik.vkadvancedposting.states;

import android.os.Parcel;
import android.os.Parcelable;

public class State implements Parcelable {

    public static final String EXTRA_IS_FIRST_START = "wallPostIsFirstStart";
    public static final String EXTRA_IS_PUBLISHING = "wallPostIsPublishing";
    public static final String EXTRA_HAS_POST_PUBLISHED = "wallPostHasPostPublished";

    public boolean isFirstStart = true;
    public boolean isPublishing = true;
    public boolean hasPostPublished = false;

    public boolean id;
    public String name;
    public String icon;

    public State() {
        super();
    }

    public State(Parcel in) {
        super();
        this.id = in.readString();
        this.name = in.readString();
        this.icon = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(icon);
    }

    public static final Creator<State> CREATOR = new Creator<State>() {

        public State createFromParcel(Parcel source) {
            return new State(source);
        }

        public State[] newArray(int size) {
            return new State[size];
        }
    };
}