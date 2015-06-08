package com.lightningkite.androidcomponents.validator;

import android.widget.TextView;

/**
 * Created by jivie on 6/2/15.
 */
public class TextValidator extends Validator {

    public static final int RESULT_NO_TEXT_VIEW = 1;
    public static final int RESULT_REQUIRED = 2;

    protected TextView mView;
    protected final boolean mOptional;

    public TextValidator(TextView textView, boolean optional) {
        mView = textView;
        mOptional = optional;
    }

    public void setTextView(TextView v) {
        mView = v;
    }

    @Override
    public void validate() {
        if (mView == null) {
            result(RESULT_NO_TEXT_VIEW);
            return;
        }
        validate(mView.getText().toString());
    }

    public void validate(String text) {
        super.validate();
        if (mResult != RESULT_OK) return;

        if (text.length() == 0 && !mOptional) {
            result(RESULT_REQUIRED);
            return;
        }

        result(RESULT_OK);
    }

    @Override
    public void result(int code) {
        super.result(code, mView);
    }
}