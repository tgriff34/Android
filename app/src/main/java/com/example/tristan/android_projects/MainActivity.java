package com.example.tristan.Android_Projects;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    static String NAME_KEY = "NAME";
    static String USER_KEY = "USER";
    static String PERSON_KEY = "PERSON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Activity 2?")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("DEMO", "Clicked ok");
                        Intent intent = new Intent("com.example.tristan.Android_Projects.intent.action.VIEW");
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.putExtra(PERSON_KEY, new Person("Bob Smith", "Charlotte", 20));
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("DEMO", "Clicked No");
                    }
                });

        final AlertDialog alert = builder.create();


        findViewById(R.id.toSecond).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alert.show();

//                Intent i = new Intent("com.example.tristan.module3practice.intent.action.VIEW");
//                i.addCategory(Intent.CATEGORY_DEFAULT);
//
//                User user = new User("Bob Smith", 20);
//                i.putExtra(USER_KEY, user);
//
//                i.putExtra(PERSON_KEY, new Person("Bob Smith", "Charlotte", 20));
//
//                startActivity(i);
            }
        });
    }
}
