package com.custom.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.widget.TextView;

import com.custom.customview.R;

/**
 * Created by Jone on 17/5/4.
 */
public class MoveScreenActivity extends BaseBackActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    protected int getTitleStringId() {
        return R.string.full_screen_move;
    }

    @Override
    int getContentLayoutId() {
        return R.layout.activity_screen_move;
    }
}
