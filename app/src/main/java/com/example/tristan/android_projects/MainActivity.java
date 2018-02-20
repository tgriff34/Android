/*
Name: Tristan Griffin
Group: 16
Date:Feb 19
INCLASS-05
 */
package com.example.tristan.android_projects;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

//32f953d146be4482b9e6476ae08a6dba

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> result;
    ArrayList<String> sources;
    ListView listView;

    public static final String NEWS_LIST="NEWS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        if (isConnected()) {
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
            new GetAsyncData().execute("https://newsapi.org/v1/sources");

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //Toast.makeText(MainActivity.this, sources.get(i), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                    intent.putExtra(NEWS_LIST, sources.get(i));
                    startActivity(intent);
                }
            });


        } else {
            Toast.makeText(this, "Not Connected", Toast.LENGTH_SHORT).show();
        }

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

    private class GetAsyncData extends AsyncTask<String, Void, ArrayList<String>> {

        private ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading Sources ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            result = new ArrayList<>();
            sources = new ArrayList<>();
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        JSONObject object = new JSONObject(line);
                        JSONArray sourcesArray = object.getJSONArray("sources");
                        for (int i = 0; i < sourcesArray.length(); i++) {
                            JSONObject sourceObject = sourcesArray.getJSONObject(i);
                            result.add(sourceObject.getString("name"));
                            sources.add(sourceObject.getString("id"));
                        }
                    }
                }
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, strings);
            listView.setAdapter(arrayAdapter);
            progressDialog.dismiss();
        }
    }
}
