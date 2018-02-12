package com.example.tristan.android_projects;

import android.os.AsyncTask;

import java.util.LinkedList;

/**
 * Created by Tristan on 2/12/18.
 */

public class GetTweetsAsyncTask extends AsyncTask<String, Void, LinkedList<String>> {

    IData iData;

    public GetTweetsAsyncTask(IData idata) { this.iData = idata; }

    @Override
    protected LinkedList<String> doInBackground(String... strings) {
        LinkedList<String> tweets = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            tweets.add("Tweet " + i);
        }

        return tweets;
    }

    @Override
    protected void onPostExecute(LinkedList<String> strings) {
        iData.HandleData(strings);
    }

    public static interface IData {
        public void HandleData(LinkedList<String> handleListData);
    }
}
