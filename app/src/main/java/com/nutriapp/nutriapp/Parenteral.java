package com.nutriapp.nutriapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

@SuppressLint("Registered")
public class Parenteral extends AppCompatActivity {

    Button next;
    EditText volumeView;
    RelativeLayout selection, volume;
    LinearLayout detail;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parenteral);
        selection = (RelativeLayout) findViewById(R.id.selection);
        volume = (RelativeLayout) findViewById(R.id.volume);
        detail = (LinearLayout) findViewById(R.id.detail);

        String[] arraySpinner = new String[] {
                "1", "2", "3", "4", "5"
        };

        Spinner s = (Spinner) findViewById(R.id.parenteralDropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                volume.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                volume.setVisibility(View.INVISIBLE);
                selection.setVisibility(View.VISIBLE);
                detail.setVisibility(View.INVISIBLE);
            }
        });

        volumeView = (EditText) findViewById(R.id.volumeInput);
        volumeView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable s) {
                detail.setVisibility(View.VISIBLE);
            }
        });

        next = findViewById(R.id.buttonNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.makanan_external);
            }
        });
    }
}
