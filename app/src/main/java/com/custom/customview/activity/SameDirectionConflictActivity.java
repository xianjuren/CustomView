package com.custom.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.custom.customview.R;
import com.custom.customview.bean.Coupon;
import com.custom.customview.adpter.NormalAdapter;
import com.custom.customview.customView.StickyLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jone on 17/5/5.
 */
public class SameDirectionConflictActivity extends BaseBackActivity implements StickyLayout.OnGiveUpTouchEventListener {

    StickyLayout mStickyLayout;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        mStickyLayout = (StickyLayout) findViewById(R.id.sticky_layout);
        mStickyLayout.setOnGiveUpTouchEventListener(this);
        TextView tvNumber = (TextView) findViewById(R.id.sticky_number);
        tvNumber.setText("我是头部");
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_page);
        mLayoutManager =new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        NormalAdapter normalAdapter = new NormalAdapter();
        normalAdapter.setType(1);
        mRecyclerView.setAdapter(normalAdapter);
        normalAdapter.setCoupons(getListCoupon());
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
        return R.string.same_direction_conflict;
    }

    @Override
    int getContentLayoutId() {
        return R.layout.activity_same_direction;
    }

    @Override
    public boolean giveUpTouchEvent(MotionEvent event) {
        if (null!=mLayoutManager &&mLayoutManager.findFirstVisibleItemPosition()==0) {
                View view =mLayoutManager.getChildAt(0);
                if(null!=view && view.getTop()>=0){
                    return true;
                }
        }
        return false;
    }
}
