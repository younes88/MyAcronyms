package yst.ymodule.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class NonFocusingHScrollView extends HorizontalScrollView {

    public NonFocusingHScrollView(Context context) {
        super(context);
    }

    public NonFocusingHScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonFocusingHScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        return true;
    }

}