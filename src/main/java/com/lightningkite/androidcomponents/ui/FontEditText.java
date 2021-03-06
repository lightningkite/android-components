package com.lightningkite.androidcomponents.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.lightningkite.androidcomponents.R;

/**
 * An EditText that can use a custom font.
 */
public class FontEditText extends EditText {
    public FontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);
        updateTypeface(a.getString(R.styleable.FontTextView_typeface));
    }

    public FontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);
        updateTypeface(a.getString(R.styleable.FontTextView_typeface));
    }

    public FontEditText(Context context) {
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
