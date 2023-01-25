package yst.ymodule;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Kasper on 4/7/2015.
 */
public class UnitHelper {
    public static float ConvertDpToPixel(int dp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static float ConvertSpToPixel(int sp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }
}
