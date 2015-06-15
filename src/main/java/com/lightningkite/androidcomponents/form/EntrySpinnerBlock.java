package com.lightningkite.androidcomponents.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.lightningkite.androidcomponents.R;

/**
 * A form block that holds a spinner.
 * Created by jivie on 6/8/15.
 */
public class EntrySpinnerBlock extends FrameLayout implements FormEntry {
    TextView mLabelTextView;
    Spinner mSpinnerView;


    static private int mDefaultLayoutRes = R.layout.entry_spinner;

    public static int getDefaultLayoutRes() {
        return mDefaultLayoutRes;
    }

    public static void setDefaultLayoutRes(int defaultLayoutRes) {
        mDefaultLayoutRes = defaultLayoutRes;
    }

    private int mLayoutRes = mDefaultLayoutRes;

    public EntrySpinnerBlock(Context context) {
        super(context);
        init();
    }

    public EntrySpinnerBlock(Context context, @LayoutRes int layout) {
        super(context);
        mLayoutRes = layout;
        init();
    }

    public EntrySpinnerBlock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EntrySpinnerBlock,
                0, 0);
        if (mLabelTextView != null) {
            mLabelTextView.setText(a.getString(R.styleable.EntrySpinnerBlock_labelText));
        }
    }

    private void init() {
        inflate(getContext(), mLayoutRes, this);
        mLabelTextView = (TextView) findViewById(R.id.entry_spinner_label);
        mSpinnerView = (Spinner) findViewById(R.id.entry_spinner_spinner);
    }

    public void setLabel(String label) {
        if (mLabelTextView != null) {
            mLabelTextView.setText(label);
        }
    }

    public void setAdapter(SpinnerAdapter adapter) {
        mSpinnerView.setAdapter(adapter);
    }

    @Override
    public Object getData() {
        return mSpinnerView.getSelectedItemPosition();
    }

    @Override
    public void setData(Object object) {
        mSpinnerView.setSelection((Integer) object);
    }

    @Override
    public void focus() {
        mSpinnerView.requestFocus();
    }

    @Override
    public void notifyLast() {
    }
}
