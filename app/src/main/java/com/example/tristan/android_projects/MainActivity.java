package com.example.tristan.android_projects;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements GetTweetsAsyncTask.IData {

    LinkedList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetTweetsAsyncTask(MainActivity.this).execute();
    }

//    public void handleData(LinkedList<String> data) {
//        this.data = data;
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Tweets");
//        builder.setItems(data.toArray(new CharSequence[data.size()]), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//
//        final AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }

    @Override
    public void HandleData(LinkedList<String> handleListData) {
        this.data = handleListData;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tweets")
                .setItems(data.toArray(new CharSequence[data.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
