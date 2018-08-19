package com.nutriapp.nutriapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

@SuppressLint("Registered")
public class AddFood extends AppCompatActivity {

    Button submit;
    EditText urtView, nameView, carbohydrateView, proteinView, fatView, caloriesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_food);

        String[] arraySpinner = new String[] {
                "1", "2", "3", "4", "5"
        };

        Spinner s = (Spinner) findViewById(R.id.addFoodDropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        urtView = (EditText) findViewById(R.id.addFoodURT);
        nameView = (EditText) findViewById(R.id.addFoodName);
        carbohydrateView = (EditText) findViewById(R.id.addFoodCarbohydrate);
        proteinView = (EditText) findViewById(R.id.addFoodProtein);
        fatView = (EditText) findViewById(R.id.addFoodFat);
        caloriesView = (EditText) findViewById(R.id.addFoodCalories);

        submit = findViewById(R.id.buttonAddParenteral);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.parenteral);
            }
        });
    }
}