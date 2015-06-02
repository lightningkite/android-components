package com.lightningkite.androidcomponents.validator;

import android.widget.TextView;

/**
 * Created by jivie on 6/2/15.
 */
public class PasswordValidator extends TextValidator {

    public static final int ERROR_NOT_LONG_ENOUGH = 2;
    private final int mMinLength;

    public PasswordValidator(TextView textView, int minLength) {
        super(textView);
        mMinLength = minLength;
    }

    @Override
    public boolean validate() {
        if (!super.validate()) return false;

        String text = mView.getText().toString();
        if (text.length() < mMinLength) {
            onError(ERROR_NOT_LONG_ENOUGH);
            return false;
        }

        return true;
    }
}
