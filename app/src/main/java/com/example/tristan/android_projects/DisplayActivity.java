package com.example.tristan.android_projects;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    private static final String CONTACT_LIST_KEY = "CONTACT_LIST";
    private static final String VIEW_CONTACT_KEY = "VIEW_CONTACT";

    private static ArrayList<Contact> Contacts = new ArrayList<>();
    ContactAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Log.d("demo", "Display: On Create");

        adapter = new ContactAdapter(DisplayActivity.this, Contacts);
        listView = (ListView) findViewById(R.id.lvContacts);
        listView.setAdapter(adapter);
        listView.setClickable(true);

        if (savedInstanceState != null && savedInstanceState.containsKey(CONTACT_LIST_KEY)) {
            Contacts = new ArrayList<>();
            Contacts = savedInstanceState.getParcelableArrayList(CONTACT_LIST_KEY);
        }

        if (getIntent().getExtras().containsKey(NewActivity.NEW_CONTACT_KEY) && getIntent() != null) {
            Contact contact = (Contact) getIntent().getExtras().getParcelable(NewActivity.NEW_CONTACT_KEY);
            Log.d("demo", contact.toString());
            Contacts.add(contact);
            Log.d("demo", "Contacts: " + Contacts.isEmpty());
        }
        else if (getIntent().getExtras().containsKey(MainActivity.DELETE_CONTACT_KEY) && getIntent() != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Object o = listView.getItemAtPosition(i);
                    Contacts.remove(o);
                    adapter.notifyDataSetChanged();
                }
            });
        }
        else if (getIntent().getExtras().containsKey(MainActivity.VIEW_CONTACTS_KEY) && getIntent() != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Object o = listView.getItemAtPosition(i);
                    String string = o.toString();

                    Log.d("demo", string);

                }
            });
        }

        Log.d("demo", "Contacts: " + Contacts.toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(CONTACT_LIST_KEY, Contacts);
    }
}
