package com.nutriapp.nutriapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nutriapp.nutriapp.object.Parenteral;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import steelkiwi.com.library.DotsLoaderView;

import static android.view.View.GONE;

@SuppressLint("Registered")
public class UpdateParenteral extends AppCompatActivity {

    public static final String EXTRA_DATA = "EXTRA_DATA";
    Button submit, delete, submitAdd;
    EditText volumeView, nameView, electroliteView, carbohydrateView, proteinView, fatView, caloriesView;
    EditText volumeUpdateView, nameUpdateView, electroliteUpdateView, carbohydrateUpdateView, proteinUpdateView, fatUpdateView, caloriesUpdateView;
    String name, volume, carbohydrate, electrolite, protein, fat, calories;
    LinearLayout updateView, tambahView;

    List<Parenteral> listAll = new ArrayList<>();
    ArrayAdapter<com.nutriapp.nutriapp.object.Parenteral> adapter;
    ArrayAdapter adapterAksi;
    Spinner s, spinnerAksi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_parenteral);

        submit = findViewById(R.id.buttonUpdateParenteral);
        delete = findViewById(R.id.buttonDeleteParenteral);
        volumeView = findViewById(R.id.addParenteralVolume);
        nameView = findViewById(R.id.addParenteralName);
        electroliteView = findViewById(R.id.addParenteralElectrolite);
        carbohydrateView = findViewById(R.id.addParenteralCarbohydrate);
        proteinView = findViewById(R.id.addParenteralProtein);
        fatView = findViewById(R.id.addParenteralFat);
        caloriesView = findViewById(R.id.addParenteralCalories);

        volumeUpdateView = findViewById(R.id.updateParenteralVolume);
        nameUpdateView = findViewById(R.id.updateParenteralName);
        electroliteUpdateView = findViewById(R.id.updateParenteralElectrolite);
        carbohydrateUpdateView = findViewById(R.id.updateParenteralCarbohydrate);
        proteinUpdateView = findViewById(R.id.updateParenteralProtein);
        fatUpdateView = findViewById(R.id.updateParenteralFat);
        caloriesUpdateView = findViewById(R.id.updateParenteralCalories);

        s = findViewById(R.id.spinnerParenteral);
        spinnerAksi = findViewById(R.id.spinneraksi);
        updateView = findViewById(R.id.updateView);
        tambahView = findViewById(R.id.tambahView);
        final DotsLoaderView dotsLoaderView = (DotsLoaderView) findViewById(R.id.loader);
        updateView.setVisibility(View.GONE);
        tambahView.setVisibility(View.GONE);

        String[] spinnerAksiArray = getResources().getStringArray(R.array.spinnerAksi);
        adapterAksi = new ArrayAdapter<>(UpdateParenteral.this, android.R.layout.simple_spinner_item, spinnerAksiArray);
        adapterAksi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAksi.setAdapter(adapterAksi);
        spinnerAksi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinnerAksi.getSelectedItem().equals("Tambah")) {
                    tambahView.setVisibility(View.VISIBLE);
                    updateView.setVisibility(View.GONE);
                } else {
                    updateView.setVisibility(View.VISIBLE);
                    tambahView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final com.nutriapp.nutriapp.object.Parenteral[] parenteralItem = new com.nutriapp.nutriapp.object.Parenteral[1];

        String myUrl = "http://nutriapp-backend.herokuapp.com/api/parenteral/all";

        //String to place our result in
        listAll = new ArrayList<>();

        dotsLoaderView.show();
        HttpGetRequest getreq = new HttpGetRequest();
        try{
            String result = getreq.execute(myUrl).get();
            Log.d("resultnyaapadah", "onCreate: " + result);
            JSONObject jsnobject = new JSONObject(result);
            JSONArray jsonArrayResult = jsnobject.getJSONArray("result");

            for (int i = 0; i < jsonArrayResult.length(); i++) {
                String nama = jsonArrayResult.getJSONObject(i).getString("nama");
                int id = jsonArrayResult.getJSONObject(i).getInt("id");
                double karbohidrat = jsonArrayResult.getJSONObject(i).getDouble("karbohidrat");
                double protein = jsonArrayResult.getJSONObject(i).getDouble("protein");
                double lemak = jsonArrayResult.getJSONObject(i).getDouble("lemak");
                double elektrolit = jsonArrayResult.getJSONObject(i).getDouble("elektrolit");
                double kalori = jsonArrayResult.getJSONObject(i).getDouble("kalori");
                com.nutriapp.nutriapp.object.Parenteral parenteral = new com.nutriapp.nutriapp.object.Parenteral(nama, 1, karbohidrat, protein, lemak, elektrolit, kalori, id);
                listAll.add(parenteral);
            }
            adapter = new ArrayAdapter<>(UpdateParenteral.this, android.R.layout.simple_spinner_item, listAll);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(adapter);
            s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    if(!s.getSelectedItem().toString().equals("")) {
                        Log.d("testsini", "onItemSelected: " + s.getSelectedItem().toString());
//                        volume.setVisibility(View.VISIBLE);
                        for (int i = 0; i < listAll.size(); i++) {
                            if (s.getSelectedItem().toString().equals(listAll.get(i).getName())) {
                                parenteralItem[0] = listAll.get(i);
                            }
                        }
                        carbohydrate = String.valueOf(parenteralItem[0].getCarbohydrate());
                        carbohydrateUpdateView.setText(carbohydrate);
                        name = String.valueOf(parenteralItem[0].getName());
                        nameUpdateView.setText(name);
                        protein = String.valueOf(parenteralItem[0].getProtein());
                        proteinUpdateView.setText(protein);
                        calories = String.valueOf(parenteralItem[0].getCalories());
                        caloriesUpdateView.setText(calories);
                        fat = String.valueOf(parenteralItem[0].getFat());
                        fatUpdateView.setText(fat);
                        volume = String.valueOf(parenteralItem[0].getVolume());
                        volumeUpdateView.setText(volume);
                        electrolite = String.valueOf(parenteralItem[0].getElectrolite());
                        electroliteUpdateView.setText(electrolite);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            dotsLoaderView.hide();
        } catch (InterruptedException | ExecutionException | JSONException e){
            e.printStackTrace();
        }

        delete = findViewById(R.id.buttonDeleteParenteral);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(parenteralItem[0] != null) {
                    String myUrl = "http://nutriapp-backend.herokuapp.com/api/parenteral/delete/" + parenteralItem[0].getId();
                    HttpSendRequest getRequest = new HttpSendRequest();
                    getRequest.execute(myUrl, "");
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Pilih parenteral", Toast.LENGTH_LONG).show();
                }
            }
        });

        submit = findViewById(R.id.buttonUpdateParenteral);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(carbohydrateUpdateView.getText().toString().equals("") | nameUpdateView.getText().toString().equals("") | proteinUpdateView.getText().toString().equals("")
                        | caloriesUpdateView.getText().toString().equals("") | fatUpdateView.getText().toString().equals("")
                        | volumeUpdateView.getText().toString().equals("") | electroliteUpdateView.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Ada kolom yang masih kosong", Toast.LENGTH_LONG).show();
                } else {
                    dotsLoaderView.show();
                    carbohydrate = carbohydrateUpdateView.getText().toString();
                    name = nameUpdateView.getText().toString();
                    protein = proteinUpdateView.getText().toString();
                    calories = caloriesUpdateView.getText().toString();
                    fat = fatUpdateView.getText().toString();
                    volume = volumeUpdateView.getText().toString();
                    electrolite = electroliteUpdateView.getText().toString();

                    Parenteral makananBaru = new Parenteral(name, Double.parseDouble(volume),
                            Double.parseDouble(carbohydrate), Double.parseDouble(protein), Double.parseDouble(fat),
                            Double.parseDouble(electrolite), Double.parseDouble(calories), parenteralItem[0].getId());
                    String makanan = (new Gson().toJson(makananBaru));
                    updateDatabase("/api/parenteral/update", makananBaru);
                    dotsLoaderView.hide();
                    final Intent data = new Intent();
                    data.putExtra(EXTRA_DATA, makanan);
                    setResult(Activity.RESULT_OK, data);
                    finish();
                    setContentView(R.layout.parenteral);
                }
            }
        });

        submitAdd = findViewById(R.id.buttonAddParenteral);
        submitAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(carbohydrateView.getText().toString().equals("") | nameView.getText().toString().equals("") | proteinView.getText().toString().equals("")
                        | caloriesView.getText().toString().equals("") | fatView.getText().toString().equals("")
                        | volumeView.getText().toString().equals("") | electroliteView.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Ada kolom yang masih kosong", Toast.LENGTH_LONG).show();
                } else {
                    dotsLoaderView.show();
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
                    addDatabase("/api/parenteral/add", makananBaru);
                    dotsLoaderView.hide();
                    final Intent data = new Intent();
                    data.putExtra(EXTRA_DATA, makanan);
                    setResult(Activity.RESULT_OK, data);
                    finish();
                    setContentView(R.layout.parenteral);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public String deleteDatabase(final String url, Parenteral parenteralModel) {
        String result = "";

        //url backend
        String apiUrl = "http://nutriapp-backend.herokuapp.com" + url;

        JSONObject json = new JSONObject();
        try {
            json.put("id", parenteralModel.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpSendRequest getRequest = new HttpSendRequest();
        try {
            result = getRequest.execute(apiUrl, json.toString()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String updateDatabase(final String url, Parenteral parenteralModel) {
        String result = "";

        //url backend
        String apiUrl = "http://nutriapp-backend.herokuapp.com" + url;

        JSONObject json = new JSONObject();
        try {
            json.put("id", parenteralModel.getId());
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

        HttpSendRequest getRequest = new HttpSendRequest();
        try {
            result = getRequest.execute(apiUrl, json.toString()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String addDatabase(final String url, Parenteral parenteralModel) {
        String result = "";

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

        HttpSendRequest getRequest = new HttpSendRequest();
        try {
            result = getRequest.execute(apiUrl, json.toString()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }
}