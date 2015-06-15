package com.lightningkite.androidcomponents.form;

import android.widget.SpinnerAdapter;

/**
 * Created by jivie on 6/10/15.
 */
public interface SpinnerAdapterFetcher {
    <T> SpinnerAdapter fetch(Class<T> clazz);

    long getId(Class<?> clazz, Object obj);
}
