package com.lightningkite.androidcomponents.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lightningkite.androidcomponents.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * A very simple form block that just has a listener that is called when the user taps it.
 * IMPORTANT: This is meant to call a class to select somthing, as it shows a piece of text and
 * stores a long id.  Think of it as an advanced Spinner.
 * Created by jivie on 5/7/15.
 */
public class EntrySelectBlock extends FrameLayout implements FormEntry {
    public static final String DATA_TEXT = "text";
    public static final String DATA_ID = "id";
    @Optional
    @InjectView(R.id.entry_select_label)
    TextView mLabelTextView;
    @InjectView(R.id.entry_select_text)
    TextView mTextView;
    @InjectView(R.id.entry_select_icon)
    ImageView mIconView;
    private EntrySelectListener mListener;
    private boolean mEditable;


    static private int mDefaultLayoutRes = R.layout.entry_select;

    public static int getDefaultLayoutRes() {
        return mDefaultLayoutRes;
    }

    public static void setDefaultLayoutRes(int defaultLayoutRes) {
        mDefaultLayoutRes = defaultLayoutRes;
    }

    private int mLayoutRes = mDefaultLayoutRes;

    public EntrySelectBlock(Context context) {
        super(context);
        init();
    }

    public EntrySelectBlock(Context context, @LayoutRes int layout) {
        super(context);
        mLayoutRes = layout;
        init();
    }

    public EntrySelectBlock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EntrySelectBlock,
                0, 0);
        if (mLabelTextView != null) {
            mLabelTextView.setText(a.getString(R.styleable.EntrySelectBlock_labelText));
        }
        mTextView.setHint(a.getString(R.styleable.EntrySelectBlock_hintText));
        mIconView.setImageDrawable(a.getDrawable(R.styleable.EntrySelectBlock_selectIcon));
        mEditable = a.getBoolean(R.styleable.EntrySelectBlock_editable, true);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(mLayoutRes, this, true);
        ButterKnife.inject(this, this);
        mTextView.setSaveEnabled(false);
        mTextView.setOnClickListener(mClickListener);
        mIconView.setOnClickListener(mClickListener);
    }

    public void setLabel(String label) {
        if (mLabelTextView != null) {
            mLabelTextView.setText(label);
        }
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
                mListener.onSelect(EntrySelectBlock.this);
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

    @Override
    public void focus() {
    }

    @Override
    public void notifyLast() {
    }

    public interface EntrySelectListener {
        void onSelect(EntrySelectBlock v);
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
        bundle.putString(DATA_TEXT, mTextView.getText().toString());
        bundle.putLong(DATA_ID, mSelectedId);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            mTextView.setText(bundle.getString(DATA_TEXT));
            mSelectedId = bundle.getLong(DATA_ID);
        }
    }
}
