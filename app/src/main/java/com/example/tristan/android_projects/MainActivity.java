/*
    Tristan Griffin
    Group: 27
    Feb 5 2018
    In class assignment: 03
 */

package com.example.tristan.android_projects;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ExecutorService myThreadPool;
    Handler handler;
    ProgressDialog progressDialog;
    SeekBar lengthSeekBar, countSeekBar;
    CharSequence[] charSequences;
    AlertDialog.Builder builder;
    int passwordCount = 1, passwordLength = 8, currentPassIndex = 0;
    String finalPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Progress dialog variable set
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Generating Passwords ...");
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);



        //For thread pool
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case DoWork.STATUS_START:
                        progressDialog.setProgress(0);
                        currentPassIndex = 0;
                        progressDialog.show();
                        Log.d("demo", "Invoking Thread Handler ...");
                        break;
                    case DoWork.STATUS_PROGRESS:

                        progressDialog.setProgress(100 / passwordCount * currentPassIndex);
                        Log.d("demo", "Number: " + currentPassIndex);
                        Log.d("demo", "Password: " + message.getData().getString(DoWork.BUNDLE_STATUS_PROGRESS));
                        charSequences[currentPassIndex] = message.getData().getString(DoWork.BUNDLE_STATUS_PROGRESS);

                        currentPassIndex++;

                        break;

                    case DoWork.STATUS_END:
                        progressDialog.dismiss();
                        builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Choose a Password");
                        builder.setItems(charSequences, new DialogInterface.OnClickListener() {
                        @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finalPassword = charSequences[i].toString();
                                TextView textView = findViewById(R.id.passwordView);
                                textView.setText(getString(R.string.password_view) + finalPassword);
                            }
                        });
                        builder.show();
                        break;
                }
                return false;
            }
        });




        //Seekbar variables
        countSeekBar = findViewById(R.id.seekBar);
        lengthSeekBar = findViewById(R.id.seekBar2);
        countSeekBar.setProgress(0);
        lengthSeekBar.setProgress(0);
        final TextView textView = findViewById(R.id.passwordCountView);
        final TextView textView1 = findViewById(R.id.passwordLengthView);
        textView.setText(getString(R.string.password_count_view) + passwordCount);
        textView1.setText(getString(R.string.password_length_view) + passwordLength);
        charSequences = new CharSequence[passwordCount];

        //When the user updates the amount of passwords, update the var value
        //and set the charSequence array size to fit the amount of passwords
        countSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                passwordCount = 1+ i;
                charSequences = new CharSequence[passwordCount];
                textView.setText(getString(R.string.password_count_view) + passwordCount);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //When the user changes the password length using the seekbar, update var value
        lengthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                passwordLength = 8+ i;
                textView1.setText(getString(R.string.password_length_view) + passwordLength);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        //Generate Passwords using thread pool
        myThreadPool = Executors.newFixedThreadPool(1);

        findViewById(R.id.threadButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myThreadPool.execute(new DoWork());
            }
        });

        //Generate Passwords using asynctask
        findViewById(R.id.asyncButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyTask().execute(passwordCount);
            }
        });

    }

    //Using AsyncTask
    private class MyTask extends AsyncTask<Integer, Integer, Void> {

        Util util = new Util();

        @Override
        protected void onPreExecute() {
            Log.d("demo", "Invoking AsyncTask ...");
            progressDialog.setProgress(0);
            progressDialog.show();
            currentPassIndex = 0;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Choose a Password");
            builder.setItems(charSequences, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finalPassword = charSequences[i].toString();
                    TextView textView = findViewById(R.id.passwordView);
                    textView.setText(getString(R.string.password_view) + finalPassword);
                }
            });
            builder.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d("demo", "Progress: " + values[0]);
            progressDialog.setProgress(values[0]);

        }

        @Override
        protected Void doInBackground(Integer... integers) {
            int count = integers[0];

            for (int i = 0; i < count; i++) {
                charSequences[currentPassIndex] = util.getPassword(passwordLength);
                Log.d("demo", "Number: " + currentPassIndex);
                Log.d("demo", "Password: " + charSequences[currentPassIndex]);
                publishProgress(100 / count * currentPassIndex);
                currentPassIndex++;
            }
            return null;
        }
    }

    //Using Thread Pool
    class DoWork implements Runnable {

        static final int STATUS_START = 0x00;
        static final int STATUS_PROGRESS = 0x01;
        static final int STATUS_END = 0x02;
        static final String BUNDLE_STATUS_PROGRESS = "PROGRESS";
        String password;

        @Override
        public void run() {

            Util util = new Util();
            Message startMessage = new Message();
            startMessage.what = STATUS_START;
            handler.sendMessage(startMessage);

            for (int i = 0; i < passwordCount; i++) {

                Message progressMessage = new Message();
                progressMessage.what = STATUS_PROGRESS;

                password = util.getPassword(passwordLength);

                Bundle bundle = new Bundle();
                bundle.putString(BUNDLE_STATUS_PROGRESS, password);
                progressMessage.setData(bundle);

                handler.sendMessage(progressMessage);
            }

            Message endMessage = new Message();
            endMessage.what = STATUS_END;
            handler.sendMessage(endMessage);
        }
    }
}
