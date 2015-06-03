package com.lightningkite.androidcomponents.example;

import retrofit.RestAdapter;

/**
 * Created by jivie on 6/3/15.
 */
public class WeatherAPIHelper {

    private static WeatherService mWeatherService;

    static public WeatherService getService() {
        if (mWeatherService != null) return mWeatherService;
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("http://api.openweathermap.org")
                .build();
        mWeatherService = adapter.create(WeatherService.class);
        return mWeatherService;
    }
}
