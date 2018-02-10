package com.example.tristan.android_projects;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String VIEW_CONTACTS_KEY = "VIEW";
    public static final String DELETE_CONTACT_KEY = "DELELTE";
    public static final String EDIT_CONTACT_KEY = "EDIT";
    public static final String NEW_CONTACT_KEY = "NEW";

    public static final int VIEW_CONTACTS = 0;
    public static final int DELETE_CONTACT = 1;
    public static final int EDIT_CONTACT = 2;
    public static final int NEW_CONTACT = 3;

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
//                Intent intent = new Intent(MainActivity.this, NewActivity.class);
//                intent.putExtra(NEW_CONTACT_KEY, NEW_CONTACT);
//                startActivity(intent);
                Thread thread = new Thread(new StartIntent(NEW_CONTACT_KEY), NEW_CONTACT_KEY);
                thread.start();
            }
        });

        /*
                    Delete contact
         */
        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
//                intent.putExtra(DELETE_CONTACT_KEY, DELETE_CONTACT);
//                startActivity(intent);
                Thread thread = new Thread(new StartIntent(DELETE_CONTACT_KEY), DELETE_CONTACT_KEY);
                thread.start();
            }
        });

        /*
                    Show contact list for editing
         */
        findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
//                intent.putExtra(EDIT_CONTACT_KEY, EDIT_CONTACT);
//                startActivity(intent);
                Thread thread = new Thread(new StartIntent(EDIT_CONTACT_KEY), EDIT_CONTACT_KEY);
                thread.start();
            }
        });

        /*
                    Display contact list
         */
        findViewById(R.id.viewButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
//                intent.putExtra(VIEW_CONTACTS_KEY, VIEW_CONTACTS);
//                startActivity(intent);
                Thread thread = new Thread(new StartIntent(VIEW_CONTACTS_KEY), VIEW_CONTACTS_KEY);
                thread.start();
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

    private class StartIntent implements Runnable {

        private String key;

        public StartIntent(String _key) {
            this.key = _key;
        }
        @Override
        public void run() {
            if (key == NEW_CONTACT_KEY) {
                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                intent.putExtra(NEW_CONTACT_KEY, NEW_CONTACT);
                startActivity(intent);
            }
            else if (key == EDIT_CONTACT_KEY) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra(EDIT_CONTACT_KEY, EDIT_CONTACT);
                startActivity(intent);
            }
            else if (key == VIEW_CONTACTS_KEY) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra(VIEW_CONTACTS_KEY, VIEW_CONTACTS);
                startActivity(intent);
            }
            else if (key == DELETE_CONTACT_KEY) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra(DELETE_CONTACT_KEY, DELETE_CONTACT);
                startActivity(intent);
            }
        }
    }
}
