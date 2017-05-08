package com.custom.customview.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jone on 17/5/3.
 */

public class AutoLinefeedViewGroup extends ViewGroup {

    private List<List<View>> mViews;
    private List<Integer> mLineHeights;
    private Scroller mScroller;

    public AutoLinefeedViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        if (null == mViews) {
            mViews = new ArrayList<>();
        }
        if (null == mLineHeights) {
            mLineHeights = new ArrayList<>();
        }
        if (null == mScroller) {
            mScroller = new Scroller(context);
        }
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    private void smoothScrollTo(int oldX, int x, int oldY, int y, int duration) {
        //int startX, int startY, int dx, int dy, int duration
        mScroller.startScroll(oldX, oldY, x, y, duration);
        invalidate();
    }

    /**
     * 子控件的高度/宽度设为wrap_content，才有必要使用。
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);


        MarginLayoutParams params = (MarginLayoutParams) this.getLayoutParams();
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                measureWidth - (params.leftMargin + params.rightMargin), MeasureSpec.EXACTLY);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                measureHeight - (params.topMargin + params.bottomMargin), MeasureSpec.EXACTLY);

        measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeight);
    }

    /**
     * 第二种子View 测量方法
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     * @param view
     * @param mParams
     */
    private void secondMeasure(int widthMeasureSpec, int heightMeasureSpec, View view, MarginLayoutParams mParams) {

        int childWidthSpec = getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight(), mParams.width);
        int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), mParams.height);
        view.measure(childWidthSpec, childHeightSpec);
    }


    /**
     * 1.记录行数以及每行的View个数
     * 2.遍历所有行数以及每行的View的个数，然后 child.layout（...）
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //1. 第一步
        mViews.clear();
        mLineHeights.clear();
        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        int count = getChildCount();
        List<View> oneLineViews = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            MarginLayoutParams mParams = (MarginLayoutParams) view.getLayoutParams();
            int childWidth = view.getMeasuredWidth() + mParams.leftMargin + mParams.rightMargin;
            int childHeight = view.getMeasuredHeight() + mParams.topMargin + mParams.bottomMargin;
            if (childWidth + lineWidth > width) {
                //换行重置宽高
                lineWidth = 0;
                //记录当前最大值的行高，
                mLineHeights.add(lineHeight);
                //记录这一样所有的view,生成一个新的ArrayList，准备保存下一行的view
                mViews.add(oneLineViews);
                oneLineViews = new ArrayList<>();
            }
            lineWidth += childWidth;
            lineHeight = Math.max(childHeight, lineHeight);
            oneLineViews.add(view);
        }
        //最后一行，添加一个高度和所有view
        mLineHeights.add(lineHeight);
        mViews.add(oneLineViews);

        //第二步

        int top = 0;
        int left = 0;

        if (null != mViews && mViews.size() > 0) {
            for (int i = 0; i < mViews.size(); i++) {
                List<View> views = mViews.get(i);
                lineHeight = mLineHeights.get(i);
                for (int j = 0; j < views.size(); j++) {
                    View view = views.get(j);
                    if (view.getVisibility() == GONE) {
                        continue;
                    }
                    MarginLayoutParams mParams = (MarginLayoutParams) view.getLayoutParams();
                    int viewWidth = view.getMeasuredWidth();
                    int viewHeight = view.getMeasuredHeight();
                    //相对于父容器的位置，只需要计算距左，距上。
                    int marginLeft = left + mParams.leftMargin;
                    int marginTop = top + mParams.topMargin;
                    int marginRight = marginLeft + viewWidth;
                    int marginBottom = viewHeight + marginTop;
                    view.layout(marginLeft, marginTop, marginRight, marginBottom);
                    //计算所有子View占据的宽度
                    left += mParams.leftMargin + mParams.rightMargin + viewWidth;
                }

                left = 0;
                top += lineHeight;
            }
        }
    }


}
