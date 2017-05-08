package com.custom.customview.application;

import android.content.Context;

/**
 * Created by Jone on 17/5/3.
 */
public class GlobalConfig {

    static Context mContent;

    public static void init(Context context) {
        mContent = context;
    }

    public static Context getContext() {
        return mContent;
    }
}
