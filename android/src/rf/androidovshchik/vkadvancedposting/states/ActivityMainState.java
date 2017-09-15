package rf.androidovshchik.vkadvancedposting.states;

import android.os.Parcel;

public class ActivityMainState extends BaseState {

    public boolean hasPostMode = true;

    public boolean hasKeyboardShown = true;
    public boolean hasStickersShown = false;
    public boolean hasWallPostShown = false;

    public String lastChosenPhoto;

    public ActivityMainState() {
        super();
    }

    public ActivityMainState(Parcel in) {
        super();
        this.hasPostMode = in.readInt() == TRUE;
        this.hasKeyboardShown = in.readInt() == TRUE;
        this.hasStickersShown = in.readInt() == TRUE;
        this.hasWallPostShown = in.readInt() == TRUE;
        this.lastChosenPhoto = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(hasPostMode ? TRUE : FALSE);
        dest.writeInt(hasKeyboardShown ? TRUE : FALSE);
        dest.writeInt(hasStickersShown ? TRUE : FALSE);
        dest.writeInt(hasWallPostShown ? TRUE : FALSE);
        dest.writeString(lastChosenPhoto);
    }

    public static final Creator<ActivityMainState> CREATOR = new Creator<ActivityMainState>() {

        public ActivityMainState createFromParcel(Parcel source) {
            return new ActivityMainState(source);
        }

        public ActivityMainState[] newArray(int size) {
            return new ActivityMainState[size];
        }
    };
}