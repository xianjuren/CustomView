package com.custom.customview.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.custom.customview.customInterface.OnHeaderUpdateListener;
import com.custom.customview.customInterface.OnRecyclerFirstVisibleGroupListener;

/**
 * Created by Jone on 17/5/6.
 */

public class ExpandableRecyclerView extends RecyclerView{

    private boolean mActionDownHappened = false;
    private View mTouchTarget;
    protected boolean mIsHeaderGroupClickable = true;
    View mHeaderView;
    int mHeaderViewWidth, mmHeaderViewHeight;
    OnRecyclerFirstVisibleGroupListener mGroupListener;
    private OnHeaderUpdateListener mHeaderUpdateListener;

    public ExpandableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setGroupListener(OnRecyclerFirstVisibleGroupListener groupListener) {
        mGroupListener = groupListener;
    }

    public void setHeaderUpdateListener(OnHeaderUpdateListener headerUpdateListener) {
        mHeaderUpdateListener = headerUpdateListener;
        mHeaderView = headerUpdateListener.getPinnedHeader();
        if (null == mHeaderView) {
            mHeaderViewWidth = mmHeaderViewHeight = 0;
            return;
        }
        refreshHeaderView();
        postInvalidate();
    }

    private void refreshHeaderView() {
        int firstVisibleGroup = mGroupListener.getFirstVisibleGroupPosition();
        mHeaderUpdateListener.updatePinnedHeader(mHeaderView, firstVisibleGroup);
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (null != mHeaderView) {
            measureChild(mHeaderView, widthSpec, heightSpec);
            mHeaderViewWidth = mHeaderView.getMeasuredWidth();
            mmHeaderViewHeight = mHeaderView.getMeasuredHeight();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(null!=mHeaderView){
            int top =mHeaderView.getTop();
            int left =mHeaderView.getLeft();
            mHeaderView.layout(left,top,mHeaderViewWidth,top+mmHeaderViewHeight);
        }
    }



    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mHeaderView != null) {
            drawChild(canvas, mHeaderView, getDrawingTime());
        }
    }


    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        refreshHeaderView();
    }
}
