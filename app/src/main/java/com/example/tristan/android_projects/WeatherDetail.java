package com.example.tristan.android_projects;

/**
 * Created by Tristan on 3/12/18.
 */

public class WeatherDetail {
    String location, icon_url, weather, temp_f, feelslike_f, relative_humidity, visibility_mi, wind_string,
        wind_gust_mph, observation_time;

    public WeatherDetail(String location, String icon_url, String weather, String temp_f, String feelslike_f, String relative_humidity, String visibility_mi, String wind_string, String wind_gust_mph, String observation_time) {
        this.location = location;
        this.icon_url = icon_url;
        this.weather = weather;
        this.temp_f = temp_f;
        this.feelslike_f = feelslike_f;
        this.relative_humidity = relative_humidity;
        this.visibility_mi = visibility_mi;
        this.wind_string = wind_string;
        this.wind_gust_mph = wind_gust_mph;
        this.observation_time = observation_time;
    }
}
