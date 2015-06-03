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
    public boolean validate() {
        if (!super.validate()) return false;
        if (!mPattern.matcher(mView.getText()).matches()) {
            result(RESULT_INVALID);
            return false;
        }
        result(RESULT_OK);
        return true;
    }
}
