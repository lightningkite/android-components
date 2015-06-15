package com.lightningkite.androidcomponents.ui;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

/**
 * An animation which animates the height of a view inside a vertical linear layout.
 * Created by Joseph on 2/25/2015.
 */
public class LinearLayoutHeightAnimation extends Animation {
    private View mView;
    private int mStartHeight;
    private int mEndHeight;
    private LinearLayout.LayoutParams mLayoutParams;

    public LinearLayoutHeightAnimation(View view, int duration, int startHeight, int endHeight) {
        setDuration(duration);
        mView = view;
        mStartHeight = startHeight;
        mEndHeight = endHeight;
        if (mStartHeight < 0)
            throw new IllegalArgumentException("startHeight must be positive or zero.");
        if (endHeight < 0)
            throw new IllegalArgumentException("endHeight must be positive or zero.");
        if (duration < 0)
            throw new IllegalArgumentException("duration must be a positive number.");
        if (mView == null)
            throw new IllegalArgumentException("view must not be null.");
        if (!(mView.getLayoutParams() instanceof LinearLayout.LayoutParams))
            throw new IllegalArgumentException("view must be in a linear layout with linear layout parameters.");
        mLayoutParams = (LinearLayout.LayoutParams) mView.getLayoutParams();
        mLayoutParams.height = mStartHeight;
        mView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        if (interpolatedTime < 1.0f) {
            mLayoutParams.height = interpolate(interpolatedTime);
            mView.requestLayout();
        } else {
            if (mEndHeight == 0)
                mView.setVisibility(View.GONE);
        }
    }

    protected int interpolate(float interpolatedTime) {
        float amount = (float) (1 - Math.cos(interpolatedTime * Math.PI)) / 2f; //sin
        //float amount = interpolatedTime //linear
        return (int) (mStartHeight * (1 - amount) + mEndHeight * amount);
    }
}
