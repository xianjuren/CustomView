package com.custom.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.custom.customview.R;
import com.custom.customview.bean.Coupon;
import com.custom.customview.adpter.NormalAdapter;
import com.custom.customview.customView.ScrollerGridLayoutManager;
import com.custom.customview.customView.ScrollerLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jone on 17/5/3.
 */
public class NestClashActivity extends BaseBackActivity {

    int TYPE_VALUE_LINEARLAYOUT=1,TYPE_VALUE_GRIDLAYOUT=2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.nest_clash_recycler_view);
        NormalAdapter normalAdapter = new NormalAdapter();
        ScrollerLinearLayoutManager manager = new ScrollerLinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        //  ScrollerGridLayoutManager manager =new ScrollerGridLayoutManager(this,9, LinearLayoutManager.HORIZONTAL,false);
      // normalAdapter.setType(TYPE_VALUE_GRIDLAYOUT);
        mRecyclerView.setLayoutManager(manager);

        normalAdapter.setType(TYPE_VALUE_LINEARLAYOUT);
        mRecyclerView.setAdapter(normalAdapter);
        normalAdapter.setCoupons(getListCoupon());
    }


    private List<Coupon> getListCoupon() {
        List<Coupon> mCoupons = new ArrayList<>(4);
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
        return R.string.nest_clash;
    }

    @Override
    int getContentLayoutId() {
        return R.layout.activity_nest_clash;
    }
}
