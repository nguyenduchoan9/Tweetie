package com.codepath.apps.restclienttemplate.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by Nguyen.D.Hoang on 10/28/2016.
 */

public class ImageUtil {
    public static float convertDpToPixel(float dp, Context context){
        Resources resource = context.getResources();
        DisplayMetrics metrics = resource.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi/DisplayMetrics.DENSITY_DEFAULT);
    }
}
