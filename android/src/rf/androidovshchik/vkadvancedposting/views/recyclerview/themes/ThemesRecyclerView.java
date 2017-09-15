package rf.androidovshchik.vkadvancedposting.views.recyclerview.themes;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import rf.androidovshchik.vkadvancedposting.R;
import rf.androidovshchik.vkadvancedposting.views.recyclerview.base.BaseRecyclerView;

public class ThemesRecyclerView extends BaseRecyclerView {

    private static final int GRADIENTS_COUNT = 5;

    private static final int THUMBS_COUNT = 2;

    public static GradientDrawable[] GRADIENT_DRAWABLES = new GradientDrawable[GRADIENTS_COUNT];

    public static String[][] GRADIENT_HEXES = new String[GRADIENTS_COUNT][2];

    public final static int[] THUMB_IDS = new int[] {
            R.drawable.thumb_beach,
            R.drawable.thumb_stars
    };

    public static String[][] THUMB_PATHS = new String[THUMBS_COUNT][3];

    public AdapterThemes adapter;

    public ThemesRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemesRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGradient(0, "30f3d2", "2e7ae6");
        initGradient(1, parseColor(R.color.sickly_yellow), parseColor(R.color.mid_green));
        initGradient(2, parseColor(R.color.sun_yellow), parseColor(R.color.orangish));
        initGradient(3, parseColor(R.color.reddish_pink), parseColor(R.color.berry));
        initGradient(4, parseColor(R.color.light_lavender), parseColor(R.color.dark_periwinkle));
        THUMB_PATHS[0] = new String[] {
                "beach/bg_beach_top.png", "beach/bg_beach_center.png", "beach/bg_beach_bottom.png"
        };
        THUMB_PATHS[1] = new String[] {
                null, "stars/bg_stars_center.png", null
        };
    }

    @Override
    protected void init() {
        adapter = new AdapterThemes();
        setAdapter(adapter);
        setupLinearLayoutManager(false);
        addItemDecoration(new DecorationThemes());
        setupCacheProperties();
    }

    private String parseColor(int color) {
        return getResources().getString(color).substring(3);
    }

    private void initGradient(int index, String topLeftHex, String bottomRightHex) {
        GRADIENT_HEXES[index][0] = topLeftHex;
        GRADIENT_HEXES[index][1] = bottomRightHex;
        GRADIENT_DRAWABLES[index] = new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                new int[]{ Color.parseColor("#" + topLeftHex),
                        Color.parseColor("#" + bottomRightHex) });
    }
}
