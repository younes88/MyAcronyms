package yst.ymodule;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.rey.material.widget.RippleManager;

/**
 * Created by Kasper on 4/4/2015.
 */
@SuppressLint("AppCompatCustomView")
public class RippleImageView extends ImageView {
    private RippleManager mRippleManager = new RippleManager();

    public RippleImageView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public RippleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public RippleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public RippleImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.mRippleManager.onCreate(this, context, attrs, defStyleAttr, defStyleRes);
    }

    public void setOnClickListener(OnClickListener l) {
        if (l == this.mRippleManager) {
            super.setOnClickListener(l);
        } else {
            this.mRippleManager.setOnClickListener(l);
            setOnClickListener(this.mRippleManager);
        }
    }

    public boolean onTouchEvent(@NonNull MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        return (this.mRippleManager.onTouchEvent(this, event)) || (result);
    }

}
