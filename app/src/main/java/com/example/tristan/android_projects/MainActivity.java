package com.example.tristan.android_projects;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String VIEW_CONTACTS_KEY = "VIEW";
    public static final String DELETE_CONTACT_KEY = "DELELTE";

    public static final int VIEW_CONTACTS = 0;
    public static final int DELETE_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
                    Create new Contact
         */
        findViewById(R.id.newButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                startActivity(intent);
            }
        });

        /*
                    Delete contact
         */
        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra(DELETE_CONTACT_KEY, DELETE_CONTACT);
                startActivity(intent);
            }
        });

        /*
                    Display contact list
         */
        findViewById(R.id.viewButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra(VIEW_CONTACTS_KEY, VIEW_CONTACTS);
                startActivity(intent);
            }
        });

        /*
                    Kills app
         */
        findViewById(R.id.finishButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
