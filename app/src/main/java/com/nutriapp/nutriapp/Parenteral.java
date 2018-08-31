package com.nutriapp.nutriapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nutriapp.nutriapp.object.InfoPribadi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import steelkiwi.com.library.DotsLoaderView;

@SuppressLint("Registered")
public class Parenteral extends AppCompatActivity {

    Button next;
    EditText volumeView;
    LinearLayout selection, volume, detail;
    TextView karbohidrat, protein, lemak, elektrolit, kalori, sisa;
    InfoPribadi infoPribadi;
    DotsLoaderView dotsLoaderView;
    ArrayAdapter<com.nutriapp.nutriapp.object.Parenteral> adapter;
    Spinner s;

    public static final String INFO = "INFO_PRIBADI";
    public static final String PARENTERAL = "PARENTERAL";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parenteral);
        s = findViewById(R.id.parenteralSelection);
        selection = findViewById(R.id.selection);
        volume = findViewById(R.id.volume);
        detail = findViewById(R.id.detail);
        karbohidrat = findViewById(R.id.carbohydrateDetailValue);
        protein = findViewById(R.id.proteinDetailValue);
        lemak = findViewById(R.id.fatDetailVslue);
        elektrolit = findViewById(R.id.electroliteDetailVslue);
        kalori = findViewById(R.id.caloriesDetailVslue);
        sisa = findViewById(R.id.caloriesRemaindersValue);
        dotsLoaderView = findViewById(R.id.loader);

        selection.setVisibility(View.VISIBLE);
        volume.setVisibility(View.GONE);
        detail.setVisibility(View.GONE);

        Intent intent = getIntent();
        infoPribadi = intent.getParcelableExtra(MainActivity.INFO);

        final String myUrl = "http://nutriapp-backend.herokuapp.com/api/parenteral/all";

        //String to place our result in
        final List<com.nutriapp.nutriapp.object.Parenteral> listAll = new ArrayList<>();
        final String[] result = new String[1];
        final HttpGetRequest getRequest = new HttpGetRequest();

        //Perform the doInBackground method, passing in our url
        @SuppressLint("StaticFieldLeak") AsyncTask<String, String, String> loaderAsync = new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                dotsLoaderView.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                Parenteral.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        try{
                            HttpGetRequest getreq = new HttpGetRequest();
                            String result = getreq.execute(myUrl).get();
                            JSONObject jsnobject = new JSONObject(result);
                            JSONArray jsonArrayResult = jsnobject.getJSONArray("result");

                            for (int i = 0; i < jsonArrayResult.length(); i++) {
                                String nama = jsonArrayResult.getJSONObject(i).getString("nama");
                                double karbohidrat = jsonArrayResult.getJSONObject(i).getDouble("karbohidrat");
                                double protein = jsonArrayResult.getJSONObject(i).getDouble("protein");
                                double lemak = jsonArrayResult.getJSONObject(i).getDouble("lemak");
                                double elektrolit = jsonArrayResult.getJSONObject(i).getDouble("elektrolit");
                                double kalori = jsonArrayResult.getJSONObject(i).getDouble("kalori");
                                com.nutriapp.nutriapp.object.Parenteral parenteral = new com.nutriapp.nutriapp.object.Parenteral(nama, 1, karbohidrat, protein, lemak, elektrolit, kalori);
                                listAll.add(parenteral);
                            }
                            adapter = new ArrayAdapter<>(Parenteral.this, android.R.layout.simple_spinner_item, listAll);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            s.setAdapter(adapter);
                            dotsLoaderView.hide();
                        } catch (InterruptedException | ExecutionException | JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
                return "done";
            }
        };
        loaderAsync.execute();

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listAll);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        final com.nutriapp.nutriapp.object.Parenteral[] parenteralItem = new com.nutriapp.nutriapp.object.Parenteral[1];
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(!s.getSelectedItem().toString().equals("")) {
                    volume.setVisibility(View.VISIBLE);
                    for(int i = 0; i < listAll.size(); i++){
                        if(s.getSelectedItem().toString().equals(listAll.get(i).getName())){
                            parenteralItem[0] = listAll.get(i);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        volumeView = findViewById(R.id.editVolume);
        volumeView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable s) {
                detail.setVisibility(View.VISIBLE);
                DecimalFormat dec = new DecimalFormat("#.0");
                double ratio = Double.parseDouble(volumeView.getText().toString()) / parenteralItem[0].getVolume();
                karbohidrat.setText(dec.format((parenteralItem[0].getCarbohydrate() * ratio)));
                protein.setText(dec.format((parenteralItem[0].getProtein() * ratio)));
                lemak.setText(dec.format((parenteralItem[0].getFat() * ratio)));
                elektrolit.setText(dec.format((parenteralItem[0].getElectrolite() * ratio)));
                kalori.setText(dec.format((parenteralItem[0].getCalories() * ratio)));
                double remain = infoPribadi.getTotalKalori() - (parenteralItem[0].getCalories() * ratio);
                double cairRemain = infoPribadi.getTotalKaloriCair() - Double.parseDouble(volumeView.getText().toString());
                String sisaText = dec.format((remain)) + "Kkal - " + dec.format((cairRemain)) + "ml";
                sisa.setText(sisaText);
            }
        });

        next = findViewById(R.id.buttonNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MakananExternalActivity.class);
                intent.putExtra(INFO, infoPribadi);
                com.nutriapp.nutriapp.object.Parenteral parenteral = new com.nutriapp.nutriapp.object.Parenteral(
                        s.getSelectedItem().toString(), Double.parseDouble(volumeView.getText().toString()),
                        Double.parseDouble(karbohidrat.getText().toString()), Double.parseDouble(protein.getText().toString()),
                                Double.parseDouble(lemak.getText().toString()), Double.parseDouble(elektrolit.getText().toString()),
                                        Double.parseDouble(kalori.getText().toString()));
                intent.putExtra(PARENTERAL, parenteral);
                startActivityForResult(intent, 200);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200) {

            if(resultCode == Activity.RESULT_OK) {

            } else {
                // AnotherActivity was not successful. No data to retrieve.
            }
        }
    }
}
