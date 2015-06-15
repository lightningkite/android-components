package com.lightningkite.androidcomponents.util;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Simplifies the retrieval of the last known location of the device.
 * Created by Joseph on 1/29/2015.
 */
public class LocationUtils {
    static public Location getLastKnownLocation(Activity activity) {
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        Location lastKnownLocation;
        lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastKnownLocation == null)
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (lastKnownLocation == null)
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        return lastKnownLocation;
    }
}
