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
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_first_name) EditText etFirstName;
    @BindView(R.id.et_last_name) EditText etLastName;
    @BindView(R.id.tv_first_name) TextView tvFirstName;
    @BindView(R.id.tv_last_name) TextView tvLastName;
    @BindView(R.id.sp_name_format) Spinner spNameFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setUpSpinner(spNameFormat);
    }

    @OnTextChanged(R.id.et_first_name)
    public void onFirstNameTextChanged() {
        tvFirstName.setText(etFirstName.getText().toString());
    }

    @OnTextChanged(R.id.et_last_name)
    public void onLastNameTextChanged() {
        tvLastName.setText(etLastName.getText().toString());
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

}
