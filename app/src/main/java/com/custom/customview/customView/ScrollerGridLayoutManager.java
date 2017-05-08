package com.custom.customview.customView;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by Jone on 17/5/3.
 */

public class ScrollerGridLayoutManager extends GridLayoutManager {


    public ScrollerGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
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
        int span = getSpanCount();//VERTICAL ,this is the number of columns;HORIZONTAL,this is the number of rows
        int itemWidth = 0;
        int itemHeight = 0;
        int maxItemHeight = 0;
        int width = 0;
        int height = 0;
        for (int i = 0; i < count; i++) {
            View child = recycler.getViewForPosition(i);
            if (child != null) {
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
                //第一种方式
                int parentWidthSpec = View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED);
                int parentHeightSpec = View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED);
                int childWidthSpec = ViewGroup.getChildMeasureSpec(parentWidthSpec, getPaddingLeft() + getPaddingRight(), lp.width);
                int childHeightSpec = ViewGroup.getChildMeasureSpec(parentHeightSpec, getPaddingTop() + getPaddingBottom(), lp.height);
                //第二种方式
//                int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, getPaddingLeft() + getPaddingRight(), lp.width);
//                int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, getPaddingTop() + getPaddingBottom(), lp.height);
                child.measure(childWidthSpec, childHeightSpec);
                recycler.recycleView(child);
                itemWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                itemHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

                if (getOrientation() == HORIZONTAL) {
                    height = Math.max(itemHeight, height);

                    if (count % span == 0) {
                        if (i == count - 1) {
                            height = span * height;
                        }
                    } else {
                        if (i == count - 1) {
                            height = (span + 1) * height;
                        }
                    }

                } else if (getOrientation() == VERTICAL) {

                    maxItemHeight = Math.max(itemHeight, maxItemHeight);
                    if (i % span == 0) {
                        height += maxItemHeight;
                        maxItemHeight = 0;
                    }

                }


            }
        }


        switch (widthMode) {
            case View.MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case View.MeasureSpec.UNSPECIFIED:
            case View.MeasureSpec.AT_MOST:
                break;
        }

        switch (heightMode) {
            case View.MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case View.MeasureSpec.UNSPECIFIED:
            case View.MeasureSpec.AT_MOST:
                break;
        }

        setMeasuredDimension(width, height);
    }
}
