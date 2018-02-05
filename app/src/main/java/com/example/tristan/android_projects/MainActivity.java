package com.example.tristan.android_projects;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ExecutorService myThreadPool;
    Handler handler;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Updating Progress");
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case DoWork.STATUS_START:
                        progressDialog.setProgress(0);
                        progressDialog.show();
                        Log.d("demo", "Thread status: Starting");
                        break;

                    case DoWork.STATUS_PROGRESS:
                        //message.obj if using object
                        progressDialog.setProgress(message.getData().getInt(DoWork.BUNDLE_STATUS_PROGRESS));
//                        Log.d("demo", "Thread status: " + message.getData().getInt(DoWork.BUNDLE_STATUS_PROGRESS));
                        break;

                    case DoWork.STATUS_STOP:
                        progressDialog.dismiss();
                        Log.d("demo", "Thread status: Ending");
                        break;
                }
                return false;
            }
        });
        myThreadPool = Executors.newFixedThreadPool(4);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Thread thread = new Thread(new DoWork(), "Worker 1");
//                thread.start();
                myThreadPool.execute(new DoWork());
            }
        });
    }

    class DoWork implements Runnable {

        static final int STATUS_START = 0x00;
        static final int STATUS_PROGRESS = 0x01;
        static final int STATUS_STOP = 0x02;
        static final String BUNDLE_STATUS_PROGRESS = "PROGRESS";

        @Override
        public void run() {

            Message startMessage = new Message();
            startMessage.what = STATUS_START;
            handler.sendMessage(startMessage);

            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100000000; j++) {

                }
                Message progressMessage = new Message();
                progressMessage.what = STATUS_PROGRESS;
                //progressMessage.obj = (Integer)i;
                Bundle bundle = new Bundle();
                bundle.putInt(BUNDLE_STATUS_PROGRESS, (Integer)i);
                progressMessage.setData(bundle);
                handler.sendMessage(progressMessage);
            }
            Message endMessage = new Message();
            endMessage.what = STATUS_STOP;
            handler.sendMessage(endMessage);
        }
    }
}
