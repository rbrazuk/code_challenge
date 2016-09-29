package com.example.android.codechallenge;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class PeopleList {
    List<Person> mPeople;

    public PeopleList() {
        mPeople = new ArrayList<Person>();
    }

    public static PeopleList parseJson(String json) {
        Gson gson = new GsonBuilder().create();
        PeopleList peopleList = gson.fromJson(json, PeopleList.class);
        return peopleList;
    }
}
