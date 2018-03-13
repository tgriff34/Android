package com.example.tristan.android_projects;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherInfo extends AppCompatActivity {

    String zmw;
    private static String API_KEY = "daa59a3075b38048";

    TextView cityNameView, weatherTypeView, tempView, likeView,
            humidView, visibilityView, windView, lastUpdatedView;

    ImageView weatherPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);

        cityNameView = findViewById(R.id.cityNameView);
        weatherTypeView = findViewById(R.id.weatherTypeView);
        tempView = findViewById(R.id.tempView);
        likeView = findViewById(R.id.likeView);
        humidView = findViewById(R.id.humidView);
        visibilityView = findViewById(R.id.visibilityView);
        windView = findViewById(R.id.windView);
        lastUpdatedView = findViewById(R.id.lastUpdatedView);
        weatherPic = findViewById(R.id.weatherPic);

        if (getIntent().getExtras().containsKey(MainActivity.WEATHER_INFO_KEY)) {
            zmw = getIntent().getStringExtra(MainActivity.WEATHER_INFO_KEY);
            Log.d("demo", zmw);
            String stringURL = "http://api.wunderground.com/api/" + API_KEY + "/conditions/q/" + zmw + ".json";
            Log.d("demo", stringURL);
            new GetWeatherInfoAsync().execute(stringURL);
        }

        findViewById(R.id.homeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeatherInfo.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private class GetWeatherInfoAsync extends AsyncTask<String, Void, WeatherDetail> {

        String location, icon_url, weather, temp_f, feelslike_f, relative_humidity, visibility_mi, wind_string,
                wind_gust_mph, observation_time;

        WeatherDetail weatherDetail;

        @Override
        protected WeatherDetail doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            StringBuilder builder = new StringBuilder();

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String result = "";

                    while ((result = reader.readLine()) != null) {
                        builder.append(result);
                    }
                    result = builder.toString();

                    JSONObject object = new JSONObject(result);
                    Log.d("demo", object.toString());
                    JSONObject currentObservationObject = object.getJSONObject("current_observation");
                    JSONObject locationObject = currentObservationObject.getJSONObject("display_location");

                    icon_url = currentObservationObject.getString("icon_url");
                    weather = currentObservationObject.getString("weather");
                    temp_f = currentObservationObject.getString("temp_f");
                    feelslike_f = currentObservationObject.getString("feelslike_f");
                    relative_humidity = currentObservationObject.getString("relative_humidity");
                    visibility_mi = currentObservationObject.getString("visibility_mi");
                    wind_string = currentObservationObject.getString("wind_string");
                    wind_gust_mph = currentObservationObject.getString("wind_gust_mph");
                    observation_time = currentObservationObject.getString("observation_time");
                    location = locationObject.getString("full");

                    weatherDetail = new WeatherDetail(location, icon_url, weather, temp_f, feelslike_f,
                            relative_humidity, visibility_mi, wind_string, wind_gust_mph, observation_time);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weatherDetail;
        }

        @Override
        protected void onPostExecute(WeatherDetail weatherDetail) {
            cityNameView.setText(weatherDetail.location);
            weatherTypeView.setText(weatherDetail.weather);
            tempView.setText("Temperature: " + weatherDetail.temp_f + " F");
            likeView.setText("Feels like: " + weatherDetail.feelslike_f + " F");
            humidView.setText("Humidity: " + weatherDetail.relative_humidity);
            visibilityView.setText("Visibility: " + weatherDetail.relative_humidity);
            windView.setText("Wind: " + weatherDetail.wind_string + ", " + weatherDetail.wind_gust_mph);
            lastUpdatedView.setText(weatherDetail.observation_time);
            Picasso.with(WeatherInfo.this).load(weatherDetail.icon_url).into(weatherPic);
        }
    }
}
