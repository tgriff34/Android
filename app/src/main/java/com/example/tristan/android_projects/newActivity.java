package com.example.tristan.android_projects;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class newActivity extends AppCompatActivity {

    EditText editTextFirst, editTextLast, editTextCompany, editTextPhone, editTextURL,
        editTextEmail, editTextAddress, editTextBirthday, editTextNickname, editTextFacebook,
        editTextTwitter, editTextSkype, editTextYoutube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editTextFirst.getText())) {
                    editTextFirst.setError("First name is required.");
                    Toast.makeText(newActivity.this, "A first name is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(editTextLast.getText())) {
                    editTextLast.setError("Last name is required.");
                    Toast.makeText(newActivity.this, "A last name is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(editTextPhone.getText())) {
                    editTextPhone.setError("A phone number is required.");
                    Toast.makeText(newActivity.this, "A phone number is required", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(newActivity.this, displayActivity.class);
                    startActivity(intent);

                }
            }
        });

    }
}
