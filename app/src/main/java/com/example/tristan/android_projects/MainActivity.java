package com.example.tristan.android_projects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetDataAsync().execute("http://api.theappsdr.com/simple.php");
        new GetImageAsync((ImageView) findViewById(R.id.imageView2)).execute("https://upload.wikimedia.org/wikipedia/commons/6/66/Android_robot.png");

        RequestParams params = new RequestParams();
        try {
            params.addParam("name", "Bob smith")
                    .addParam("age", "22")
                    .addParam("email", "bob.gmail.com")
                    .addParam("password", "asdfasdfaf");

            new GetDataGetParamsAsync(params).execute("http://api.theappsdr.com/params.php");
            new GetDataPostParamsAsync(params).execute("http://api.theappsdr.com/params.php");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo.isConnected() && networkInfo != null) {
            return true;
        }
        return false;
    }

    private class GetImageAsync extends AsyncTask<String, Void, Void> {

        ImageView imageView;
        Bitmap bitmap;

        public GetImageAsync(ImageView iv) {
            this.imageView = iv;
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                bitmap = BitmapFactory.decodeStream(connection.getInputStream());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            imageView.setImageBitmap(bitmap);
        }
    }

    private class GetDataAsync extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                bufferedReader.close();
                connection.disconnect();

                return stringBuilder.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            if (s != null) {
                Log.d("demo", s);
            } else {
                Log.d("demo", "No result");
            }

        }
    }

    private class GetDataGetParamsAsync extends AsyncTask<String, Void, String> {

        RequestParams mParams;

        public GetDataGetParamsAsync(RequestParams params) {
            this.mParams = params;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(mParams.getEncodedURL(strings[0]));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                bufferedReader.close();
                connection.disconnect();

                return stringBuilder.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                Log.d("demo", s);
            } else {
                Log.d("demo", "nothing here ...");
            }
        }
    }

    private class GetDataPostParamsAsync extends AsyncTask<String, Void, String> {

        RequestParams mParams;

        public GetDataPostParamsAsync(RequestParams params) {
            this.mParams = params;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(mParams.getEncodedURL(strings[0]));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                mParams.encodePostParams(connection);
                connection.connect();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                bufferedReader.close();
                connection.disconnect();

                return stringBuilder.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                Log.d("demo", s);
            } else {
                Log.d("demo", "nothing here ...");
            }
        }
    }


}
