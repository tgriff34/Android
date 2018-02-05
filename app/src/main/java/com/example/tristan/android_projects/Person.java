package com.example.tristan.android_projects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tristan on 1/29/18.
 */

public class Person implements Parcelable {

    String name;
    String city;
    double age;

    public Person(String name, String city, double age) {
        this.name = name;
        this.city = city;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", age=" + age +
                '}';
    }

    protected Person(Parcel in) {
        this.name = in.readString();
        this.age = in.readDouble();
        this.city = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeDouble(this.age);
        parcel.writeString(this.city);
    }
}
