package com.example.tristan.android_projects;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    public static final String TRY_AGAIN_KEY = "TRYAGAIN";

    private ArrayList<Integer> userAnswers = new ArrayList<>();
    private ArrayList<Question> correctAnswers = new ArrayList<>();
    private int numberOfCorrectAnswers = 0;
    private String URL;

    TextView percentageText, statsMessageText;
    ProgressBar progressBar;
    Button tryAgainButton, quitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        URL = "http://dev.theappsdr.com/apis/trivia_json/index.php";
        percentageText = findViewById(R.id.statsPercentage);
        statsMessageText = findViewById(R.id.statsMessageText);
        progressBar = findViewById(R.id.progressBar);
        tryAgainButton = findViewById(R.id.statsTryAgainButton);
        quitButton = findViewById(R.id.statsQuitButton);

        userAnswers = getIntent().getExtras().getIntegerArrayList(QuestionActivity.STATS_USERS_ANSWER_KEY);
        correctAnswers = getIntent().getExtras().getParcelableArrayList(QuestionActivity.STATS_QUESTIONS_ANSWERS_KEY);

        Log.d("demo", userAnswers.toString());
        Log.d("demo", correctAnswers.toString());

        for (int i = 0; i < userAnswers.size(); i++) {
            Log.d("demo", "Answer: " + userAnswers.get(i).toString());
            Log.d("demo", "Correct Answer: " + correctAnswers.get(i).answer);
            if (userAnswers.get(i) == Integer.parseInt(correctAnswers.get(i).answer)) {
                numberOfCorrectAnswers++;
                Log.d("demo", "Number of Correct Answers: " + numberOfCorrectAnswers);
            }
        }

        numberOfCorrectAnswers = (int)(((double) numberOfCorrectAnswers / 16) * 100);

        if (numberOfCorrectAnswers != 100) {
            statsMessageText.setText("Congrats you got all the questions correct!");
        }

        percentageText.setText(numberOfCorrectAnswers + "%");
        progressBar.setMax(100);
        progressBar.setProgress(numberOfCorrectAnswers);

        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetDataAsync(StatsActivity.this).execute(URL);
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
