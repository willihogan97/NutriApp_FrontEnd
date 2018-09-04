package com.nutriapp.nutriapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nutriapp.nutriapp.object.MakananExternal;
import com.nutriapp.nutriapp.object.Parenteral;
import com.nutriapp.nutriapp.object.TabelMakanan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import steelkiwi.com.library.DotsLoaderView;

@SuppressLint("Registered")
public class AddFood extends AppCompatActivity {

    public static final String EXTRA_DATA = "EXTRA_DATA";
    public static final String TABELMAKANAN = "TABELMAKANAN";
    Button submit;
    EditText urtView, nameView, carbohydrateView, proteinView, fatView, caloriesView;
    String urt, name, carbohydrate, protein, fat, calories;
    DotsLoaderView dotsLoaderView;
    MakananExternal makananBaru;
    ArrayList<TabelMakanan> tabelMakanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_food);

        dotsLoaderView = findViewById(R.id.loader);
        dotsLoaderView.show();
        dotsLoaderView.hide();

        Intent intent = getIntent();
        tabelMakanan = intent.getParcelableArrayListExtra(TambahJadwalExternal.TABELMAKANAN);

        String[] arraySpinner = new String[] {
                "Karbohidrat", "Protein", "Lemak", "Sayuran", "Buah/Gula", "Susu", "Minyak"
        };

        final Spinner s = findViewById(R.id.addFoodDropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        urtView = findViewById(R.id.addFoodURT);
        nameView = findViewById(R.id.addFoodName);
        carbohydrateView = findViewById(R.id.addFoodCarbohydrate);
        proteinView = findViewById(R.id.addFoodProtein);
        fatView = findViewById(R.id.addFoodFat);
        caloriesView = findViewById(R.id.addFoodCalories);

        @SuppressLint("StaticFieldLeak") final AsyncTask<String, String, String> loaderAsync = new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                dotsLoaderView.animateViews();
                dotsLoaderView.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                AddFood.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        addDatabase("/api/external/add", makananBaru);
                        dotsLoaderView.hide();
                    }
                });
                return "done";
            }
        };

        submit = findViewById(R.id.buttonAddFood);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(carbohydrateView.getText().toString().equals("") | nameView.getText().toString().equals("")
                        | proteinView.getText().toString().equals("") | caloriesView.getText().toString().equals("")
                        | fatView.getText().toString().equals("") | urtView.getText().toString().equals("")
                        | s.getSelectedItem().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Ada kolom yang masih kosong", Toast.LENGTH_LONG).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddFood.this);
                    builder.setTitle(R.string.app_name);
                    builder.setMessage("Apa anda yakin untuk menyimpan makanan baru ini ?");
                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            carbohydrate = carbohydrateView.getText().toString();
                            name = nameView.getText().toString();
                            protein = proteinView.getText().toString();
                            calories = caloriesView.getText().toString();
                            fat = fatView.getText().toString();
                            urt = urtView.getText().toString();
                            int tipe = 0;
                            if (s.getSelectedItem().toString().equalsIgnoreCase("Karbohidrat")) {
                                tipe = 1;
                            } else if (s.getSelectedItem().toString().equalsIgnoreCase("protein")) {
                                tipe = 2;
                            } else if (s.getSelectedItem().toString().equalsIgnoreCase("lemak")) {
                                tipe = 3;
                            } else if (s.getSelectedItem().toString().equalsIgnoreCase("sayuran")) {
                                tipe = 4;
                            } else if (s.getSelectedItem().toString().equalsIgnoreCase("buah/gula")) {
                                tipe = 5;
                            } else if (s.getSelectedItem().toString().equalsIgnoreCase("susu")) {
                                tipe = 6;
                            } else if (s.getSelectedItem().toString().equalsIgnoreCase("minyak")) {
                                tipe = 7;
                            }

                            makananBaru = new MakananExternal(tipe, Double.parseDouble(calories), Double.parseDouble(carbohydrate)
                                    , Double.parseDouble(protein), Double.parseDouble(urt), Double.parseDouble(fat), name);
                            String makanan = (new Gson().toJson(makananBaru));
                            //Tinggal kasi ke backend
                            loaderAsync.execute();

                            final Intent data = new Intent();
                            data.putExtra(EXTRA_DATA, makanan);
                            data.putExtra(TABELMAKANAN, tabelMakanan);
                            setResult(Activity.RESULT_OK, data);
                            finish();
                        }
                    });
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
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


    public String addDatabase(final String url, MakananExternal makananExternal) {
        String result = "";

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
            json.put("tipe", makananExternal.getJenis());
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