package com.lightningkite.androidcomponents.validator;

import android.widget.TextView;

/**
 * Created by jivie on 6/2/15.
 */
public class PasswordValidator extends TextValidator {

    public static final int ERROR_NOT_LONG_ENOUGH = 2;
    private final int mMinLength;

    public PasswordValidator(TextView textView, int minLength) {
        super(textView, true);
        mMinLength = minLength;
    }

    @Override
    public void validate(String text) {
        super.validate(text);
        if (mResult != RESULT_OK) return;

        if (text.length() < mMinLength) {
            result(ERROR_NOT_LONG_ENOUGH);
        }
    }
}
