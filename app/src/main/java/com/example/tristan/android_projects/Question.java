package com.example.tristan.android_projects;

/**
 * Created by Tristan on 3/2/18.
 */

public class Question {
    String id, text, image, choices[], answer;

    public Question(String id, String text, String image, String[] choices, String answer) {
        this.id = id;
        this.text = text;
        this.image = image;
        this.choices = choices;
        this.answer = answer;
    }
}
