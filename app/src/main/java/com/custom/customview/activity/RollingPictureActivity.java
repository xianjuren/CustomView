package com.custom.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.custom.customview.R;
import com.custom.customview.customView.RollingPictureViewGroup;
import com.custom.customview.util.DensityUtil;

/**
 * Created by Jone on 17/5/7.
 */

public class RollingPictureActivity extends BaseBackActivity {

    int[] mImageResoureId = new int[]{R.drawable.photo1, R.drawable.photo2, R.drawable.photo3, R.drawable.photo4};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        RollingPictureViewGroup mViewGroup = (RollingPictureViewGroup) findViewById(R.id.rolling_picture_view_group);
        for (int i = 0; i < 4; i++) {
            ImageView mImage = new ImageView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                    , LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.height = (int) DensityUtil.dpToPx(this, 250);
            lp.width = (int) DensityUtil.dpToPx(this, 250);
            mImage.setImageResource(mImageResoureId[i]);
            mImage.setScaleType(ImageView.ScaleType.FIT_XY);
            mImage.setLayoutParams(lp);
            mViewGroup.addView(mImage);
        }
    }

    @Override
    protected int getTitleStringId() {
        return R.string.rolling_picture;
    }

    @Override
    int getContentLayoutId() {
        return R.layout.activity_roll_picture;
    }
}
