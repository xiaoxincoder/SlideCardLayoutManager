package com.keepshare.slidecard.utils;

import android.content.Context;
import android.content.res.Resources;

public class ScreenUtils {

    private static Resources resources = Resources.getSystem();

    public static int dp2px(float dp) {
        return (int)(dp * resources.getDisplayMetrics().density + 0.5f);
    }
}
