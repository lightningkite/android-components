package com.lightningkite.androidcomponents.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lightningkite.androidcomponents.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jivie on 6/8/15.
 */
public class EntryToggleView extends FrameLayout implements FormEntry {

    @InjectView(R.id.entry_toggle_button)
    ToggleButton mToggleButton;

    @InjectView(R.id.entry_toggle_label)
    TextView mLabelView;

    public EntryToggleView(Context context) {
        super(context);
        init();
    }

    public EntryToggleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EntryToggleView,
                0, 0);
        mLabelView.setText(a.getString(R.styleable.EntryToggleView_labelText));
    }

    public EntryToggleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EntryToggleView,
                0, 0);
        mLabelView.setText(a.getString(R.styleable.EntryToggleView_labelText));
    }

    private void init() {
        inflate(getContext(), R.layout.entry_toggle, this);
        ButterKnife.inject(this);
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
        mLabelView.setText(label);
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
