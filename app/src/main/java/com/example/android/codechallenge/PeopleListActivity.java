package com.example.android.codechallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PeopleListActivity extends AppCompatActivity {

    private final String PEOPLE_ENDPOINT = "https://gist.githubusercontent.com/joxenford/c49932a9ce74007e49b466cae8886fec/raw/8a999d3011cc000dec989538951c370c4bcfce5c/people.json";
    private String json;

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_list);

        try {
            getPeopleJson(PEOPLE_ENDPOINT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(json);
    }

    public void getPeopleJson(String endpoint) throws Exception {
        Request request = new Request.Builder()
                .url(endpoint)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                json = response.body().string();

                try {
                    ArrayList<Person> peopleList = getPeopleListFromJson(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    public ArrayList<Person> getPeopleListFromJson(String json) throws JSONException {

        List<Person> peopleList = new ArrayList<>();
        JSONObject obj = new JSONObject(json);
        JSONArray people = obj.getJSONArray("people");

        for (int i = 0; i < people.length(); i++) {
            JSONObject personJson = (JSONObject) people.get(i);

            Person newPerson = new Person(personJson.getString("first_name"), personJson.getString("last_name"));
            peopleList.add(newPerson);
        }

        return (ArrayList<Person>) peopleList;
    }
}
