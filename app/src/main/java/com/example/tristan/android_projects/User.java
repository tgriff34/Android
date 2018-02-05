package com.example.tristan.android_projects;

import java.io.Serializable;

/**
 * Created by Tristan on 1/29/18.
 */

public class User implements Serializable {
    String name;
    double age;

    public User(String name, double age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
