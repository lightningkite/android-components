package com.lightningkite.androidcomponents.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lightningkite.androidcomponents.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jivie on 5/7/15.
 */
public class EntrySelectView extends FrameLayout implements FormEntry {
    public static final String DATA_TEXT = "text";
    public static final String DATA_ID = "id";
    @InjectView(R.id.entry_select_label)
    TextView mLabelTextView;
    @InjectView(R.id.entry_select_text)
    TextView mTextView;
    @InjectView(R.id.entry_select_icon)
    ImageView mIconView;
    private EntrySelectListener mListener;
    private boolean mEditable;

    public EntrySelectView(Context context) {
        super(context);
        init();
    }

    public EntrySelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EntrySelectView,
                0, 0);
        mLabelTextView.setText(a.getString(R.styleable.EntrySelectView_labelText));
        mTextView.setHint(a.getString(R.styleable.EntrySelectView_hintText));
        mIconView.setImageDrawable(a.getDrawable(R.styleable.EntrySelectView_selectIcon));
        mEditable = a.getBoolean(R.styleable.EntrySelectView_editable, true);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.entry_select, this, true);
        ButterKnife.inject(this, this);
        mTextView.setSaveEnabled(false);
        mTextView.setOnClickListener(mClickListener);
        mIconView.setOnClickListener(mClickListener);
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    public void setSelectListener(EntrySelectListener listener) {
        mListener = listener;
    }

    private OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListener != null && mEditable) {
                mListener.onSelect(EntrySelectView.this);
            }
        }
    };

    @Override
    public Object getData() {
        Bundle data = new Bundle();
        data.putString(DATA_TEXT, mTextView.getText().toString());
        data.putLong(DATA_ID, mSelectedId);
        return data;
    }

    @Override
    public void setData(Object object) {
        if (object instanceof Bundle) {
            Bundle data = (Bundle) object;
            mSelectedId = data.getLong(DATA_ID, -1);
            setText(data.getString(DATA_TEXT));
        } else {
            throw new IllegalArgumentException("Data must be a Bundle with an ID and text in it!");
        }
    }

    public interface EntrySelectListener {
        void onSelect(EntrySelectView v);
    }

    private long mSelectedId = -1;

    public long getSelectedId() {
        return mSelectedId;
    }

    public void setSelectedId(long mSelectedId) {
        this.mSelectedId = mSelectedId;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putString("mTextView.text", mTextView.getText().toString());
        bundle.putLong("mSelectedId", mSelectedId);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            mTextView.setText(bundle.getString("mTextView.text"));
            mSelectedId = bundle.getLong("mSelectedId");
        }
    }
}
