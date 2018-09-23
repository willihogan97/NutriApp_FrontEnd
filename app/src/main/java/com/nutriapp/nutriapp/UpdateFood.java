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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nutriapp.nutriapp.object.MakananExternal;
import com.nutriapp.nutriapp.object.TabelMakanan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import steelkiwi.com.library.DotsLoaderView;

@SuppressLint("Registered")
public class UpdateFood extends AppCompatActivity {

    public static final String EXTRA_DATA = "EXTRA_DATA";
    public static final String TABELMAKANAN = "TABELMAKANAN";
    Button submit, delete, updateFood;
    EditText urtView, nameView, carbohydrateView, proteinView, fatView, caloriesView;
    EditText urtUpdateView, nameUpdateView, carbohydrateUpdateView, proteinUpdateView, fatUpdateView, caloriesUpdateView;
    String urt, name, carbohydrate, protein, fat, calories, jenis;
    DotsLoaderView dotsLoaderView;
    MakananExternal makananBaru;
    ArrayList<TabelMakanan> tabelMakanan;
    ArrayList<MakananExternal> listAll;
    Spinner spinnerAksi;
    LinearLayout updateView, tambahView;
    ArrayAdapter adapterAksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_food);

        dotsLoaderView = findViewById(R.id.loader);

        Intent intent = getIntent();
        tabelMakanan = intent.getParcelableArrayListExtra(TambahJadwalExternal.TABELMAKANAN);

        final MakananExternal[] makananItem = new MakananExternal[1];

        String[] arraySpinner = new String[] {
                "Karbohidrat", "Protein", "Lemak", "Sayuran", "Buah/Gula", "Susu", "Minyak"
        };

        final Spinner s = findViewById(R.id.addFoodDropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        final Spinner sFoodUpdate = findViewById(R.id.updateFoodDropdown);
        ArrayAdapter<String> adapterFood = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapterFood.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sFoodUpdate.setAdapter(adapterFood);

        urtView = findViewById(R.id.addFoodURT);
        nameView = findViewById(R.id.addFoodName);
        carbohydrateView = findViewById(R.id.addFoodCarbohydrate);
        proteinView = findViewById(R.id.addFoodProtein);
        fatView = findViewById(R.id.addFoodFat);
        caloriesView = findViewById(R.id.addFoodCalories);

        urtUpdateView = findViewById(R.id.updateFoodURT);
        nameUpdateView = findViewById(R.id.updateFoodName);
        carbohydrateUpdateView = findViewById(R.id.updateFoodCarbohydrate);
        proteinUpdateView = findViewById(R.id.updateFoodProtein);
        fatUpdateView = findViewById(R.id.updateFoodFat);
        caloriesUpdateView = findViewById(R.id.updateFoodCalories);

        spinnerAksi = findViewById(R.id.spinneraksi);
        updateView = findViewById(R.id.updateView);
        tambahView = findViewById(R.id.tambahView);
        updateView.setVisibility(View.GONE);
        tambahView.setVisibility(View.GONE);

        String[] spinnerAksiArray = getResources().getStringArray(R.array.spinnerAksi);
        adapterAksi = new ArrayAdapter<>(UpdateFood.this, android.R.layout.simple_spinner_item, spinnerAksiArray);
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

        dotsLoaderView.show();
        getDataFromDatabase();
        dotsLoaderView.hide();
        final Spinner spinnerMakanan = findViewById(R.id.spinnerMakanan);
        ArrayAdapter<MakananExternal> adapterMakananSemua = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listAll);
        adapterMakananSemua.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMakanan.setAdapter(adapterMakananSemua);
        spinnerMakanan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!spinnerMakanan.getSelectedItem().toString().equals("")) {
//                        volume.setVisibility(View.VISIBLE);
                    for (int i = 0; i < listAll.size(); i++) {
                        if (spinnerMakanan.getSelectedItem().toString().equals(listAll.get(i).getNama())) {
                            makananItem[0] = listAll.get(i);
                        }
                    }
                    carbohydrate = String.valueOf(makananItem[0].getKarbohidrat());
                    name = String.valueOf(makananItem[0].getNama());
                    protein = String.valueOf(makananItem[0].getProtein());
                    calories = String.valueOf(makananItem[0].getKalori());
                    fat = String.valueOf(makananItem[0].getLemak());
                    jenis = String.valueOf(makananItem[0].getJenis());
                    urt = String.valueOf(makananItem[0].getUrt());
                    carbohydrateUpdateView.setText(carbohydrate);
                    nameUpdateView.setText(name);
                    proteinUpdateView.setText(protein);
                    caloriesUpdateView.setText(calories);
                    fatUpdateView.setText(fat);
                    urtUpdateView.setText(urt);
                    sFoodUpdate.setSelection(Integer.parseInt(jenis) - 1);
                    Log.d("keberapaye", "onItemSelected: " + jenis);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        delete = findViewById(R.id.buttonDeleteFood);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //panggil method

                if(makananItem[0] != null) {
                    String myUrl = "http://nutriapp-backend.herokuapp.com/api/external/delete/" + makananItem[0].getId();
                    HttpSendRequest getRequest = new HttpSendRequest();
                    getRequest.execute(myUrl, "");
                    final Intent data = new Intent();
                    data.putExtra(TABELMAKANAN, tabelMakanan);
                    setResult(Activity.RESULT_OK, data);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Pilih parenteral", Toast.LENGTH_LONG).show();
                }
            }
        });

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
                    dotsLoaderView.show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateFood.this);
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
                            addDatabase("/api/external/add", makananBaru);
                            dotsLoaderView.hide();

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

        updateFood = findViewById(R.id.buttonUpdateFood);
        updateFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(carbohydrateUpdateView.getText().toString().equals("") | nameUpdateView.getText().toString().equals("")
                        | proteinUpdateView.getText().toString().equals("") | caloriesUpdateView.getText().toString().equals("")
                        | fatUpdateView.getText().toString().equals("") | urtUpdateView.getText().toString().equals("")
                        | sFoodUpdate.getSelectedItem().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Ada kolom yang masih kosong", Toast.LENGTH_LONG).show();
                } else {
                    dotsLoaderView.show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateFood.this);
                    builder.setTitle(R.string.app_name);
                    builder.setMessage("Apa anda yakin untuk menyimpan makanan baru ini ?");
                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            carbohydrate = carbohydrateUpdateView.getText().toString();
                            name = nameUpdateView.getText().toString();
                            protein = proteinUpdateView.getText().toString();
                            calories = caloriesUpdateView.getText().toString();
                            fat = fatUpdateView.getText().toString();
                            urt = urtUpdateView.getText().toString();
                            int tipe = 0;
                            if (sFoodUpdate.getSelectedItem().toString().equalsIgnoreCase("Karbohidrat")) {
                                tipe = 1;
                            } else if (sFoodUpdate.getSelectedItem().toString().equalsIgnoreCase("protein")) {
                                tipe = 2;
                            } else if (sFoodUpdate.getSelectedItem().toString().equalsIgnoreCase("lemak")) {
                                tipe = 3;
                            } else if (sFoodUpdate.getSelectedItem().toString().equalsIgnoreCase("sayuran")) {
                                tipe = 4;
                            } else if (sFoodUpdate.getSelectedItem().toString().equalsIgnoreCase("buah/gula")) {
                                tipe = 5;
                            } else if (sFoodUpdate.getSelectedItem().toString().equalsIgnoreCase("susu")) {
                                tipe = 6;
                            } else if (sFoodUpdate.getSelectedItem().toString().equalsIgnoreCase("minyak")) {
                                tipe = 7;
                            }

                            makananBaru = new MakananExternal(tipe, Double.parseDouble(calories), Double.parseDouble(carbohydrate)
                                    , Double.parseDouble(protein), Double.parseDouble(urt), Double.parseDouble(fat), name, makananItem[0].getId());
                            String makanan = (new Gson().toJson(makananBaru));
                            //Tinggal kasi ke backend
                            updateDatabase("/api/external/update", makananBaru);
                            dotsLoaderView.hide();

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

    public String updateDatabase(final String url, MakananExternal makananExternal) {
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
            json.put("id", makananExternal.getId());
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

    private void getDataFromDatabase() {

        String myUrl = "http://nutriapp-backend.herokuapp.com/api/external/all";

        //String to place our result in
        listAll = new ArrayList<>();
        String result;
        HttpGetRequest newGetReq = new HttpGetRequest();
        try {
            result = newGetReq.execute(myUrl).get();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                String nama = jsonArray.getJSONObject(i).getString("nama");
                double karbohidrat = jsonArray.getJSONObject(i).getDouble("karbohidrat");
                double protein = jsonArray.getJSONObject(i).getDouble("protein");
                double lemak = jsonArray.getJSONObject(i).getDouble("lemak");
                double urt = jsonArray.getJSONObject(i).getDouble("urt");
                int tipe = jsonArray.getJSONObject(i).getInt("tipe");
                double kalori = jsonArray.getJSONObject(i).getDouble("kalori");
                int id = jsonArray.getJSONObject(i).getInt("id");
                MakananExternal makanan = new MakananExternal(tipe, kalori, karbohidrat, protein, urt, lemak, nama, id);
                listAll.add(makanan);
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
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