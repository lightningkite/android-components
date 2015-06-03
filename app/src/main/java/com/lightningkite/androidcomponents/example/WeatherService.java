package com.lightningkite.androidcomponents.example;

import com.lightningkite.androidcomponents.example.model.CurrentWeather;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by jivie on 6/3/15.
 */
public interface WeatherService {
    @GET("/data/2.5/weather")
    void getZipWeather(@Query("zip") String city, Callback<CurrentWeather> cb);
}
