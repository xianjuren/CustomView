package com.custom.customview.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.custom.customview.R;
import com.custom.customview.bean.Coupon;

import java.util.List;

/**
 * Created by Jone on 17/5/3.
 */

public class NormalAdapter extends RecyclerView.Adapter {

    private List<Coupon> mCoupons;
    private int mType;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView;
        if(mType==1){
            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_linear_layout_item, null);
        }else {
            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_gride_layout_item, null);
        }
        return new FlickerViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FlickerViewHolder mHolder = (FlickerViewHolder) holder;
        if (null != mCoupons && null != mCoupons.get(position)) {
            mHolder.mTvPrice.setText(mCoupons.get(position).getPrice());
            mHolder.mTvCouponName.setText(mCoupons.get(position).getKind());
        }
    }

    @Override
    public int getItemCount() {
        if (null != mCoupons && mCoupons.size() > 0) {
            return mCoupons.size();
        }
        return 0;
    }


    public void setCoupons(List<Coupon> coupons) {
        mCoupons = coupons;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setType(int type) {
        mType=type;
    }

    private class FlickerViewHolder extends RecyclerView.ViewHolder {
        TextView mTvPrice, mTvCouponName;

        public FlickerViewHolder(View mView) {
            super(mView);
            mTvPrice = (TextView) mView.findViewById(R.id.flicker_item_price);
            mTvCouponName = (TextView) mView.findViewById(R.id.flicker_item_coupon_name);

        }
    }
}
