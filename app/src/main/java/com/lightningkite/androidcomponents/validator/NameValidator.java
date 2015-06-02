package com.lightningkite.androidcomponents.validator;

import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * Created by jivie on 6/2/15.
 */
public class NameValidator extends TextPatternValidator {

    public static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z'-]+");

    public NameValidator(TextView textView) {
        super(textView, NAME_PATTERN);
    }
}
