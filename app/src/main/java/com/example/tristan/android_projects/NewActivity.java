package com.example.tristan.android_projects;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewActivity extends AppCompatActivity {

    public static String NEW_CONTACT_KEY = "CONTACT";
    public static String EDITED_CONTACT_KEY = "EDITED";

    Calendar myCalendar = Calendar.getInstance();

    private EditText editTextFirst, editTextLast, editTextCompany, editTextPhone, editTextURL,
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

                     When user clicks birthday

        ================================================= */
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCalendar.set(Calendar.YEAR, i);
                myCalendar.set(Calendar.MONTH, i1);
                myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                updateLabel();
            }
        };

        editTextBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(NewActivity.this, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        /* =================================================

                        When user is editing

        ================================================= */
        if (getIntent().getExtras().containsKey(DisplayActivity.EDIT_CONTACT_KEY)) {
            Contact contact = (Contact) getIntent().getExtras().get(DisplayActivity.EDIT_CONTACT_KEY);
            editTextFirst.setText(contact.user_firstName);
            editTextLast.setText(contact.user_lastName);
            editTextCompany.setText(contact.user_company);
            editTextPhone.setText(contact.user_phone);
            editTextURL.setText(contact.user_url);
            editTextEmail.setText(contact.user_email);
            editTextAddress.setText(contact.user_address);
            editTextBirthday.setText(contact.user_birthday);
            editTextNickname.setText(contact.user_nickname);
            editTextFacebook.setText(contact.user_facebook);
            editTextTwitter.setText(contact.user_twitter);
            editTextSkype.setText(contact.user_skype);
            editTextYoutube.setText(contact.user_youtube);

            findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Contact editedContact = checkValidation();

                   if (editedContact != null) {
                       Intent intent = new Intent();
                       intent.putExtra(EDITED_CONTACT_KEY, editedContact);
                       setResult(RESULT_OK, intent);
                       finish();
                   }
                }
            });
        }
        /* =================================================

                        When user clicks save

        ================================================= */
        else if (getIntent().getExtras().containsKey(MainActivity.NEW_CONTACT_KEY)){
            findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Contact contact = checkValidation();
                    if (contact != null) {
                        Intent intent = new Intent(NewActivity.this, DisplayActivity.class);
                        intent.putExtra(NEW_CONTACT_KEY, contact);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);

        editTextBirthday.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    private Contact checkValidation() {
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
                    editTextCompany.getText().toString(), editTextPhone.getText().toString(), editTextURL.getText().toString(),
                    editTextEmail.getText().toString(), editTextAddress.getText().toString(), editTextBirthday.getText().toString(),
                    editTextNickname.getText().toString(), editTextFacebook.getText().toString(), editTextTwitter.getText().toString(),
                    editTextSkype.getText().toString(), editTextYoutube.getText().toString());
             return contact;
        }
        return null;
    }
}
