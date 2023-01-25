package yst.ymodule;


import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;


/**
 * Created by Hadi Hashemi on 11/12/2014.
 */
public class AnimatedExpander {

    final static int DURATION_MULTIPIER = 8;

    public static void expand(final View v, final Runnable runOnFinish) {
        expand(v, DURATION_MULTIPIER, runOnFinish);
    }

    public static void expand(final View v) {
        expand(v, DURATION_MULTIPIER, null);
    }

    public static void expand(final View v, int DURATION_MULTIPIER) {
        expand(v, DURATION_MULTIPIER, null);
    }

    public static void expand(final View v, int DURATION_MULTIPIER, final Runnable runOnFinish) {
        v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        int duration = (int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density);
        a.setDuration(duration * DURATION_MULTIPIER);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (runOnFinish != null)
                    runOnFinish.run();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(a);
    }

    public static void collapse(final View v, final Runnable runOfFinish) {
        collapse(v, DURATION_MULTIPIER, runOfFinish);
    }

    public static void collapse(final View v) {
        collapse(v, DURATION_MULTIPIER, null);
    }

    public static void collapse(final View v, int DURATION_MULTIPIER) {
        collapse(v, DURATION_MULTIPIER, null);
    }

    public static void collapse(final View v, int DURATION_MULTIPIER, final Runnable runOfFinish) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        int duration = (int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density);
        a.setDuration(duration * DURATION_MULTIPIER);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (runOfFinish != null)
                    runOfFinish.run();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(a);
    }

    public static void changeHeighAnimated(final View v, int currentHeight, int finalHeight, int Duration) {
        changeHeighAnimated(v, currentHeight, finalHeight, Duration, null);
    }

    public static void changeHeighAnimated(final View v, int currentHeight, int finalHeight, int Duration, final Runnable runOnFinishAnim) {

        ValueAnimator animator = ValueAnimator.ofInt(currentHeight, finalHeight);


        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        animator.setDuration(Duration);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (runOnFinishAnim != null)
                    runOnFinishAnim.run();
            }
        });
        animator.start();

    }

    public static void changeWidthAnimated(final View v, int currentWidth, int finalWidth, int Duration) {
        changeWidthAnimated(v, currentWidth, finalWidth, Duration, null);
    }

    public static void changeWidthAnimated(final View v, int currentWidth, int finalWidth, int Duration, final Runnable runOnFinishAnim) {

        ValueAnimator animator = ValueAnimator.ofInt(currentWidth, finalWidth);


        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.width = value;
                v.setLayoutParams(layoutParams);
            }
        });
//        animator.setInterpolator(new LinearOutSlowInInterpolator());
        animator.setDuration(Duration);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (runOnFinishAnim != null)
                    runOnFinishAnim.run();
            }
        });
        animator.start();

    }

}
