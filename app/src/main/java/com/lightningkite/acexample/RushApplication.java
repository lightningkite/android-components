package com.lightningkite.acexample;

import android.app.Application;

import co.uk.rushorm.android.RushAndroid;

/**
 * Created by jivie on 6/3/15.
 */
public class RushApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RushAndroid.initialize(getApplicationContext());
    }
}
