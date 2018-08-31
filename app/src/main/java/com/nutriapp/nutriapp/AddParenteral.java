package com.nutriapp.nutriapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.nutriapp.nutriapp.object.Parenteral;

import org.json.JSONException;
import org.json.JSONObject;

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
                if(carbohydrateView.getText().toString().equals("") | nameView.getText().toString().equals("") | proteinView.getText().toString().equals("")
                        | caloriesView.getText().toString().equals("") | fatView.getText().toString().equals("")
                        | volumeView.getText().toString().equals("") | electroliteView.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Ada kolom yang masih kosong", Toast.LENGTH_LONG);
                } else {
                    carbohydrate = carbohydrateView.getText().toString();
                    name = nameView.getText().toString();
                    protein = proteinView.getText().toString();
                    calories = caloriesView.getText().toString();
                    fat = fatView.getText().toString();
                    volume = volumeView.getText().toString();
                    electrolite = electroliteView.getText().toString();

                    Parenteral makananBaru = new Parenteral(name, Double.parseDouble(volume),
                            Double.parseDouble(carbohydrate), Double.parseDouble(protein), Double.parseDouble(fat),
                            Double.parseDouble(electrolite), Double.parseDouble(calories));
                    String makanan = (new Gson().toJson(makananBaru));
                    final Intent data = new Intent();
                    data.putExtra(EXTRA_DATA, makanan);

                    setResult(Activity.RESULT_OK, data);
                    finish();

                    addDatabase("/api/parenteral/add", makananBaru);
                    setContentView(R.layout.parenteral);
                }
            }
        });
    }

    public String addDatabase(final String url, Parenteral parenteralModel) {
        StringBuilder result = new StringBuilder();

        //url backend
        String apiUrl = "http://nutriapp-backend.herokuapp.com" + url;

        JSONObject json = new JSONObject();
        try {
            json.put("nama", parenteralModel.getName());
            json.put("karbohidrat", parenteralModel.getCarbohydrate());
            json.put("protein", parenteralModel.getProtein());
            json.put("lemak", parenteralModel.getFat());
            json.put("kalori", parenteralModel.getCalories());
            json.put("volume", parenteralModel.getVolume());
            json.put("elektrolit", parenteralModel.getElectrolite());
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
}