package com.example.tristan.android_projects;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Tristan on 3/2/18.
 */

public class GetDataAsync extends AsyncTask<String, Void, ArrayList<Question>> {

    public final static String QUESTION_KEY = "QUESTION";

    ArrayList<Question> questions;
    String id, text, image, answer;
    ArrayList<String> choices;
    ProgressDialog progressDialog;
    Context mContext;
    Intent intent;

    public GetDataAsync(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Generating Quiz ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        intent = new Intent(mContext, QuestionActivity.class);
    }

    @Override
    protected ArrayList<Question> doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();

        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            questions = new ArrayList<>();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String result = "";

                while ((result = reader.readLine()) != null) {
                    builder.append(result);
                }
                result = builder.toString();

                JSONObject object = new JSONObject(result);
                JSONArray questionArray = object.getJSONArray("questions");

                for (int i = 0; i < questionArray.length(); i++) {

                    JSONObject questionObject = questionArray.getJSONObject(i);
                    id = questionObject.getString("id");
                    text = questionObject.getString("text");
                    if (questionObject.has("image")) {
                        image = questionObject.getString("image");
                    }

                    JSONObject choicesObject = questionObject.getJSONObject("choices");
                    JSONArray choicesArray = choicesObject.getJSONArray("choice");
                    choices = new ArrayList<>();
                    for (int j = 0; j < choicesArray.length(); j++) {
                        choices.add(choicesArray.getString(j));
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
    protected void onPostExecute(ArrayList<Question> questions) {
        Log.d("demo", questions.toString());
        intent.putExtra(QUESTION_KEY, questions);
        progressDialog.dismiss();
        mContext.startActivity(intent);
        ((Activity)mContext).finish();
    }
}
