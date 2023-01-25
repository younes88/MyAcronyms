package yst.ymodule;

import android.content.Context;

import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Kasper on 2/11/2015.
 */
public class SwipeControllableViewPager extends ViewPager {
    private boolean swipe = true;

    public SwipeControllableViewPager(Context context) {
        super(context);
    }

    public SwipeControllableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isSwipe() {
        return swipe;
    }

    public void setSwipe(boolean swipe) {
        this.swipe = swipe;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {

        if (swipe) {
            return super.onInterceptTouchEvent(arg0);
        }

        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (swipe)
            return super.onTouchEvent(ev);
        else
            return false;
    }
}