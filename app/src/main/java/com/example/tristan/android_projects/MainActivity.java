/*
Name: Tristan Griffin
Date: March 12
MIDTERM
Group: 16
 */
package com.example.tristan.android_projects;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//Key: daa59a3075b38048
public class MainActivity extends AppCompatActivity {

    TextView search;
    String city;

    CityInfoAdapter cityInfoAdapter;
    ListView listView;

    ArrayList<CityInfo> info;

    private static String API_KEY = "daa59a3075b38048";

    public static String WEATHER_INFO_KEY = "WEATHER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.editText);
        listView = (ListView) findViewById(R.id.listView);

        if (isConnected()) {
            findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    city = search.getText().toString();

                    final String stringURL = "http://autocomplete.wunderground.com/aq?query=" + city;

                    new GetDataAsync().execute(stringURL);
                }
            });
        } else {
            Toast.makeText(this, "Not Connected to the Internet", Toast.LENGTH_SHORT).show();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, WeatherInfo.class);
                Log.d("demo", info.get(i).zmw);
                intent.putExtra(WEATHER_INFO_KEY, info.get(i).zmw);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    private class GetDataAsync extends AsyncTask<String, Void, ArrayList<CityInfo>> {

        String name, country, zmw;

        @Override
        protected ArrayList<CityInfo> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            StringBuilder builder = new StringBuilder();

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                info = new ArrayList<>();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String result = "";

                    while ((result = reader.readLine()) != null) {
                        builder.append(result);
                    }
                    result = builder.toString();

                    JSONObject object = new JSONObject(result);
                    JSONArray resultsArray = object.getJSONArray("RESULTS");

                    for (int i = 0; i < resultsArray.length(); i++) {
                        JSONObject resultObject = resultsArray.getJSONObject(i);
                        name = resultObject.getString("name");
                        country = resultObject.getString("c");
                        zmw = resultObject.getString("zmw");

                        CityInfo cityInfo = new CityInfo(name, country, zmw);
                        info.add(cityInfo);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return info;
        }

        @Override
        protected void onPostExecute(ArrayList<CityInfo> infos) {
            cityInfoAdapter = new CityInfoAdapter(MainActivity.this, R.layout.new_view, infos);
            listView.setAdapter(cityInfoAdapter);
        }
    }
}
