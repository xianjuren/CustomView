package com.custom.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.custom.customview.R;
import com.custom.customview.bean.Coupon;
import com.custom.customview.adpter.NormalAdapter;
import com.custom.customview.customView.HorizontalMoveView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jone on 17/5/5.
 */
public class DifferentDirectionConflictActivity extends BaseBackActivity {

    HorizontalMoveView mHorizontalMoveView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHorizontalMoveView = (HorizontalMoveView) findViewById(R.id.horizontal_move_view);
        initData();
    }

    private void initData() {

        final int screenWidth =getResources().getDisplayMetrics().widthPixels;
        for (int i = 0; i < 3; i++) {
            View la = LayoutInflater.from(this).inflate(R.layout.full_screen_recyclerview, null);
            LinearLayout.LayoutParams params =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.width =screenWidth;
            la.setLayoutParams(params);
            TextView tvNumber = (TextView) la.findViewById(R.id.sticky_number);
            tvNumber.setText((i + 1) + "");
            RecyclerView recyclerView = (RecyclerView) la.findViewById(R.id.recycler_view_page);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            NormalAdapter normalAdapter = new NormalAdapter();
            normalAdapter.setType(1);
            recyclerView.setAdapter(normalAdapter);
            normalAdapter.setCoupons(getListCoupon());
            mHorizontalMoveView.addView(la);
        }
    }




    private List<Coupon> getListCoupon() {
        List<Coupon> mCoupons = new ArrayList<>();
        mCoupons.add(new Coupon("5.00", "直减优惠券", false));
        mCoupons.add(new Coupon("8.00", "满减优惠券", false));
        mCoupons.add(new Coupon("16.00", "特种优惠券", false));
        mCoupons.add(new Coupon("278.00", "积分优惠券", false));
        mCoupons.add(new Coupon("5.00", "直减优惠券", false));
        mCoupons.add(new Coupon("8.00", "满减优惠券", false));
        mCoupons.add(new Coupon("16.00", "特种优惠券", false));
        mCoupons.add(new Coupon("5.00", "直减优惠券", false));
        mCoupons.add(new Coupon("8.00", "满减优惠券", false));
        mCoupons.add(new Coupon("16.00", "特种优惠券", false));
        mCoupons.add(new Coupon("278.00", "积分优惠券", false));
        mCoupons.add(new Coupon("5.00", "直减优惠券", false));
        mCoupons.add(new Coupon("8.00", "满减优惠券", false));
        mCoupons.add(new Coupon("16.00", "特种优惠券", false));
        mCoupons.add(new Coupon("5.00", "直减优惠券", false));
        mCoupons.add(new Coupon("8.00", "满减优惠券", false));
        mCoupons.add(new Coupon("16.00", "特种优惠券", false));
        return mCoupons;
    }


    @Override
    protected int getTitleStringId() {
        return R.string.different_direction_conflict;
    }

    @Override
    int getContentLayoutId() {
        return R.layout.activity_different_direction;
    }
}
