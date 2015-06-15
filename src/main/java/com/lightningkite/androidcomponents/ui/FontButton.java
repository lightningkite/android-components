package com.lightningkite.androidcomponents.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.lightningkite.androidcomponents.R;


/**
 * A button that uses the custom font.
 * Created by Joseph on 1/20/2015.
 */
public class FontButton extends Button {
    public FontButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);
        updateTypeface(a.getString(R.styleable.FontTextView_font));
    }

    public FontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);
        updateTypeface(a.getString(R.styleable.FontTextView_font));
    }

    public FontButton(Context context) {
        super(context);
        updateTypeface(null);
    }

    private void updateTypeface(String fontName) {
        Typeface tf;
        if (fontName == null)
            fontName = Fonts.DEFAULT;
        tf = Fonts.getTypeface(getContext(), fontName);

        if (tf != null)
            setTypeface(tf);
    }
}