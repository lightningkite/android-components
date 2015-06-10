package com.lightningkite.androidcomponents.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.lightningkite.androidcomponents.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * A form block that holds a spinner.
 * Created by jivie on 6/8/15.
 */
public class EntrySpinnerBlock extends FrameLayout implements FormEntry {
    @Optional
    @InjectView(R.id.entry_spinner_label)
    TextView mLabelTextView;

    @InjectView(R.id.entry_spinner_spinner)
    Spinner mSpinnerView;

    public EntrySpinnerBlock(Context context) {
        super(context);
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
        inflate(getContext(), R.layout.entry_spinner, this);
        ButterKnife.inject(this);
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
