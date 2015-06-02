package com.lightningkite.androidcomponents.validator;

import android.widget.TextView;

/**
 * Created by jivie on 6/2/15.
 */
public class TextValidator implements Validator {

    public static final int ERROR_EMPTY = 0;
    public static final int ERROR_NO_TEXT_VIEW = 1;

    protected final TextView mView;

    public TextValidator(TextView textView) {
        mView = textView;
    }

    @Override
    public boolean validate() {
        if (mView == null) {
            onError(ERROR_NO_TEXT_VIEW);
            return false;
        }

        String text = mView.getText().toString();

        if (text.isEmpty()) {
            onError(ERROR_EMPTY);
            return false;
        }
        return true;
    }

    public void onError(int errorCode) {
        mView.setBackgroundColor(0xFFFFAAAA);
    }
}