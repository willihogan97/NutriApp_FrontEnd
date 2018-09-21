package com.nutriapp.nutriapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.TextView;

import com.nutriapp.nutriapp.object.InfoPribadi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import steelkiwi.com.library.DotsLoaderView;

public class Parenteral extends AppCompatActivity {

    Button next, buttonEdit;
    EditText volumeView;
    LinearLayout selection, volume, detail;
    TextView karbohidrat, protein, lemak, elektrolit, kalori, sisa;
    InfoPribadi infoPribadi;
    DotsLoaderView dotsLoaderView;
    ArrayAdapter<com.nutriapp.nutriapp.object.Parenteral> adapter;
    Spinner s;
    List<com.nutriapp.nutriapp.object.Parenteral> listAll = new ArrayList<>();

    public static final String INFO = "INFO_PRIBADI";
    public static final String PARENTERAL = "PARENTERAL";
    public static final String VOLUME = "VOLUME";
    public static final String SPINNERSELECTED = "SPINNERSELECTED";

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
        buttonEdit = findViewById(R.id.buttonEdit);

        selection.setVisibility(View.VISIBLE);
        volume.setVisibility(View.GONE);
        detail.setVisibility(View.GONE);

        Intent intent = getIntent();
        infoPribadi = intent.getParcelableExtra(MainActivity.INFO);

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

        //Perform the doInBackground method, passing in our url
//        @SuppressLint("StaticFieldLeak") AsyncTask<String, String, String> loaderAsync = new AsyncTask<String, String, String>() {
//            @Override
//            protected void onPreExecute() {
//                dotsLoaderView.show();
//            }
//
//            @Override
//            protected String doInBackground(String... strings) {
//                Parenteral.this.runOnUiThread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        try{
//                            HttpGetRequest getreq = new HttpGetRequest();
//                            String result = getreq.execute(myUrl).get();
//                            JSONObject jsnobject = new JSONObject(result);
//                            JSONArray jsonArrayResult = jsnobject.getJSONArray("result");
//
//                            for (int i = 0; i < jsonArrayResult.length(); i++) {
//                                String nama = jsonArrayResult.getJSONObject(i).getString("nama");
//                                double karbohidrat = jsonArrayResult.getJSONObject(i).getDouble("karbohidrat");
//                                double protein = jsonArrayResult.getJSONObject(i).getDouble("protein");
//                                double lemak = jsonArrayResult.getJSONObject(i).getDouble("lemak");
//                                double elektrolit = jsonArrayResult.getJSONObject(i).getDouble("elektrolit");
//                                double kalori = jsonArrayResult.getJSONObject(i).getDouble("kalori");
//                                com.nutriapp.nutriapp.object.Parenteral parenteral = new com.nutriapp.nutriapp.object.Parenteral(nama, 1, karbohidrat, protein, lemak, elektrolit, kalori);
//                                listAll.add(parenteral);
//                            }
//                            adapter = new ArrayAdapter<>(Parenteral.this, android.R.layout.simple_spinner_item, listAll);
//                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            s.setAdapter(adapter);
//                            dotsLoaderView.hide();
//                        } catch (InterruptedException | ExecutionException | JSONException e){
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                return "done";
//            }
//        };
//        loaderAsync.execute();

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
                    if(!volumeView.getText().toString().equals("")) {
                        DecimalFormat dec = new DecimalFormat("#.0");
                        double ratio = Double.parseDouble(volumeView.getText().toString()) / parenteralItem[0].getVolume();
                        karbohidrat.setText(dec.format((parenteralItem[0].getCarbohydrate() * ratio)));
                        protein.setText(dec.format((parenteralItem[0].getProtein() * ratio)));
                        lemak.setText(dec.format((parenteralItem[0].getFat() * ratio)));
                        elektrolit.setText(dec.format((parenteralItem[0].getElectrolite() * ratio)));
                        kalori.setText(dec.format((parenteralItem[0].getCalories() * ratio)));
                        double remain = infoPribadi.getTotalKalori() - (parenteralItem[0].getCalories() * ratio);
                        double cairRemain = infoPribadi.getTotalKaloriCair() - Double.parseDouble(volumeView.getText().toString());
                        String sisaText = dec.format((remain)) + "Kkal / " + dec.format((cairRemain)) + "ml";
                        sisa.setText(sisaText);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpdateParenteral.class);
                startActivityForResult(intent, 200);
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
                if(!volumeView.getText().toString().equals("")) {
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
                    String sisaText = dec.format((remain)) + "Kkal / " + dec.format((cairRemain)) + "ml";
                    sisa.setText(sisaText);
                } else {
                    detail.setVisibility(View.GONE);
                }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200) {
            if(resultCode == Activity.RESULT_OK) {
                String myUrl = "http://nutriapp-backend.herokuapp.com/api/parenteral/all";
                Log.d("masuksiniga", "onActivityResult: ");
                //String to place our result in
                listAll = new ArrayList<>();
//                dotsLoaderView.show();
                try{
                    HttpGetRequest getreq = new HttpGetRequest();
                    String result = getreq.execute(myUrl).get();
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArrayResult = jsonObject.getJSONArray("result");
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
                    Log.d("kalosini", "onActivityResult: " + jsonArrayResult.toString());
                    adapter = new ArrayAdapter<>(Parenteral.this, android.R.layout.simple_spinner_item, listAll);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    s.setAdapter(adapter);
//                    dotsLoaderView.hide();
                } catch (InterruptedException | ExecutionException | JSONException e){
                    e.printStackTrace();
                }
            }
            else { }
        }
    }
}
