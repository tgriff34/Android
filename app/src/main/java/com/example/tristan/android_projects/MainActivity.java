package com.example.tristan.android_projects;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String URL;
    static ArrayList<Question> questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isConnected()) {
            URL = "http://dev.theappsdr.com/apis/trivia_json/index.php";
            findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new GetDataAsync(MainActivity.this, questions).execute(URL);
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

    /*
    public static void setArrayList(ArrayList<Question> questionsArrayList) {
        questions = questionsArrayList;
        if (questions != null) {
            Log.d("demo", questions.toString());
        } else {
            Log.d("demo", "empty");
        }
    }
    */
}
