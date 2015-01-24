package livroandroid.lib.utils;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.FrameLayout;

/**
 * Created by ricardo on 01/01/15.
 */
public class MaterialUtils {

    public static void setRippleBackgroundEffect(FrameLayout view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int[] attrs = new int[] { android.R.attr.selectableItemBackground};
            TypedArray ta = view.getContext().obtainStyledAttributes(attrs);
            Drawable drawable = ta.getDrawable(0 /* index */);
            ta.recycle();
            view.setForeground(drawable);
        }
    }
}
