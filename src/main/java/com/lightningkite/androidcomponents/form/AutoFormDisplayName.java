package com.lightningkite.androidcomponents.form;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by jivie on 6/10/15.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFormDisplayName {
    String value();
}
