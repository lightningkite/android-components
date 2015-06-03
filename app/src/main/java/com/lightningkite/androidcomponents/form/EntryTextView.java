package com.lightningkite.androidcomponents.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lightningkite.androidcomponents.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jivie on 5/7/15.
 */
public class EntryTextView extends FrameLayout implements FormEntry {
    @InjectView(R.id.entry_text_label)
    TextView mLabelTextView;
    @InjectView(R.id.entry_text_edit)
    EditText mTextView;
    private DoneListener mDoneListener;

    public DoneListener getDoneListener() {
        return mDoneListener;
    }

    public void setDoneListener(DoneListener mDoneListener) {
        this.mDoneListener = mDoneListener;
    }

    public EntryTextView(Context context) {
        super(context);
        init();
        mTextView.setInputType(InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
        mTextView.setSingleLine(true);
        mTextView.setImeOptions(EditorInfo.IME_ACTION_NEXT);
    }

    public EntryTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EntryTextView,
                0, 0);
        mLabelTextView.setText(a.getString(R.styleable.EntryTextView_labelText));
        mTextView.setHint(a.getString(R.styleable.EntryTextView_hintText));
        if (!isInEditMode()) {
            int inputMethod = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "inputType", InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            int imeOptions = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "imeOptions", EditorInfo.IME_ACTION_NEXT);
            mTextView.setInputType(a.getBoolean(R.styleable.EntryTextView_editable, true) ? inputMethod : InputType.TYPE_NULL);
            mTextView.setImeOptions(imeOptions);
        }
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.entry_text, this, true);
        ButterKnife.inject(this, this);
        mTextView.setSaveEnabled(false);
        mTextView.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d(EntryTextView.class.getName(), String.valueOf(KeyEvent.FLAG_EDITOR_ACTION));
                if (event != null) {
                    Log.d(EntryTextView.class.getName(), String.valueOf(event.getKeyCode()));
                    Log.d(EntryTextView.class.getName(), String.valueOf(KeyEvent.FLAG_EDITOR_ACTION));
                    if (event.getKeyCode() == KeyEvent.FLAG_EDITOR_ACTION) {
                        if (mDoneListener != null) {
                            mDoneListener.onDone(EntryTextView.this);
                            Log.d(EntryTextView.class.getName(), "DONE");
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }

    @Override
    public boolean hasFocus() {
        return super.hasFocus() || mTextView.hasFocus();
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    public String getText() {
        return mTextView.getText().toString();
    }

    public void addTextChangedListener(TextWatcher w) {
        mTextView.addTextChangedListener(w);
    }

    public void removeTextChangedListener(TextWatcher w) {
        mTextView.removeTextChangedListener(w);
    }

    public void setOnTextFocusChangeListener(OnFocusChangeListener listener) {
        mTextView.setOnFocusChangeListener(listener);
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setLabel(String label) {
        mLabelTextView.setText(label);
    }

    public void setHint(String hint) {
        mTextView.setHint(hint);
    }

    @Override
    public Object getData() {
        return mTextView.getText().toString();
    }

    @Override
    public void setData(Object object) {
        if (object instanceof String) {
            setText((String) object);
        } else {
            throw new IllegalArgumentException("Data must be a String");
        }
    }

    @Override
    public void focus() {
        mTextView.requestFocus();
    }

    @Override
    public void notifyLast() {
        mTextView.setImeOptions(EditorInfo.IME_ACTION_DONE);
    }

    public static abstract class ShortWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        abstract public void onTextChanged(CharSequence s, int start, int before, int count);

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putString("mTextView.text", mTextView.getText().toString());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            mTextView.setText(bundle.getString("mTextView.text"));
        }
    }

    public interface DoneListener {
        void onDone(EntryTextView tv);
    }
}
