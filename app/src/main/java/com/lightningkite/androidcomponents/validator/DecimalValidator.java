package com.lightningkite.androidcomponents.validator;

import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * Created by jivie on 6/2/15.
 */
public class DecimalValidator extends TextPatternValidator {

    public static final Pattern DECIMAL_PATTERN = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");

    public DecimalValidator(TextView textView) {
        super(textView, DECIMAL_PATTERN);
    }
}
