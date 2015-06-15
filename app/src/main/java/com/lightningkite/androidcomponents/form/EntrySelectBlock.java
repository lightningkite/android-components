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

import org.parceler.Parcels;

import java.io.Serializable;

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
    public static final String DATA_OBJECT = "id";
    private static final String DATA_TYPE = "type";
    @Optional
    @InjectView(R.id.entry_select_label)
    TextView mLabelTextView;
    @InjectView(R.id.entry_select_text)
    TextView mTextView;
    @InjectView(R.id.entry_select_icon)
    ImageView mIconView;
    private EntrySelectListener mListener;
    private boolean mEditable = true;
    private Object mData = null;


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
        return mData;
    }

    @Override
    public void setData(Object object) {
        mData = object;
        setText(String.valueOf(mData));
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

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        if(mData instanceof Integer) {
            bundle.putInt(DATA_TYPE, 1);
            bundle.putInt(DATA_OBJECT, (Integer) mData);
        } else if(mData instanceof Long) {
            bundle.putInt(DATA_TYPE, 2);
            bundle.putLong(DATA_OBJECT, (Long) mData);
        } else if(mData instanceof Float) {
            bundle.putInt(DATA_TYPE, 3);
            bundle.putFloat(DATA_OBJECT, (Float) mData);
        } else if(mData instanceof Double) {
            bundle.putInt(DATA_TYPE, 4);
            bundle.putDouble(DATA_OBJECT, (Double) mData);
        } else if(mData instanceof Boolean) {
            bundle.putInt(DATA_TYPE, 5);
            bundle.putBoolean(DATA_OBJECT, (Boolean) mData);
        } else if(mData instanceof String) {
            bundle.putInt(DATA_TYPE, 6);
            bundle.putString(DATA_OBJECT, (String) mData);
        } else if(mData instanceof int[]) {
            bundle.putInt(DATA_TYPE, 101);
            bundle.putIntArray(DATA_OBJECT, (int[]) mData);
        } else if(mData instanceof long[]) {
            bundle.putInt(DATA_TYPE, 102);
            bundle.putLongArray(DATA_OBJECT, (long[]) mData);
        } else if(mData instanceof float[]) {
            bundle.putInt(DATA_TYPE, 103);
            bundle.putFloatArray(DATA_OBJECT, (float[]) mData);
        } else if(mData instanceof double[]) {
            bundle.putInt(DATA_TYPE, 104);
            bundle.putDoubleArray(DATA_OBJECT, (double[]) mData);
        } else if(mData instanceof boolean[]) {
            bundle.putInt(DATA_TYPE, 105);
            bundle.putBooleanArray(DATA_OBJECT, (boolean[]) mData);
        } else if(mData instanceof String[]) {
            bundle.putInt(DATA_TYPE, 16);
            bundle.putStringArray(DATA_OBJECT, (String[]) mData);
        } else if(mData instanceof Parcelable) {
            bundle.putInt(DATA_TYPE, 7);
            bundle.putParcelable(DATA_OBJECT, (Parcelable) mData);
        } else if(mData instanceof Serializable) {
            bundle.putInt(DATA_TYPE, 8);
            bundle.putSerializable(DATA_OBJECT, (Serializable) mData);
        } else {
            bundle.putInt(DATA_TYPE, 0);
            bundle.putParcelable(DATA_OBJECT, Parcels.wrap(mData));
        }
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            switch(bundle.getInt(DATA_TYPE)){
                case 0:
                    mData = Parcels.unwrap((Parcelable) bundle.get(DATA_OBJECT));
                    break;
                default:
                    mData = bundle.get(DATA_OBJECT);
            }
            mTextView.setText(String.valueOf(mData));
        }
    }
}
