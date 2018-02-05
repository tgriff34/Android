package com.example.tristan.android_projects;

import java.io.Serializable;

/**
 * Created by Tristan on 1/29/18.
 */

public class User implements Serializable {
    String user_name, user_email, department, mood;

    public User(String name, String email, String department, String mood) {
        this.user_name = name;
        this.user_email = email;
        this.department = department;
        this.mood = mood;
    }

    @Override
    public String toString() {
        return
                "Name: " + user_name + '\n' +
                "Email: " + user_email + '\n' +
                "Department: " + department + '\n' +
                "I am " + mood;
    }
}
