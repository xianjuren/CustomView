package com.custom.customview.customInterface;

import android.view.View;

/**
 * Created by Jone on 17/5/7.
 */

public interface OnHeaderUpdateListener {

    public View getPinnedHeader();

    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos);
}
