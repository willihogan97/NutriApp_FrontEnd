package com.nutriapp.nutriapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

@SuppressLint("Registered")
public class Parenteral extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.parenteral);

            String[] arraySpinner = new String[] {
                    "1", "2", "3", "4", "5"
            };
            Spinner s = (Spinner) findViewById(R.id.parenteralDropdown);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, arraySpinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(adapter);
        }
}
