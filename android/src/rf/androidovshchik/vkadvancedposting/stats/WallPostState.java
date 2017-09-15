package rf.androidovshchik.vkadvancedposting.stats;

import android.os.Parcel;

public class WallPostState extends BaseState {

    public WallPostState() {
        super();
    }

    public WallPostState(Parcel in) {
        super();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<WallPostState> CREATOR = new Creator<WallPostState>() {

        public WallPostState createFromParcel(Parcel source) {
            return new WallPostState(source);
        }

        public WallPostState[] newArray(int size) {
            return new WallPostState[size];
        }
    };
}