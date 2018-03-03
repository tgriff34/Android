package com.example.tristan.android_projects;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
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

/**
 * Created by Tristan on 3/2/18.
 */

public class GetDataAsync extends AsyncTask<String, Void, ArrayList<Question>> {

    ArrayList<Question> questions;
    String id, text, image, choices[], answer;

    @Override
    protected ArrayList<Question> doInBackground(String... strings) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            questions = new ArrayList<>();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String line = IOUtils.toString(connection.getInputStream(), "UTF8");
                Log.d("demo", line);
                JSONObject jsonObject = new JSONObject(line);
                JSONArray questionArray = jsonObject.getJSONArray("questions");

                for (int i = 0; i < questionArray.length(); i++) {
                    JSONObject object = questionArray.getJSONObject(i);
                    id = object.getString("id");
                    text = object.getString("text");
                    image = object.getString("image");


                    JSONObject choicesObject = object.getJSONObject("choices");
                    JSONArray choicesArray = choicesObject.getJSONArray("choice");

                    for (int j = 0; j < choicesArray.length(); j++) {
                        choices[j] = (String) choicesArray.get(j);
                    }

                    answer = choicesObject.getString("answer");

                    Question question = new Question(id, text, image, choices, answer);
                    questions.add(question);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    protected void onPostExecute (ArrayList<Question> questions) {
        Log.d("demo", questions.toString());
    }
}
