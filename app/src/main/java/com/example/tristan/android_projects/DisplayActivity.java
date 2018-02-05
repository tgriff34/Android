package com.example.tristan.android_projects;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        if (getIntent() != null) {
            User user = (User) getIntent().getExtras().getSerializable(MainActivity.USER_KEY);

            TextView userInfoView = (TextView) findViewById(R.id.displayUserView);

            ImageView profileImageView = (ImageView) findViewById(R.id.displayAvatarImage);
            ImageView moodImageView = (ImageView) findViewById(R.id.displayMoodImage);
            profileImageView.setImageResource(MainActivity.avatarInt);
            moodImageView.setImageResource(MainActivity.moodInt);


            userInfoView.setText(user.toString());

        }
    }
}
