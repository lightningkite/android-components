package com.lightningkite.androidcomponents.validator;

import android.widget.TextView;

/**
 * Created by jivie on 6/2/15.
 */
public class TextValidator extends Validator {

    public static final int RESULT_NO_TEXT_VIEW = 1;
    public static final int RESULT_REQUIRED = 2;

    protected TextView mView;
    protected final boolean mRequired;

    public TextValidator(TextView textView, boolean required) {
        mView = textView;
        mRequired = required;
    }

    public void setTextView(TextView v) {
        mView = v;
    }

    @Override
    public boolean validate() {
        if (mView == null) {
            result(RESULT_NO_TEXT_VIEW);
            return false;
        }

        if (mView.length() == 0 && mRequired) {
            result(RESULT_REQUIRED);
            return false;
        }

        result(RESULT_OK);
        return true;
    }

    @Override
    public void result(int code) {
        super.result(code, mView);
    }
}