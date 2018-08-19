package com.nutriapp.nutriapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

@SuppressLint("Registered")
public class AddParenteral extends AppCompatActivity {

    Button submit;
    EditText volumeView, nameView, electroliteView, carbohydrateView, proteinView, fatView, caloriesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_parenteral);

        volumeView = (EditText) findViewById(R.id.addParenteralVolume);
        nameView = (EditText) findViewById(R.id.addParenteralName);
        electroliteView = (EditText) findViewById(R.id.addParenteralElectrolite);
        carbohydrateView = (EditText) findViewById(R.id.addParenteralCarbohydrate);
        proteinView = (EditText) findViewById(R.id.addParenteralProtein);
        fatView = (EditText) findViewById(R.id.addParenteralFat);
        caloriesView = (EditText) findViewById(R.id.addParenteralCalories);

        submit = findViewById(R.id.buttonAddParenteral);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.parenteral);
            }
        });

    }
}