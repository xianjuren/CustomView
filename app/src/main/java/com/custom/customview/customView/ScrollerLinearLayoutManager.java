package com.custom.customview.customView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Jone on 17/5/3.
 */

public class ScrollerLinearLayoutManager extends LinearLayoutManager {


    public ScrollerLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        //关键的一步，不允许ScrollerLinearLayoutManager或RecyclerView主动测量，否则在下面的onMeasure（。。。）中，
        // View child = recycler.getViewForPosition(i)会一直报错：ndexOutOfBoundsException：Invalid item position 0(0). Item count:0
        setAutoMeasureEnabled(false);
    }


    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);
        int widthSize = View.MeasureSpec.getSize(widthSpec);
        int heightSize = View.MeasureSpec.getSize(heightSpec);
        int widthMode = View.MeasureSpec.getMode(widthSpec);
        int heightMode = View.MeasureSpec.getMode(heightSpec);
        int count = getItemCount();
        // int count =getChildCount();不能使用该方法，值偏下，复用的布局不会计算在内。
        int itemWidth = 0;
        int itemHeight = 0;
        int width = 0;
        int height = 0;
        for (int i = 0; i < count; i++) {
            View child = recycler.getViewForPosition(i);
            if (null != child) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                //第一种
                int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, getPaddingLeft() + getPaddingRight(), layoutParams.width);
                int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, getPaddingTop() + getPaddingBottom(), layoutParams.height);
                child.measure(childWidthSpec, childHeightSpec);
                recycler.recycleView(child);
                //第二种
                //  measureView(recycler, i, child, layoutParams);
                itemWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                itemHeight = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
                if (getOrientation() == HORIZONTAL) {
                    width += itemWidth;
                    height = Math.max(height, itemHeight);
                } else if (getOrientation() == VERTICAL) {
                    height += itemHeight;
                    width = Math.max(width, itemWidth);
                }
            }

        }

        setMeasuredDimension(widthMode == View.MeasureSpec.EXACTLY ? widthSize : width, heightMode ==
                View.MeasureSpec.EXACTLY ? heightSize : height);

    }

    private void measureView(RecyclerView.Recycler recycler, int i, View child, RecyclerView.LayoutParams layoutParams) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED);
        int childWidthSpec = ViewGroup.getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight(), layoutParams.width);
        int childHeightSpec = ViewGroup.getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), layoutParams.height);
        child.measure(childWidthSpec, childHeightSpec);
        recycler.recycleView(child);
    }


}
