package com.custom.customview.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by Jone on 17/5/4.
 */

public class MoveScreenTextView extends TextView {

    int mLastX = 0;
    int mLastY = 0;

    public MoveScreenTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //全屏幕滑动，需要绝对坐标
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = x - mLastX;
                int moveY = y - mLastY;
               // Log.d("MoveScreenTextView====moveX",moveX+"===moveY==="+moveY+"====getTranslationX=="+getTranslationX()+"===TranslationY=="+getTranslationY());
                //移动距离==坐标点距离+偏移量
                animate().translationX(moveX+getTranslationX()).translationY(moveY+getTranslationY()).setDuration(0).start();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        mLastX = x;
        mLastY = y;
      //  Log.d("MoveScreenTextView====x",x+"===y==="+y+"====mLastX=="+mLastX+"===mLastY=="+mLastY);
        return true;
    }
}
