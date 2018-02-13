/*
Name: Tristan Griffin
Group: 16
Date: Feb 12
 */
package com.example.tristan.android_projects;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> imageURLs;
    ImageView imageView;
    EditText editText;
    RequestParams params;
    int imageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        imageView = (ImageView) findViewById(R.id.imageView);
        params = new RequestParams();

        findViewById(R.id.goButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (isConnected()) {
                        imageNumber = 0;
                        params.addParam("keyword", editText.getText().toString());
                        Log.d("demo", params.getEncodedParams());
                        new GetImageAsync(params).execute("http://dev.theappsdr.com/apis/photos/index.php");
                    } else {
                        Toast.makeText(MainActivity.this, "Not Connect to Internet", Toast.LENGTH_SHORT).show();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageNumber++;
                if (imageNumber > imageURLs.size() - 1) {
                    imageNumber = 0;
                }
                Log.d("demo", "Downloading ... " + imageURLs.get(imageNumber));
                new DownloadImage(imageView).execute(imageURLs.get(imageNumber));
            }
        });

        findViewById(R.id.prevButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageNumber--;
                if (imageNumber < 0) {
                    imageNumber = imageURLs.size() - 1;
                }

                Log.d("demo", "Downloading ... " + imageURLs.get(imageNumber));
                new DownloadImage(imageView).execute(imageURLs.get(imageNumber));
            }
        });

    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private class GetImageAsync extends AsyncTask<String, Void, Void> {

        RequestParams mParams;
        private ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        public GetImageAsync(RequestParams params) {
            this.mParams = params;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading Images ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                URL url = new URL(mParams.getEncodedURL(strings[0]));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                imageURLs = new ArrayList<>();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";

                while((line = bufferedReader.readLine()) != null) {
                    imageURLs.add(line);
                }

                for (int i = 0; i < imageURLs.size(); i++) {
                    Log.d("demo", "Number: " + i + " URL: " + imageURLs.get(i));
                }

                bufferedReader.close();
                connection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            new DownloadImage(imageView).execute(imageURLs.get(0));
            progressDialog.dismiss();
        }
    }

    private class DownloadImage extends AsyncTask<String, Void, Void> {
        ImageView imageView;
        Bitmap bitmap;
        private ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        public DownloadImage(ImageView iv) {
            this.imageView = iv;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading Photo ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
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
            progressDialog.dismiss();
        }
    }
}
