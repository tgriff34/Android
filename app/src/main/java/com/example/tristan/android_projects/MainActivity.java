/*
Name: Tristan Griffin
Group: 27
Class: 4180
 */

package com.example.tristan.android_projects;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int AVATAR_REQ = 1;
    static String USER_KEY = "USER";
    public static int avatarInt, moodInt;

    private String mood;

    RadioGroup radioGroup;
    int radioButtonID;
    RadioButton rb;
    String selectedRadioButtonText;

    TextView name, email;

    SeekBar seekBar;
    TextView moodTextView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (TextView) findViewById(R.id.nameText);
        email = (TextView) findViewById(R.id.emailText);



        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                radioButtonID = radioGroup.getCheckedRadioButtonId();
                rb = (RadioButton) findViewById(radioButtonID);
                selectedRadioButtonText = (String) rb.getText().toString();

                User user = new User(name.getText().toString(), email.getText().toString(), selectedRadioButtonText, mood);
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra(USER_KEY, user);
                startActivity(intent);
            }
        });

        findViewById(R.id.avatarButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivityForResult(intent, AVATAR_REQ);
            }
        });

        moodTextView = (TextView) findViewById(R.id.moodTextView);
        imageView = (ImageView) findViewById(R.id.imageView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setProgress(0);
        seekBar.setMax(3);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i == 0) {
                    mood = "Angry";
                    moodTextView.setText("Your current mood is: " + mood);
                    moodInt = R.drawable.angry;
                    imageView.setImageResource(moodInt);
                }
                else if (i == 1) {
                    mood = "Sad";
                    moodTextView.setText("Your current mood is: " + mood);
                    moodInt = R.drawable.sad;
                    imageView.setImageResource(moodInt);
                }
                else if (i == 2) {
                    mood = "Happy";
                    moodTextView.setText("Your current mood is: " + mood);
                    moodInt = R.drawable.happy;
                    imageView.setImageResource(moodInt);
                } else {
                    mood = "Awesome";
                    moodTextView.setText("Your current mood is: " + mood);
                    moodInt = R.drawable.awesome;
                    imageView.setImageResource(moodInt);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AVATAR_REQ) {
            Log.d("Demo", "Test");

            avatarInt = data.getExtras().getInt(ProfileActivity.PICTURE_KEY);
            ImageButton avatarButton = findViewById(R.id.avatarButton);

            avatarButton.setImageResource(avatarInt);
        }
    }
}
