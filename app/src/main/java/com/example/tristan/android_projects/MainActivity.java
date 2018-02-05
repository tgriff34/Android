package com.example.tristan.android_projects;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyTask().execute(100000000);
            }
        });
    }


    private class MyTask extends AsyncTask<Integer, Integer, Integer> {

        private ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Doing Work. Please Wait ...");
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            int count = integers[0];
            for (int i = 0; i < 100; i++){
                for (int j = 0; j < count; j++) {
                }
                publishProgress(i+1);
            }
            return count * 100;
        }
    }
}
