package com.example.android.codechallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_first_name) EditText etFirstName;
    @BindView(R.id.et_last_name) EditText etLastName;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.sp_name_format) Spinner spNameFormat;

    private final String PEOPLE_ENDPOINT = "https://gist.githubusercontent.com/joxenford/c49932a9ce74007e49b466cae8886fec/raw/8a999d3011cc000dec989538951c370c4bcfce5c/people.json";

    private String mFirstName = "";
    private String mLastName = "";
    private boolean isDefaultNameFormat = true;
    private String json;

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setUpSpinner(spNameFormat);

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

    public void setUpSpinner(Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.name_formats,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    public void populateNameField() {
        if (isDefaultNameFormat) {
            tvName.setText(String.format(getResources().getString(R.string.name_format_first_last), mFirstName, mLastName));
        } else {
            tvName.setText(String.format(getResources().getString(R.string.name_format_last_first), mFirstName, mLastName));
        }
    }

    @OnTextChanged(R.id.et_first_name)
    public void onFirstNameTextChanged() {
        mFirstName = etFirstName.getText().toString();
        populateNameField();
    }

    @OnTextChanged(R.id.et_last_name)
    public void onLastNameTextChanged() {
        mLastName = etLastName.getText().toString();
        populateNameField();
    }

    @OnItemSelected(R.id.sp_name_format)
    public void onNameFormatSelected(int position) {
        switch (position) {
            case 0:
                isDefaultNameFormat = true;
                populateNameField();
                break;
            case 1:
                isDefaultNameFormat = false;
                populateNameField();
                break;
            default:
                break;
        }
    }
}
