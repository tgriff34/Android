package com.example.tristan.android_projects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Tristan on 3/2/18.
 */

public class Question implements Parcelable {
    String id, text, image, answer;
    ArrayList<String> choices;

    public Question(String id, String text, String image, ArrayList<String> choices, String answer) {
        this.id = id;
        this.text = text;
        this.image = image;
        this.choices = choices;
        this.answer = answer;
    }

    protected Question(Parcel in) {
        id = in.readString();
        text = in.readString();
        image = in.readString();
        answer = in.readString();
        choices = in.createStringArrayList();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(text);
        parcel.writeString(image);
        parcel.writeString(answer);
        parcel.writeStringList(choices);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", image='" + image + '\'' +
                ", answer='" + answer + '\'' +
                ", choices=" + choices +
                '}';
    }
}
