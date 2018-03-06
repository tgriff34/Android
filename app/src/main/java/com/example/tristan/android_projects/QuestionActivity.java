package com.example.tristan.android_projects;

import android.content.Intent;
import android.media.Image;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    public static final String STATS_USERS_ANSWER_KEY = "STATS";
    public static final String STATS_QUESTIONS_ANSWERS_KEY = "STATS_A";

    private int currentQuestionNumber;

    ArrayList<Question> questions = new ArrayList<>();
    ArrayList<Integer> answers = new ArrayList<>();
    TextView questionNumber;
    TextView timeLeft;
    TextView questionText;
    ImageView questionImage;
    Button nextButton;
    Button quitButton;
    RadioGroup rg;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        currentQuestionNumber = 0;
        questionNumber = (TextView) findViewById(R.id.questionNumberTextView);
        timeLeft = (TextView) findViewById(R.id.timeLeftTextView);
        questionText = (TextView) findViewById(R.id.questionText);
        questionImage = (ImageView) findViewById(R.id.questionImageView);
        nextButton = (Button) findViewById(R.id.nextQuestionButton);
        quitButton = (Button) findViewById(R.id.quitButton);

        if (getIntent().getExtras().containsKey(GetDataAsync.QUESTION_KEY)) {

            questions = getIntent().getParcelableArrayListExtra(GetDataAsync.QUESTION_KEY);

            timer = new CountDownTimer(120000, 1000) {
                @Override
                public void onTick(long l) {
                    timeLeft.setText("Time Left: " + l / 1000);
                }

                @Override
                public void onFinish() {
                    Toast.makeText(QuestionActivity.this, "RAN OUT OF TIME!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(QuestionActivity.this, StatsActivity.class);
                    intent.putExtra(STATS_USERS_ANSWER_KEY, answers);
                    intent.putExtra(STATS_QUESTIONS_ANSWERS_KEY, questions);
                    startActivity(intent);
                }
            }.start();

            questionNumber.setText("Q" + (currentQuestionNumber + 1));
            questionText.setText(questions.get(currentQuestionNumber).text);
            Picasso.with(this).load(questions.get(currentQuestionNumber).image).into(questionImage);
            createChoiceButtons(questions.get(currentQuestionNumber).choices.size());

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    answers.add(currentQuestionNumber, getCheckedAnswer());
                    Log.d("demo", answers.toString());
                    currentQuestionNumber++;
                    questionNumber.setText("Q" + (currentQuestionNumber + 1));
                    questionText.setText(questions.get(currentQuestionNumber).text);
                    Picasso.with(QuestionActivity.this).load(questions.get(currentQuestionNumber).image).into(questionImage);
                    createChoiceButtons(questions.get(currentQuestionNumber).choices.size());
                }
            });

            quitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            if (currentQuestionNumber == 15) {
                nextButton.setText("Finish");
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(QuestionActivity.this, StatsActivity.class);
                        intent.putExtra(STATS_USERS_ANSWER_KEY, answers);
                        intent.putExtra(STATS_QUESTIONS_ANSWERS_KEY, questions);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    private void createChoiceButtons(int numberOfButtons) {
        RadioButton[] rb = new RadioButton[numberOfButtons];
        rg = findViewById(R.id.radioGroup);
        rg.removeAllViews();
        rg.clearCheck();

        for (int i = 0; i < numberOfButtons; i++) {
            rb[i] = new RadioButton(this);
            rb[i].setText(questions.get(currentQuestionNumber).choices.get(i));
            LinearLayout.LayoutParams linearLayout = new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT);
            rg.addView(rb[i], 0, linearLayout);
        }
    }

    private int getCheckedAnswer() {
        return rg.indexOfChild(findViewById(rg.getCheckedRadioButtonId()));
    }
}
