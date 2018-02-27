/* Tristan Griffin
In Class 06
Date: Feb 26
Group 16
 */

package com.example.tristan.android_projects;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Articles> articles;
    NewsAdapter newsAdapter;
    ListView listView;
    AlertDialog.Builder alertDialog;
    String[] categories = {"General", "Entertainment", "Business", "Health"};

    String category = null;
    private static String API_KEY = "32f953d146be4482b9e6476ae08a6dba";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alertDialog = new AlertDialog.Builder(MainActivity.this);


        final TextView textView = (TextView) findViewById(R.id.textView);
        listView = (ListView) findViewById(R.id.listView);

        if (isConnected()){
            findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.setTitle("Select from the list");
                    alertDialog.setItems(categories, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            category = categories[i];
                            textView.setText(category);
                            final String stringURL = "https://newsapi.org/v2/top-headlines?country=us&category=" + category +
                                    "&apiKey=" + API_KEY;
                            new GetAsyncData().execute(stringURL);
                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                }
            });
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

    private class GetAsyncData extends AsyncTask<String, Void, ArrayList<Articles>> {

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        @Override
        protected void onPreExecute() {
            progressBar.setMax(100);
            progressBar.setProgress(0);
        }

        @Override
        protected ArrayList<Articles> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                articles = new ArrayList<>();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        JSONObject object = new JSONObject(line);
                        JSONArray articlesArray = object.getJSONArray("articles");

                        for (int i = 0; i < articlesArray.length(); i++) {
                            JSONObject sourceObject = articlesArray.getJSONObject(i);
                            Articles article = new Articles(sourceObject.getString("title"),
                                    sourceObject.getString("description"), sourceObject.getString("publishedAt"),
                                    sourceObject.getString("urlToImage"));
                            articles.add(article);
                            progressBar.setProgress(progressBar.getProgress() + (100 / articlesArray.length()));
                        }
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return articles;
        }

        @Override
        protected void onPostExecute(ArrayList<Articles> strings) {
            newsAdapter = new NewsAdapter(MainActivity.this, R.layout.news_view, strings);
            listView.setAdapter(newsAdapter);
        }
    }
}
