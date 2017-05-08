package com.custom.customview.application;

import android.app.Application;

/**
 * Created by Jone on 17/5/3.
 */

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalConfig.init(this);
    }
}
