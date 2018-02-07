package com.example.tristan.android_projects;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewActivity extends AppCompatActivity {

    public static String NEW_CONTACT_KEY = "CONTACT";

    EditText editTextFirst, editTextLast, editTextCompany, editTextPhone, editTextURL,
            editTextEmail, editTextAddress, editTextBirthday, editTextNickname, editTextFacebook,
            editTextTwitter, editTextSkype, editTextYoutube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        /* Variables Assignments */
        editTextFirst = findViewById(R.id.editFirstName);
        editTextLast = findViewById(R.id.editLastName);
        editTextCompany = findViewById(R.id.editCompany);
        editTextPhone = findViewById(R.id.editPhone);
        editTextURL = findViewById(R.id.editURL);
        editTextEmail = findViewById(R.id.editEmail);
        editTextAddress = findViewById(R.id.editAddress);
        editTextBirthday = findViewById(R.id.editBirthday);
        editTextNickname = findViewById(R.id.editNickname);
        editTextFacebook = findViewById(R.id.editFacebook);
        editTextTwitter = findViewById(R.id.editTwitter);
        editTextSkype = findViewById(R.id.editSkype);
        editTextYoutube = findViewById(R.id.editYoutube);
        /* ===================== */


        /* =================================================

                        When user clicks save

        ================================================= */

        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editTextFirst.getText())) {
                    editTextFirst.setError("First name is required.");
                    Toast.makeText(NewActivity.this, "A first name is required", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(editTextLast.getText())) {
                    editTextLast.setError("Last name is required.");
                    Toast.makeText(NewActivity.this, "A last name is required", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(editTextPhone.getText())) {
                    editTextPhone.setError("A phone number is required.");
                    Toast.makeText(NewActivity.this, "A phone number is required", Toast.LENGTH_SHORT).show();
                }
                if (!TextUtils.isEmpty(editTextFirst.getText()) && !TextUtils.isEmpty(editTextLast.getText()) && !TextUtils.isEmpty(editTextPhone.getText())) {
                    Contact contact = new Contact(editTextFirst.getText().toString(), editTextLast.getText().toString(),
                            editTextCompany.getText().toString(), editTextPhone.getText().toString(), editTextEmail.getText().toString(),
                            editTextAddress.getText().toString(), editTextBirthday.getText().toString(), editTextNickname.getText().toString(),
                            editTextFacebook.getText().toString(), editTextTwitter.getText().toString(), editTextSkype.getText().toString(),
                            editTextYoutube.getText().toString());
                    Intent intent = new Intent(NewActivity.this, DisplayActivity.class);
                    intent.putExtra(NEW_CONTACT_KEY, contact);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
