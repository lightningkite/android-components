package com.lightningkite.androidcomponents.validator;

import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * Created by jivie on 6/2/15.
 */
public class IntegerValidator extends TextPatternValidator {

    public static final Pattern INTEGER_PATTERN = Pattern.compile("[-+]?[0-9]+");

    public IntegerValidator(TextView textView) {
        super(textView, INTEGER_PATTERN);
    }
}
