package com.example.tristan.android_projects;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    public static final int EDIT_CONTACT_CODE = 0;
    private static final String CONTACT_LIST_KEY = "CONTACT_LIST";
    public static final String EDIT_CONTACT_KEY = "EDIT_CONTACT";
    public static final String VIEW_CONTACT_KEY = "VIEW_CONTACT";

    private static ArrayList<Contact> Contacts = new ArrayList<>();
    ContactAdapter adapter;
    ListView listView;

    int editContactLocation, deleteContactLocation;
    Object contactToDelete;

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

        //          USER WANTS TO CREATE NEW CONTACT
        if (getIntent().getExtras().containsKey(NewActivity.NEW_CONTACT_KEY) && getIntent() != null) {
            Contact contact = (Contact) getIntent().getExtras().getParcelable(NewActivity.NEW_CONTACT_KEY);
            Log.d("demo", contact.toString());
            Contacts.add(contact);
            Log.d("demo", "Contacts: " + Contacts.isEmpty());
            onClick();
        }
        //          USER WANTS TO REMOVE CONTACT
        else if (getIntent().getExtras().containsKey(MainActivity.DELETE_CONTACT_KEY) && getIntent() != null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(DisplayActivity.this);

            builder.setTitle("Delete Contact")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d("demo", "User deleting contact");
                            deleteContact(contactToDelete);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d("demo", "User did not delete any contact");
                        }
                    });

            final AlertDialog alertDialog = builder.create();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    contactToDelete = listView.getItemAtPosition(i);
                    alertDialog.show();
                }
            });
        }
        //          USER WANTS TO EDIT CONTACTS
        else if (getIntent().getExtras().containsKey(MainActivity.EDIT_CONTACT_KEY) && getIntent() != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Contact contact = Contacts.get(i);
                    editContactLocation = i;
                    Intent intent = new Intent(DisplayActivity.this, NewActivity.class);
                    intent.putExtra(EDIT_CONTACT_KEY, contact);
                    startActivityForResult(intent, EDIT_CONTACT_CODE);
                }
            });
        }
        //          USER WANTS TO BROWSE CONTACTS
        else if (getIntent().getExtras().containsKey(MainActivity.VIEW_CONTACTS_KEY) && getIntent() != null) {
            onClick();
        }

        Log.d("demo", "Contacts: " + Contacts.toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(CONTACT_LIST_KEY, Contacts);
    }

    //          When the user wants to view a particular contact
    private void onClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact contact = Contacts.get(i);
                Log.d("demo", contact.toString());
                Intent intent = new Intent(DisplayActivity.this, ViewActivity.class);
                intent.putExtra(VIEW_CONTACT_KEY, contact);
                startActivity(intent);
            }
        });
    }

    //          When the user want to delete a contact
    private void deleteContact(Object object) {
        Contacts.remove(object);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_CONTACT_CODE){
            Log.d("demo", "Editing contact ...");
            Contact editedContact = (Contact) data.getExtras().get(NewActivity.EDITED_CONTACT_KEY);
            Contacts.set(editContactLocation, editedContact);
            adapter.notifyDataSetChanged();
        }
    }
}
