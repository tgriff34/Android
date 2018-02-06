package com.example.tristan.android_projects;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class displayActivity extends AppCompatActivity {

    ArrayList<Contact> Contacts = new ArrayList<Contact>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


        if (getIntent() != null) {
            Contact contact = (Contact) getIntent().getExtras().getSerializable(newActivity.CONTACT_KEY);

            Contacts.add(contact);
        }

        ContactAdapter adapter = new ContactAdapter(this, Contacts);
        ListView listView = (ListView) findViewById(R.id.lvContacts);
        listView.setAdapter(adapter);
    }
}
