package yst.ymodule.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import ir.yst.ymodule.R;


public class CircleImageView extends ImageView {
    // The name of the view
    private String name;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs,
                    R.styleable.CircleImageView);

            this.name = array.getString(R.styleable.CircleImageView_name);
            array.recycle();
        }
    }

    /**
     * Return the name of the view.
     *
     * @return Returns the name of the view.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the view.
     *
     * @param name The name to be set for the view.
     */
    public void setName(String name) {
        this.name = name;
    }


}
