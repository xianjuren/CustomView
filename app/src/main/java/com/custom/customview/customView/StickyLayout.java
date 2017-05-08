package com.custom.customview.customView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by Jone on 17/5/5.
 */

public class StickyLayout extends LinearLayout {

    boolean mIsSticky = true, mInitSuccess;
    View mHeadView;
    private int mLastX, mLastY, mTouchSlop;
    private int mInitHeadHeight, mCurrentHeadHeight;
    private OnGiveUpTouchEventListener mGiveUpTouchEventListener;
    private int STATUS_SHOW = 1;
    private int STATUS_CLOSED = 2;
    private int mStatus = STATUS_SHOW;
    private Scroller mScroller;

    public StickyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public StickyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mScroller = new Scroller(getContext());
    }

    public void setOnGiveUpTouchEventListener(OnGiveUpTouchEventListener l) {
        mGiveUpTouchEventListener = l;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            initData();
        }
    }

    private void initData() {
        int headId = getResources().getIdentifier("sticky_header", "id", getContext().getPackageName());
        if (headId != 0) {
            mHeadView = findViewById(headId);
            mInitHeadHeight = mHeadView.getMeasuredHeight();
            if (mInitHeadHeight > 0) {
                mInitSuccess = true;
            }
            mCurrentHeadHeight = mInitHeadHeight;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = x - mLastX;
                int moveY = y - mLastY;
                if (Math.abs(moveY) >= Math.abs(moveX)) {
                    if (mStatus == STATUS_SHOW && moveY <= -mTouchSlop) {//HeadView展示并向上的趋势
                        intercept = true;
                    }
                    if (null != mGiveUpTouchEventListener) { //recyclerView滑动到顶部向下的趋势
                        if (moveY >= mTouchSlop && mGiveUpTouchEventListener.giveUpTouchEvent(ev)) {
                            intercept = true;
                        }
                    }

                } else {
                    intercept = false;
                }
                break;

            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }

        return intercept && mIsSticky;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        final int destHeight;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = x - mLastX;
                int moveY = y - mLastY;
                mCurrentHeadHeight += moveY;
                moveHeadViewHeight(mCurrentHeadHeight);
                break;
            case MotionEvent.ACTION_UP:
                // 这里做了下判断，当松开手的时候，会自动向两边滑动，具体向哪边滑，要看当前所处的位置
                //上滑超一半，设为关闭状态
                if (mCurrentHeadHeight < mInitHeadHeight * 0.5f) {
                    mStatus = STATUS_CLOSED;
                    destHeight = 0;//滑动隐藏headView
                } else {
                    //上滑未一半，设为打开状态
                    mStatus = STATUS_SHOW;
                    destHeight = mInitHeadHeight;//滑动展示headView
                }
                ValueAnimator animator = ValueAnimator.ofFloat(0, 100);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float fraction = animation.getAnimatedFraction();
                        int height = (int) (mCurrentHeadHeight + (destHeight - mCurrentHeadHeight) * fraction);
                        moveHeadViewHeight(height);
                    }
                });
                animator.start();
                break;
        }
        return true;
    }

    private void moveHeadViewHeight(int currentHeadHeight) {

        if (!mInitSuccess) {
            initData();
        }
        //设置阀值
        if (currentHeadHeight <= 0) {
            currentHeadHeight = 0;
        } else if (currentHeadHeight > mInitHeadHeight) {
            currentHeadHeight = mInitHeadHeight;
        }
        //设置HeadView的状态
        if (currentHeadHeight == 0) {
            mStatus = STATUS_CLOSED;
        } else {
            mStatus = STATUS_SHOW;
        }

        //scrollBy/scrollTo适合对View内容的滑动，单纯的坐标滑动；LayoutParams适合有交互的View；HeadView动态改变高度
        if (null != mHeadView && mHeadView.getLayoutParams() != null) {
            mHeadView.getLayoutParams().height = currentHeadHeight;
            mHeadView.requestLayout();
            mCurrentHeadHeight = currentHeadHeight;
        }
    }


    public void smoothScroll(int startY, int distY, int duration) {
        mScroller.startScroll(0, startY, 0, distY, duration);
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

    /**
     * 回调RecyclerView或者ListView是否滑动到顶部
     */
    public interface OnGiveUpTouchEventListener {
        boolean giveUpTouchEvent(MotionEvent event);
    }


}
