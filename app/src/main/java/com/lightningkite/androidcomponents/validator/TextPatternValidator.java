package com.lightningkite.androidcomponents.validator;

import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * Created by jivie on 6/2/15.
 */
public class TextPatternValidator extends TextValidator {

    public static final int ERROR_INVALID = 2;
    private final Pattern mPattern;

    public TextPatternValidator(TextView textView, Pattern pattern) {
        super(textView);
        mPattern = pattern;
    }

    @Override
    public boolean validate() {
        if (!super.validate()) return false;
        if (!mPattern.matcher(mView.getText()).matches()) {
            onError(ERROR_INVALID);
            return false;
        }
        return true;
    }
}
