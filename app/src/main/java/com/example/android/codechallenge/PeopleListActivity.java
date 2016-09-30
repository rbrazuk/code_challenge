package com.example.android.codechallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PeopleListActivity extends AppCompatActivity {

    @BindView(R.id.rv_people) RecyclerView rvPeople;

    private static OkHttpClient client = new OkHttpClient();
    private final String PEOPLE_ENDPOINT = "https://gist.githubusercontent.com/joxenford/c49932a9ce74007e49b466cae8886fec/raw/8a999d3011cc000dec989538951c370c4bcfce5c/people.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_list);

        ButterKnife.bind(this);

        populateViews();
    }

    public void populateViews() {
        Request request = new Request.Builder()
                .url(PEOPLE_ENDPOINT)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                String json = response.body().string();

                try {
                    List<Person> peopleList = getPeopleList(json);
                    if (peopleList != null || !peopleList.isEmpty()) {
                        setUpRecyclerView(peopleList);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setUpRecyclerView(List<Person> list) {
        PeopleAdapter adapter = new PeopleAdapter(this, list);
        rvPeople.setAdapter(adapter);
        rvPeople.setLayoutManager(new LinearLayoutManager(this));
    }

    public static ArrayList<Person> getPeopleList(String json) throws JSONException {
        List<Person> peopleList = new ArrayList<>();
        JSONObject obj =new JSONObject(json);
        JSONArray peopleJsonArray = obj.getJSONArray("people");

        for (int i = 0; i < peopleJsonArray.length(); i++) {
            JSONObject personJson = (JSONObject) peopleJsonArray.get(i);

            Person newPerson = new Person(personJson.getString("first_name"), personJson.getString("last_name"));
            peopleList.add(newPerson);
        }

        return (ArrayList<Person>) peopleList;
    }



}
