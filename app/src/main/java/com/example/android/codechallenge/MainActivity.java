package com.example.android.codechallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_first_name) EditText etFirstName;
    @BindView(R.id.et_last_name) EditText etLastName;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.sp_name_format) Spinner spNameFormat;

    private String mFirstName = "";
    private String mLastName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setUpSpinner(spNameFormat);
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
        tvName.setText(String.format(getResources().getString(R.string.name_format_first_last), mFirstName, mLastName));
    }

}
