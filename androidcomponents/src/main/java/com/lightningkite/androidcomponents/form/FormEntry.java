package com.lightningkite.androidcomponents.form;

/**
 * Used for saving state.
 * Created by jivie on 6/2/15.
 */
public interface FormEntry {
    Object getData();

    void setData(Object object);

    void focus();

    void notifyLast();
}
