package com.example.tristan.Android_Projects;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        if (getIntent() != null && getIntent().getExtras() != null) {
            //User user = (User) getIntent().getExtras().getSerializable(MainActivity.USER_KEY);
            //Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show();

            Person person = getIntent().getExtras().getParcelable(MainActivity.PERSON_KEY);
            Toast.makeText(this, person.toString(), Toast.LENGTH_SHORT).show();
        }

        findViewById(R.id.doneButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
