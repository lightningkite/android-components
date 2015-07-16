package com.lightningkite.androidcomponents.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lightningkite.androidcomponents.R;

/**
 * Created by jivie on 6/23/15.
 */
public class IOSToggle extends FrameLayout {

    private int mColor;
    private TextView mLeftText;
    private TextView mRightText;
    private boolean mRightSelected = true;
    private Drawable mLeftDrawable;
    private Drawable mRightDrawable;
    private Drawable mBorderDrawable;
    private View mSeparatorView;
    private OnStateChangeListener mListener = null;

    public OnStateChangeListener getListener() {
        return mListener;
    }

    public void setListener(OnStateChangeListener mListener) {
        this.mListener = mListener;
    }

    public boolean isRightSelected() {
        return mRightSelected;
    }

    public IOSToggle(Context context) {
        super(context);
        init();
    }

    public IOSToggle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.IOSToggle,
                0, 0);
        setColor(a.getColor(R.styleable.IOSToggle_color, 0xFF4444FF));
        setLeftText(a.getString(R.styleable.IOSToggle_leftText));
        setRightText(a.getString(R.styleable.IOSToggle_rightText));
    }

    private void init() {
        inflate(getContext(), R.layout.ui_ios_toggle, this);
        mLeftText = (TextView) findViewById(R.id.ui_ios_toggle_left_text);
        mRightText = (TextView) findViewById(R.id.ui_ios_toggle_right_text);
        ImageView mLeftImage = (ImageView) findViewById(R.id.ui_ios_toggle_left_image);
        ImageView mRightImage = (ImageView) findViewById(R.id.ui_ios_toggle_right_image);
        mLeftImage.setOnClickListener(mLeftListener);
        mRightImage.setOnClickListener(mRightListener);
        mLeftDrawable = mLeftImage.getDrawable();
        mRightDrawable = mRightImage.getDrawable();
        mBorderDrawable = findViewById(R.id.ui_ios_toggle_border).getBackground();
        mSeparatorView = findViewById(R.id.ui_ios_toggle_separator);
        initRightSelected(true);
    }

    public void setColor(int color) {
        mColor = color;
        mBorderDrawable.setColorFilter(makeFilter(1));
        mSeparatorView.setBackgroundColor(mColor);
        initRightSelected(mRightSelected);
    }

    private ColorMatrixColorFilter makeFilter(float colorAmount) {
        int colorRed = (mColor >> 16) & 0xFF;
        int colorGreen = (mColor >> 8) & 0xFF;
        int colorBlue = mColor & 0xFF;

        float white = 0xFF * (1 - colorAmount);
        int red = (int) (colorRed * colorAmount + white);
        int green = (int) (colorGreen * colorAmount + white);
        int blue = (int) (colorBlue * colorAmount + white);

        float[] matrix =
                {1, 0, 0, 0, red
                        , 0, 1, 0, 0, green
                        , 0, 0, 1, 0, blue
                        , 0, 0, 0, 1, 0};

        return new ColorMatrixColorFilter(matrix);
    }

    public void setLeftText(String text) {
        mLeftText.setText(text);
    }

    public void setRightText(String text) {
        mRightText.setText(text);
    }

    public void initRightSelected(boolean rightSelected) {
        internalSwitchSelected(rightSelected);
        mLeftDrawable.mutate().setColorFilter(makeFilter(rightSelected ? 0 : 1));
        mRightDrawable.mutate().setColorFilter(makeFilter(rightSelected ? 1 : 0));
    }

    public void setRightSelected(boolean rightSelected) {
        initRightSelected(rightSelected);
        if (mListener != null) mListener.stateChanged(this, rightSelected);
    }

    private ValueAnimator mAnimator;

    public void animateRightSelected(boolean rightSelected) {
        if (internalSwitchSelected(rightSelected)) return;
        if (mListener != null) mListener.stateChanged(this, rightSelected);
        if (mAnimator != null) mAnimator.end();
        mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.setDuration(300);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animTime = animation.getCurrentPlayTime() * 1f / animation.getDuration();
                float time = mRightSelected ? animTime : 1 - animTime;
                mLeftDrawable.mutate().setColorFilter(makeFilter(1 - time));
                mRightDrawable.mutate().setColorFilter(makeFilter(time));
            }
        });
        mAnimator.start();
    }

    private boolean internalSwitchSelected(boolean rightSelected) {
        mLeftText.setTextColor(rightSelected ? mColor : 0xFFFFFFFF);
        mRightText.setTextColor(rightSelected ? 0xFFFFFFFF : mColor);
        if (mRightSelected == rightSelected) return true;
        mRightSelected = rightSelected;
        return false;
    }

    private OnClickListener mLeftListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("IOSToggle", "left listener");
            animateRightSelected(false);
        }
    };

    private OnClickListener mRightListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("IOSToggle", "right listener");
            animateRightSelected(true);
        }
    };

    @SuppressWarnings("NullableProblems")
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("ss", super.onSaveInstanceState());
        bundle.putBoolean("rightSelected", mRightSelected);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        super.onRestoreInstanceState(bundle.getParcelable("ss"));
        initRightSelected(bundle.getBoolean("rightSelected"));
        //setRightSelected();
    }

    public interface OnStateChangeListener {
        void stateChanged(IOSToggle toggle, boolean isRightSelected);
    }
}
