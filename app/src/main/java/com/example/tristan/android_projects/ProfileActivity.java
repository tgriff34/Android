package com.example.tristan.android_projects;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ProfileActivity extends AppCompatActivity {

    public static String PICTURE_KEY = "PICTURE";
    int avatarValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViewById(R.id.femaleOneButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avatarValue = R.drawable.avatar_f_1;

                Intent intent = new Intent();
                intent.putExtra(PICTURE_KEY, avatarValue);
                setResult(RESULT_OK, intent);

                finish();
            }
        });

        findViewById(R.id.femaleTwoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avatarValue = R.drawable.avatar_f_2;

                Intent intent = new Intent();
                intent.putExtra(PICTURE_KEY, avatarValue);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
        findViewById(R.id.femaleThreeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avatarValue = R.drawable.avatar_f_3;

                Intent intent = new Intent();
                intent.putExtra(PICTURE_KEY, avatarValue);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
        findViewById(R.id.maleOneButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avatarValue = R.drawable.avatar_m_1;

                Intent intent = new Intent();
                intent.putExtra(PICTURE_KEY, avatarValue);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
        findViewById(R.id.maleTwoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avatarValue = R.drawable.avatar_m_2;

                Intent intent = new Intent();
                intent.putExtra(PICTURE_KEY, avatarValue);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
        findViewById(R.id.maleThreeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avatarValue = R.drawable.avatar_m_3;

                Intent intent = new Intent();
                intent.putExtra(PICTURE_KEY, avatarValue);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }
}
