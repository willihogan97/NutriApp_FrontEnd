package com.nutriapp.nutriapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.nutriapp.nutriapp.object.Parenteral;

@SuppressLint("Registered")
public class AddParenteral extends AppCompatActivity {

    public static final String EXTRA_DATA = "EXTRA_DATA";
    Button submit;
    EditText volumeView, nameView, electroliteView, carbohydrateView, proteinView, fatView, caloriesView;
    String name, volume, carbohydrate, electrolite, protein, fat, calories;

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
                carbohydrate = carbohydrateView.getText().toString();
                name = nameView.getText().toString();
                protein = proteinView.getText().toString();
                calories = caloriesView.getText().toString();
                fat = fatView.getText().toString();
                volume = volumeView.getText().toString();
                electrolite = electroliteView.getText().toString();

                Parenteral makananBaru = new Parenteral(carbohydrate, name, volume, protein, calories, fat, electrolite);
                String makanan = (new Gson().toJson(makananBaru));
                final Intent data = new Intent();
                data.putExtra(EXTRA_DATA, makanan);

                setResult(Activity.RESULT_OK, data);
                finish();

                setContentView(R.layout.parenteral);
            }
        });

    }
}