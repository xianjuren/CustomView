package com.custom.customview.customView;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

/**
 * Created by Jone on 17/5/7.
 */

public class RollingPictureViewGroup extends ViewGroup {

    int mStartScreen = 1;
    int mHeight = 0;
    float standard_angle = 90;
    private Matrix mMatrix;
    private Camera mCamera;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mLastY = 0;
    private float standard_speed = 2000;

    public RollingPictureViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RollingPictureViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 我们分三步来实现下：
     * 1、首先，初始化控件，进行测量和布局,init()。
     * 这里我们整个容器继承自ViewGroup,来看看吧，初始化Camera和Matrix，因为涉及到滚动，我们用个辅助工具Scroller:
     */

    public void init() {
        mCamera = new Camera();
        mMatrix = new Matrix();
        mScroller = new Scroller(getContext(), new LinearInterpolator());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = 0;
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            ViewGroup.MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            int childWith = view.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = view.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            width = Math.max(width, childWith);
            height += childHeight;
        }

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width, heightMode == MeasureSpec.EXACTLY ? heightSize : height);
        mHeight = getMeasuredHeight();


    }

    //各个子控件从上到下依次排列，而整个控件大小是一定的，界面上也就只显示一个子控件，在整个控件滚动的时候形成界面切换效果。//
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int totalHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            ViewGroup.MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            if (child.getVisibility() == GONE) {
                continue;
            }
            int left = lp.leftMargin;
            int right = left + child.getMeasuredWidth();
            int top = lp.topMargin + totalHeight;
            int bottom = top + child.getMeasuredHeight();
            child.layout(left, top, right, bottom);
            totalHeight += child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
        }
    }

    /**
     * 2、重写dispatchDraw方法，实现3D界面切换效果
     * 在dispatchDraw方法中，重新对各个子控件用Camera和Matrix进行矩阵转换，以此在滚动中实现立体效果，
     * 根据我们之前了解的，我们将Camera沿着X轴进行一定的角度旋转，
     * 两个控件在滚动过程中就会形成一个夹角，从而出现立体效果，当然，一定要注意的是要将控件中心点移至0,0点，否则会看不到效果：
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        // super.dispatchDraw(canvas);
        for (int i = 0; i < getChildCount(); i++) {
            drawView(canvas, i, getDrawingTime());
        }
    }

    private void drawView(Canvas canvas, int screen, long duration) {
        View childView = getChildAt(screen);
        int childHeight = childView.getMeasuredHeight();
        int childWidth = childView.getMeasuredWidth();
        int scrollY = getScrollY();
        float currentDegree = scrollY * (standard_angle / childHeight);
        float rotateDegree = currentDegree - screen * standard_angle;//不同的view按照360度排列，旋转时顺序显示。
        //0--90/-90---0向上顺时针翻滚，角度递加，0-- -90/90--0向下逆时针旋转，角度递减,不限制角度会滚动时会有两张照片1.3/2.4一起滚动
        if (rotateDegree < 90 && rotateDegree > -90) {
            float centerX = childWidth / 2;
            float centerY = childHeight < scrollY ? 2 * childHeight : childHeight;
            canvas.save();
            mCamera.save();
            mCamera.rotateX(rotateDegree);
            mCamera.getMatrix(mMatrix);
            mCamera.restore();
            mMatrix.preTranslate(-centerX, -centerY);
            mMatrix.postTranslate(centerX, centerY);
            canvas.concat(mMatrix);
            drawChild(canvas, childView, duration);
            canvas.restore();
        }

    }

    /**
     * 3、重写onInterceptTouchEvent和onTouchEvent方法实现手指滑动界面切换
     * 在onTouchEvent方法中，根据手指移动的距离，调用ScrollBy()方法进行持续的滚动，在手指抬起的时候，判断当前的速率，如果大于一定值或超过子控件的1/2时，
     * 转换当前状态进行界面切换，否则回滚回当前页面。这里在onInterceptTouchEvent简单的拦截了当前事件，而如果我们需要子控件处理事件时还需要进行处理。
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return false;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.setFinalY(mScroller.getCurrY());
                    mScroller.abortAnimation();
                    scrollTo(0, getScrollY());
                }
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int distY = mLastY - y;
                mLastY = y;
                if (mScroller.isFinished()) {
                    moveY(distY);
                }
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000);
                float velocityTrackerY = mVelocityTracker.getYVelocity();
                int startY;
                //下滑速度大于standard_speed，或者下滑时，顶部页面的高度超过1/2
                if (velocityTrackerY > standard_speed || (getScrollY() + mHeight / 2) / mHeight < mStartScreen) {
                    startY = getScrollY() + mHeight;
                    setPrePage();
                    //上滑速度小于-standard_speed，或者上滑时，底部页面的高度超过1/2
                } else if (velocityTrackerY < -standard_speed || (getScrollY() + mHeight / 2) / mHeight > mStartScreen) {
                    startY = getScrollY() - mHeight;
                    setNextPage();
                } else {
                    startY = getScrollY();
                    //在当前页面小角度转动
                }
                int distanceY = mStartScreen * mHeight - startY;
                smoothScroll(0, startY, 0, distanceY, 500);

                break;
        }
        return true;
    }

    private void moveY(int distY) {

        scrollBy(0, distY);
        //初始化下滑，View没有设置切换，会显示空白
        if (getScrollY() < 8) {
            setPrePage();
            scrollBy(0, mHeight);
        } else if (getScrollY() > (getChildCount() - 1) * mHeight) {
            setNextPage();
            scrollBy(0, -mHeight);
        }

    }

    private void setNextPage() {
        int childCount = getChildCount();
        View view = getChildAt(0);
        removeView(view);
        addView(view, childCount - 1);
    }

    private void setPrePage() {
        View view = getChildAt(getChildCount() - 1);
        removeView(view);
        addView(view, 0);
    }

    private void smoothScroll(int startX, int startY, int distX, int distY, int duration) {
        mScroller.startScroll(startX, startY, distX, distY, duration);
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
        if (null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
        }
    }

}
