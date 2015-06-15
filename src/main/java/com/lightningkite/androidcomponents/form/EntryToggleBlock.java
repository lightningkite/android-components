package com.lightningkite.androidcomponents.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lightningkite.androidcomponents.R;

/**
 * A form block that holds a toggle button.
 * Created by jivie on 6/8/15.
 */
public class EntryToggleBlock extends FrameLayout implements FormEntry {

    ToggleButton mToggleButton;
    TextView mLabelTextView;

    static private int mDefaultLayoutRes = R.layout.entry_toggle;

    public static int getDefaultLayoutRes() {
        return mDefaultLayoutRes;
    }

    public static void setDefaultLayoutRes(int defaultLayoutRes) {
        mDefaultLayoutRes = defaultLayoutRes;
    }

    private int mLayoutRes = mDefaultLayoutRes;

    public EntryToggleBlock(Context context) {
        super(context);
        init();
    }

    public EntryToggleBlock(Context context, @LayoutRes int layout) {
        super(context);
        mLayoutRes = layout;
        init();
    }

    public EntryToggleBlock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EntryToggleBlock,
                0, 0);
        if (mLabelTextView != null) {
            mLabelTextView.setText(a.getString(R.styleable.EntryToggleBlock_labelText));
        }
    }

    private void init() {
        inflate(getContext(), mLayoutRes, this);
        mToggleButton = (ToggleButton) findViewById(R.id.entry_toggle_button);
        mLabelTextView = (TextView) findViewById(R.id.entry_toggle_label);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putBoolean("checked", mToggleButton.isChecked());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            mToggleButton.setChecked(bundle.getBoolean("checked"));
        }
    }

    public void setLabel(String label) {
        if (mLabelTextView != null) {
            mLabelTextView.setText(label);
        }
    }

    public boolean isChecked() {
        return mToggleButton.isChecked();
    }

    public void setChecked(boolean checked) {
        mToggleButton.setChecked(checked);
    }

    @Override
    public Object getData() {
        return mToggleButton.isChecked();
    }

    @Override
    public void setData(Object object) {
        if (!(object instanceof Boolean)) return;
        mToggleButton.setChecked((Boolean) object);
    }

    @Override
    public void focus() {
        mToggleButton.requestFocus();
    }

    @Override
    public void notifyLast() {

    }
}
