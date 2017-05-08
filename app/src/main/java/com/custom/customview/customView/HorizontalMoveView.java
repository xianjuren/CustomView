package com.custom.customview.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by Jone on 17/5/5.
 */

public class HorizontalMoveView extends ViewGroup {

    int mLastY, mChildCount, mChildWidth, mCurrentChildIndex;
    int mTouchSlop;
    VelocityTracker mVelocityTracker;
    private int mLastX;
    private Scroller mScroller;
    private String Tag = "HorizontalMoveView";
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;

    public HorizontalMoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public HorizontalMoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                intercept = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = x - mLastX;
                int moveY = y - mLastY;
                if (Math.abs(moveX) > Math.abs(moveY) && Math.abs(moveX) > mTouchSlop) {
                    intercept = true;
                    // Log.d(Tag, "onInterceptTouchEvent====x==" + x + "==y==" + y + "===mlastx==" + mLastX + "===mlastY==" + mLastY);
                } else {
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }


        return intercept;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.setFinalX(mScroller.getCurrX());
                    mScroller.abortAnimation();
                    scrollTo(mScroller.getCurrX(), 0);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = x - mLastX;
                int moveY = y - mLastY;
                //  scrollTo(...);基于当前参数的绝对滑动，我们自己调用时基本使用scrollBy（。。。）
                //mScrollX、mScrollY正负值与坐标方向的取值相反的，所以这里传值时要传负值。
                scrollBy(-moveX, 0);//基于当前位置的相对滑动
                mLastX = x;
                break;
            case MotionEvent.ACTION_UP:
                int mScrollX = getScrollX();
                mVelocityTracker.computeCurrentVelocity(1000);
                float speed = mVelocityTracker.getXVelocity();
                //速度达到某值进入下一页
                if (Math.abs(speed) >= 100) {
                    //speed>0，从左往右滑动，页数递减
                    mCurrentChildIndex = speed > 0 ? mCurrentChildIndex - 1 : mCurrentChildIndex + 1;
                } else {
                    //滑动的距离超过一半，就进入下一页
                    mCurrentChildIndex = (mScrollX + mChildWidth / 2) / mChildWidth;
                }
                //保证在0页和最后一页滑动时不会越界
                mCurrentChildIndex = Math.max(0, Math.min(mCurrentChildIndex, mChildCount - 1));
                //没有达到进入下一页的要求，恢复原样
                int startX = mScrollX;
                int dx = mCurrentChildIndex * mChildWidth - mScrollX;
                smoothScrollTo(startX, dx, 500);
                mVelocityTracker.clear();
                break;
        }

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        mChildCount = getChildCount();
        MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
        int childWidthSpec = MeasureSpec.makeMeasureSpec(widthSize - (lp.leftMargin + lp.rightMargin), MeasureSpec.EXACTLY);
        int childHeightSpec = MeasureSpec.makeMeasureSpec(heightSize - (lp.topMargin + lp.bottomMargin), MeasureSpec.EXACTLY);
        measureChildren(childWidthSpec, childHeightSpec);
        setMeasuredDimension(widthSize, heightSize);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mChildCount = getChildCount();
        int marginLeft = 0;
        for (int i = 0; i < mChildCount; i++) {
            View view = getChildAt(i);
            if (view.getVisibility() == GONE) {
                continue;
            }
            mChildWidth = view.getMeasuredWidth();
            MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
            int left = lp.leftMargin + marginLeft;
            int right = left + mChildWidth;
            int top = lp.topMargin;
            int bottom = top + view.getMeasuredHeight();
            view.layout(left, top, right, bottom);
            marginLeft += lp.leftMargin + mChildWidth + lp.rightMargin;

        }

    }

    private void smoothScrollTo(int startX, int dx, int dy) {
        mScroller.startScroll(startX, 0, dx, 0, dy);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mVelocityTracker.recycle();
    }
}
