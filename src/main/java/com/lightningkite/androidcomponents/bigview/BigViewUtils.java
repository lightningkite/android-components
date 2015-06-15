package com.lightningkite.androidcomponents.bigview;

import android.os.Bundle;

/**
 * Utility functions for dealing with BigViews, meant to be primarily used by classes that implement
 * BigViewContainer.
 * Created by jivie on 6/2/15.
 */
public class BigViewUtils {
    public static BigView make(String bigViewClassName, BigViewContainer container, Bundle arguments, int id) {
        try {
            return make((Class<? extends BigView>) Class.forName(bigViewClassName), container, arguments, id);
        } catch (Exception e) {
            e.printStackTrace();
            return new BigView(container, arguments, id);
        }
    }

    public static BigView make(Class<? extends BigView> bigViewClass, BigViewContainer container, Bundle arguments, int id) {
        try {
            return bigViewClass.getConstructor(BigViewContainer.class, Bundle.class, int.class).newInstance(container, arguments, id);
        } catch (Exception e) {
            e.printStackTrace();
            return new BigView(container, arguments, id);
        }
    }
}
