package com.lightningkite.acexample.model;

import com.google.gson.annotations.SerializedName;
import com.lightningkite.androidcomponents.form.AutoFormDeep;
import com.lightningkite.androidcomponents.form.AutoFormIgnore;

import java.util.ArrayList;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushList;

/**
 * An example Rush object model for the OpenWeather API.
 * Note the use of the @RushList annotation, and the @SerializedName annotation.
 * Created by jivie on 6/3/15.
 */
public class CurrentWeather extends RushObject {
    @AutoFormDeep
    public Coordinates coord;
    @AutoFormDeep
    public Sys sys;
    @AutoFormIgnore
    @RushList(classType = Weather.class)
    public ArrayList<Weather> weather;
    @AutoFormDeep
    public Main main;
    @AutoFormDeep
    public Wind wind;
    @AutoFormDeep
    public Rain rain;
    @AutoFormDeep
    public Clouds clouds;
    public long dt;
    public long id;
    public String name;
    public long cod;

    static public class Coordinates extends RushObject {
        public double lon;
        public double lat;
    }

    static public class Sys extends RushObject {
        public String country;
        public long sunrise;
        public long sunset;
    }

    static public class Weather extends RushObject {
        public long id;
        public String main;
        public String description;
        public String icon;
    }

    static public class Main extends RushObject {
        public double temp;
        public double humidity;
        public double pressure;
        public double temp_min;
        public double temp_max;
    }

    static public class Wind extends RushObject {
        public double speed;
        public double deg;
    }

    static public class Rain extends RushObject {
    }

    static public class Clouds extends RushObject {
        @SerializedName("all")
        public double clouds_all;
    }

    @Override
    public String toString() {
        if (weather.size() > 0) {
            return weather.get(0).description;
        }
        return String.valueOf(main.temp);
    }
}
