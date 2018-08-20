package com.nutriapp.nutriapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.nutriapp.nutriapp.object.MakananExternal;

@SuppressLint("Registered")
public class AddFood extends AppCompatActivity {

    public static final String EXTRA_DATA = "EXTRA_DATA";
    Button submit;
    EditText urtView, nameView, carbohydrateView, proteinView, fatView, caloriesView;
    String urt, name, carbohydrate, protein, fat, calories;

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
                carbohydrate = carbohydrateView.getText().toString();
                name = nameView.getText().toString();
                protein = proteinView.getText().toString();
                calories = caloriesView.getText().toString();
                fat = fatView.getText().toString();
                urt = urtView.getText().toString();

                MakananExternal makananBaru = new MakananExternal(carbohydrate, name, calories, fat, protein, urt);
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