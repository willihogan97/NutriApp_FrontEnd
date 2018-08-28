package com.nutriapp.nutriapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.nutriapp.nutriapp.object.MakananExternal;
import com.nutriapp.nutriapp.object.Parenteral;

import org.json.JSONException;
import org.json.JSONObject;

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        urtView = (EditText) findViewById(R.id.addFoodURT);
        nameView = (EditText) findViewById(R.id.addFoodName);
        carbohydrateView = (EditText) findViewById(R.id.addFoodCarbohydrate);
        proteinView = (EditText) findViewById(R.id.addFoodProtein);
        fatView = (EditText) findViewById(R.id.addFoodFat);
        caloriesView = (EditText) findViewById(R.id.addFoodCalories);
        final String tipe = s.getSelectedItem().toString();

        submit = findViewById(R.id.buttonAddFood);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carbohydrate = carbohydrateView.getText().toString();
                name = nameView.getText().toString();
                protein = proteinView.getText().toString();
                calories = caloriesView.getText().toString();
                fat = fatView.getText().toString();
                urt = urtView.getText().toString();

                MakananExternal makananBaru = new MakananExternal(convertTipe(tipe), Double.parseDouble(calories), Double.parseDouble(carbohydrate)
                        , Double.parseDouble(protein), urt, Double.parseDouble(fat), name);
                String makanan = (new Gson().toJson(makananBaru));
                //Tinggal kasi ke backend

                final Intent data = new Intent();
                data.putExtra(EXTRA_DATA, makanan);

                setResult(Activity.RESULT_OK, data);
                finish();

                addDatabase("/api/external/add", makananBaru);
                setContentView(R.layout.tambah_jadwal_external);
            }
        });
    }

    public String addDatabase(final String url, MakananExternal makananExternal) {
        StringBuilder result = new StringBuilder();

        //url backend
        String apiUrl = "http://nutriapp-backend.herokuapp.com" + url;

        JSONObject json = new JSONObject();
        try {
            json.put("nama", makananExternal.getNama());
            json.put("karbohidrat", makananExternal.getKarbohidrat());
            json.put("protein", makananExternal.getProtein());
            json.put("lemak", makananExternal.getLemak());
            json.put("kalori", makananExternal.getKalori());
            json.put("urt", makananExternal.getUrt());
            json.put("jenis", makananExternal.getJenis());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue req = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, apiUrl, json, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("isinya", "sendGet: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("asd", "onErrorResponse: asd");
                    }
                });

        req.add(jsonObjectRequest);

        return result.toString();
    }

    public double convertTipe(String tipe){
        switch (tipe) {
            case "karbohidrat":
                return 1;
            case "protein":
                return 2;
            case "lemak":
                return 3;
            case "sayuran":
                return 4;
            case "buah/gula":
                return 5;
            case "susu":
                return 6;
            default:
                return 7;
        }
    }
}