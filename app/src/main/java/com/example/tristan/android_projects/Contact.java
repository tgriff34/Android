package com.example.tristan.android_projects;

import java.io.Serializable;

/**
 * Created by Tristan on 2/6/18.
 */

public class Contact implements Serializable {

    String user_firstName, user_lastName, user_company, user_phone, user_email, user_url,
        user_address, user_nickname, user_facebook, user_twitter, user_skype, user_youtube;

    public Contact(String user_firstName, String user_lastName, String user_company,
                   String user_phone, String user_email, String user_url, String user_address,
                   String user_nickname, String user_facebook, String user_twitter,
                   String user_skype, String user_youtube) {
        this.user_firstName = user_firstName;
        this.user_lastName = user_lastName;
        this.user_company = user_company;
        this.user_phone = user_phone;
        this.user_email = user_email;
        this.user_url = user_url;
        this.user_address = user_address;
        this.user_nickname = user_nickname;
        this.user_facebook = user_facebook;
        this.user_twitter = user_twitter;
        this.user_skype = user_skype;
        this.user_youtube = user_youtube;
    }
}
