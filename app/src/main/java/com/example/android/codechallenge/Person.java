package com.example.android.codechallenge;


import com.google.gson.annotations.SerializedName;

public class Person {

    String mFirstName;
    String mLastName;

    public Person(String firstName, String lastName) {
        mFirstName = firstName;
        mLastName = lastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }
}
