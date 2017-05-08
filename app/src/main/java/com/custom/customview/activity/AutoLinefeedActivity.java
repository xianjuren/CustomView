package com.custom.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.custom.customview.R;
import com.custom.customview.customView.AutoLinefeedViewGroup;
import com.custom.customview.util.DensityUtil;

/**
 * Created by Jone on 17/5/3.
 */
public class AutoLinefeedActivity extends BaseBackActivity {

    AutoLinefeedViewGroup mAutoLinefeedViewGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAutoLinefeedViewGroup = (AutoLinefeedViewGroup) findViewById(R.id.auto_linefeed_view_group);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            initImageView();
        }
    }

    private void initImageView() {
        ImageView mImageView = new ImageView(this);
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mParams.setMargins(20, 0, 0, 0);
        mImageView.setImageResource(R.mipmap.ic_launcher);
        mParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        mParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        mImageView.setLayoutParams(mParams);
        mAutoLinefeedViewGroup.addView(mImageView);
    }


    public void addView(View view) {
        initImageView();
    }

    public void removeView(View view) {
        if (mAutoLinefeedViewGroup.getChildCount() > 0) {
            mAutoLinefeedViewGroup.removeViewAt(mAutoLinefeedViewGroup.getChildCount() - 1);
        }
    }

    @Override
    protected int getTitleStringId() {
        return R.string.auto_linefeed;
    }

    @Override
    int getContentLayoutId() {
        return R.layout.activity_auto_linefeed;
    }
}
