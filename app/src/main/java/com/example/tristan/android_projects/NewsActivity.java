package com.example.tristan.android_projects;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    NewsAdapter newsAdapter;
    ArrayList<NewArticles> newsActivities;
    ListView listView;

    String apiKey = "32f953d146be4482b9e6476ae08a6dba";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        listView = (ListView) findViewById(R.id.listView2);

        if (getIntent().getExtras() != null) {
            String id = getIntent().getExtras().getString(MainActivity.NEWS_LIST);

            try {
                String stringURL = "https://newsapi.org/v1/articles" + "?" +
                        "source=" + URLEncoder.encode(id, "UTF-8") +
                        "&" + "apiKey=" + URLEncoder.encode(apiKey, "UTF-8");

                Log.d("demo", stringURL);
                new GetAsyncNewsData().execute(stringURL);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

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

    private class GetAsyncNewsData extends AsyncTask<String, Void, ArrayList<NewArticles>> {

        private ProgressDialog progressDialog = new ProgressDialog(NewsActivity.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loadings Articles ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected ArrayList<NewArticles> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                newsActivities = new ArrayList<>();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        JSONObject object = new JSONObject(line);
                        JSONArray sourcesArray = object.getJSONArray("articles");
                        for (int i = 0; i < sourcesArray.length(); i++) {
                            JSONObject sourceObject = sourcesArray.getJSONObject(i);
                            NewArticles newArticles = new NewArticles(sourceObject.getString("author"),
                                    sourceObject.getString("title"), sourceObject.getString("publishedAt"),
                                    sourceObject.getString("urlToImage"));

                            newsActivities.add(newArticles);
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
            return newsActivities;
        }

        @Override
        protected void onPostExecute(ArrayList<NewArticles> strings) {
            newsAdapter = new NewsAdapter(NewsActivity.this, R.layout.new_view, strings);
            listView.setAdapter(newsAdapter);
            progressDialog.dismiss();
        }
    }
}
