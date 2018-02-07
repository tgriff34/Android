package com.example.tristan.android_projects;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {

    TextView firstNameView, lastNameView, companyView, phoneView, emailView, urlView, addressView,
        birthdayView, nicknameView, facebookView, twitterView, skypeView, youtubeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        firstNameView = findViewById(R.id.firstNameView);
        lastNameView = findViewById(R.id.lastNameView);
        companyView = findViewById(R.id.companyView);
        phoneView = findViewById(R.id.phoneView);
        emailView = findViewById(R.id.emailView);
        urlView = findViewById(R.id.urlView);
        addressView = findViewById(R.id.addressView);
        birthdayView = findViewById(R.id.birthdayView);
        nicknameView = findViewById(R.id.nicknameView);
        facebookView = findViewById(R.id.facebookView);
        twitterView = findViewById(R.id.twitterView);
        skypeView = findViewById(R.id.skypeView);
        youtubeView = findViewById(R.id.youtubeView);

        if (getIntent().getExtras() != null && getIntent() != null) {
           Contact contact = getIntent().getExtras().getParcelable(DisplayActivity.VIEW_CONTACT_KEY);

           firstNameView.setText(getString(R.string.textView_firstName) + contact.user_firstName);
           lastNameView.setText(getString(R.string.textView_lastName) + contact.user_lastName);
           companyView.setText(getString(R.string.textView_company) + contact.user_company);
           phoneView.setText(getString(R.string.textView_phone) + contact.user_phone);
           emailView.setText(getString(R.string.textView_email) + contact.user_email);
           urlView.setText(getString(R.string.textView_url) + contact.user_url);
           addressView.setText(getString(R.string.textView_address) + contact.user_address);
           //ADD IN BIRTHDAY
           nicknameView.setText(getString(R.string.textView_nickname) + contact.user_nickname);
           facebookView.setText(getString(R.string.textView_facebook) + contact.user_facebook);
           twitterView.setText(getString(R.string.textView_twitter) + contact.user_twitter);
           skypeView.setText(getString(R.string.textView_skype) + contact.user_skype);
           youtubeView.setText(getString(R.string.textView_youtube) + contact.user_youtube);
        }
    }
}
