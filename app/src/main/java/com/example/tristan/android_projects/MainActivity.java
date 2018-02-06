package com.example.tristan.android_projects;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
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


        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Generating Passwords ...");
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);



        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case DoWork.STATUS_START:
                        progressDialog.setProgress(0);
                        currentPassIndex = 0;
                        progressDialog.show();
                        Log.d("demo", "Thread starting");
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
                                textView.setText("Password: " + finalPassword);
                            }
                        });
                        builder.show();
                        break;
                }
                return false;
            }
        });




        countSeekBar = findViewById(R.id.seekBar);
        lengthSeekBar = findViewById(R.id.seekBar2);
        countSeekBar.setProgress(0);
        lengthSeekBar.setProgress(0);
        countSeekBar.setMax(10);
        lengthSeekBar.setMax(23);
        countSeekBar.setMin(1);
        lengthSeekBar.setMin(8);
        final TextView textView = findViewById(R.id.passwordCountView);
        final TextView textView1 = findViewById(R.id.passwordLengthView);
        textView.setText("Select password count: " + passwordCount);
        textView1.setText("Select password length: " + passwordLength);
        charSequences = new CharSequence[passwordCount];

        countSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                passwordCount = i;
                charSequences = new CharSequence[i];
                textView.setText("Select password count: " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lengthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                passwordLength = i;
                textView1.setText("Select password length: " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        myThreadPool = Executors.newFixedThreadPool(1);

        findViewById(R.id.threadButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myThreadPool.execute(new DoWork());
            }
        });
    }

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

                Log.d("demo", password);
                Log.d("demo", "For loop count: " + i);

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
