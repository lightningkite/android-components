package com.lightningkite.androidcomponents.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains all of the font resources.
 * Created by Joseph on 1/20/2015.
 */
public class Fonts {
    public static final String DEFAULT = "";

    static private Map<String, Typeface> typefaceMap = new HashMap<>();

    /**
     * Loads a TTF font or returns it if already loaded.
     *
     * @param context      Context to use for loading.
     * @param resourceName Filename of the font without the ".ttf".
     * @return The typeface generated from the TTF file.
     */
    static public Typeface getTypeface(@NonNull Context context, String resourceName) {
        if (resourceName == null) {
            Log.w("Fonts", "Input is null; returning default typeface.");
            return Typeface.DEFAULT;
        } else if (resourceName.isEmpty()) {
            return Typeface.DEFAULT;
        }
        if (typefaceMap.containsKey(resourceName)) {
            return typefaceMap.get(resourceName);
        } else {
            try {
                Typeface typeface = Typeface.createFromAsset(context.getAssets(), resourceName + ".ttf");
                typefaceMap.put(resourceName, typeface);
                return typeface;
            } catch (Exception e) {
                e.printStackTrace();
                Log.w("Fonts", "Font " + resourceName + " failed to be retrieved.");
                return Typeface.DEFAULT;
            }
        }
    }
}
