package com.lightningkite.androidcomponents.validator;

import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * Created by jivie on 6/2/15.
 */
public class TextPatternValidator extends TextValidator {

    public static final int RESULT_INVALID = 2;
    private final Pattern mPattern;

    public TextPatternValidator(TextView textView, Pattern pattern, boolean optional) {
        super(textView, optional);
        mPattern = pattern;
    }

    @Override
    public void validate(String text) {
        super.validate(text);
        if (mResult != RESULT_OK) return;
        if (text.length() > 0 && !mPattern.matcher(text).matches()) {
            result(RESULT_INVALID);
            return;
        }
        result(RESULT_OK);
    }
}
