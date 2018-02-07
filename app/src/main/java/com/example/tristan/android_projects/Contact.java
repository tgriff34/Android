package com.example.tristan.android_projects;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Tristan on 2/6/18.
 */

public class Contact implements Parcelable {

    String user_firstName, user_lastName, user_company, user_phone, user_email, user_url,
        user_address, user_birthday, user_nickname, user_facebook, user_twitter, user_skype, user_youtube;

    public Contact(String user_firstName, String user_lastName, String user_company,
                   String user_phone, String user_email, String user_url, String user_address,
                   String user_birthday, String user_nickname, String user_facebook, String user_twitter,
                   String user_skype, String user_youtube) {
        this.user_firstName = user_firstName;
        this.user_lastName = user_lastName;
        this.user_company = user_company;
        this.user_phone = user_phone;
        this.user_email = user_email;
        this.user_url = user_url;
        this.user_address = user_address;
        this.user_birthday = user_birthday;
        this.user_nickname = user_nickname;
        this.user_facebook = user_facebook;
        this.user_twitter = user_twitter;
        this.user_skype = user_skype;
        this.user_youtube = user_youtube;
    }

    protected Contact(Parcel in) {
        user_firstName = in.readString();
        user_lastName = in.readString();
        user_company = in.readString();
        user_phone = in.readString();
        user_email = in.readString();
        user_url = in.readString();
        user_address = in.readString();
        user_birthday = in.readString();
        user_nickname = in.readString();
        user_facebook = in.readString();
        user_twitter = in.readString();
        user_skype = in.readString();
        user_youtube = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public String toString() {
        return "Contact{" +
                "user_firstName='" + user_firstName + '\'' +
                ", user_lastName='" + user_lastName + '\'' +
                ", user_company='" + user_company + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_url='" + user_url + '\'' +
                ", user_address='" + user_address + '\'' +
                ", user_birthday='" + user_birthday + '\'' +
                ", user_nickname='" + user_nickname + '\'' +
                ", user_facebook='" + user_facebook + '\'' +
                ", user_twitter='" + user_twitter + '\'' +
                ", user_skype='" + user_skype + '\'' +
                ", user_youtube='" + user_youtube + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(user_firstName);
        parcel.writeString(user_lastName);
        parcel.writeString(user_company);
        parcel.writeString(user_phone);
        parcel.writeString(user_email);
        parcel.writeString(user_url);
        parcel.writeString(user_address);
        parcel.writeString(user_birthday);
        parcel.writeString(user_nickname);
        parcel.writeString(user_facebook);
        parcel.writeString(user_twitter);
        parcel.writeString(user_skype);
        parcel.writeString(user_youtube);
    }
}
