package com.example.tristan.android_projects;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class displayActivity extends AppCompatActivity {

    TextView textView;
    ArrayList<Contact> Contacts = new ArrayList<Contact>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        textView = findViewById(R.id.textViewContact);

        if (getIntent() != null) {
            Contact contact = (Contact) getIntent().getExtras().getSerializable(newActivity.CONTACT_KEY);

            Contacts.add(contact);

            textView.setText(contact.toString());

        }
    }
}
