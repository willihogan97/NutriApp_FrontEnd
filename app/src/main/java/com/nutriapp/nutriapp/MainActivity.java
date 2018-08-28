package com.nutriapp.nutriapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nutriapp.nutriapp.object.InfoPribadi;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static double standarLLA;
    Button btnnext;
    EditText beratBadanView, tinggiBadanView, llaView, skinFoldView, kkalView, stressFactorView, mlView;
    LinearLayout percentage, cairan, totKal, stressFactor, normalKal, bmiLayout, otherBMI, normalBMI,
            hitunganKalori, btn, llaResult, tipeHitungan;
    TextView bmi, bmiStatus, llaStatus, totKalori, totCair, llaCount;
    public static final String INFO = "INFO_PRIBADI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        percentage = (LinearLayout) findViewById(R.id.percentage);
        cairan = (LinearLayout) findViewById(R.id.c);
        totKal = (LinearLayout) findViewById(R.id.totKal);
        stressFactor = (LinearLayout) findViewById(R.id.stressFactor);
        normalKal = (LinearLayout) findViewById(R.id.normalKal);
        bmiLayout = (LinearLayout) findViewById(R.id.bmiLayout);
        otherBMI = (LinearLayout) findViewById(R.id.otherBMI);
        normalBMI = (LinearLayout) findViewById(R.id.normalBMI);
        hitunganKalori = (LinearLayout) findViewById(R.id.hitunganKalori);
        btn = (LinearLayout) findViewById(R.id.btn);
        llaResult = (LinearLayout) findViewById(R.id.llaResult);
        beratBadanView = (EditText) findViewById(R.id.beratBadan);
        tinggiBadanView = (EditText) findViewById(R.id.tinggiBadan);
        kkalView = (EditText) findViewById(R.id.kalori);
        mlView = (EditText) findViewById(R.id.mlkal);
        llaView = (EditText) findViewById(R.id.lla);
        bmi = (TextView) findViewById(R.id.bmi);
        totKalori = (TextView) findViewById(R.id.totalKal);
        totCair = (TextView) findViewById(R.id.cairan);
        bmiStatus = (TextView) findViewById(R.id.bmiStatus);
        llaCount = (TextView) findViewById(R.id.llaCount);
        llaStatus = (TextView) findViewById(R.id.llaStatus);

        percentage.setVisibility(View.GONE);
        cairan.setVisibility(View.GONE);
        totKal.setVisibility(View.GONE);
        stressFactor.setVisibility(View.GONE);
        normalKal.setVisibility(View.GONE);
        bmiLayout.setVisibility(View.GONE);
        otherBMI.setVisibility(View.GONE);
        normalBMI.setVisibility(View.GONE);
        btn.setVisibility(View.GONE);
        hitunganKalori.setVisibility(View.GONE);
        llaResult.setVisibility(View.GONE);

        String[] tipeBMI = getResources().getStringArray(R.array.spinnerBMI);
        String[] tipeHitunganKalori = getResources().getStringArray(R.array.spinnerHitunganKalori);
        String[] jenisKelamin = getResources().getStringArray(R.array.spinnerJK);

        final Spinner s = (Spinner) findViewById(R.id.bmidropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tipeBMI);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(s.getSelectedItem().toString().equals("Berat - Tinggi Badan")) {
                    normalBMI.setVisibility(View.VISIBLE);
                    otherBMI.setVisibility(View.GONE);
                } else {
                    otherBMI.setVisibility(View.VISIBLE);
                    normalBMI.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        beratBadanView.addTextChangedListener(new GenericTextWatcher(beratBadanView));
        tinggiBadanView.addTextChangedListener(new GenericTextWatcher(tinggiBadanView));

        final Spinner t = (Spinner) findViewById(R.id.jkdropdown);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, jenisKelamin);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        t.setAdapter(adapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(t.getSelectedItem().toString().equals("Laki-Laki")) {
                    llaResult.setVisibility(View.VISIBLE);
                    standarLLA = 29.3;
                } else {
                    llaResult.setVisibility(View.VISIBLE);
                    standarLLA = 28.5;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        llaView.addTextChangedListener(new GenericTextWatcher(llaView));

        final Spinner r = (Spinner) findViewById(R.id.hitunganKaloriDropdown);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tipeHitunganKalori);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        r.setAdapter(adapter);

        r.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(r.getSelectedItem().toString().equals("Normal")) {
                    normalKal.setVisibility(View.VISIBLE);
                    stressFactor.setVisibility(View.GONE);
                } else {
                    stressFactor.setVisibility(View.VISIBLE);
                    normalKal.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        kkalView.addTextChangedListener(new GenericTextWatcher(kkalView));
        mlView.addTextChangedListener(new GenericTextWatcher(mlView));

        Button button = findViewById(R.id.btnNext);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), Parenteral.class);
            InfoPribadi infoPribadi = new InfoPribadi(1,1,"a","a","a","a","a","a","100","12","a","a","a");
            intent.putExtra(INFO, infoPribadi);
            startActivityForResult(intent, 200);
            }
        });
    }

    private class GenericTextWatcher implements TextWatcher{

        private View view;
        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        public void afterTextChanged(Editable editable1) {
            String bb = editable1.toString();

            if(!beratBadanView.getText().toString().equals("") && !tinggiBadanView.getText().toString().equals("")) {
                //lakukan itung
                bmiLayout.setVisibility(View.VISIBLE);
                DecimalFormat dec = new DecimalFormat("#.0");
                double countBMI = Double.parseDouble(beratBadanView.getText().toString()) / Math.pow(Double.parseDouble(tinggiBadanView.getText().toString())/100, 2);
                bmi.setText(dec.format(countBMI));
                setRightLabelBMI(countBMI);
                hitunganKalori.setVisibility(View.VISIBLE);
            }

            if(!llaView.getText().toString().equals("")){
                Double lla = (Double.parseDouble(llaView.getText().toString()) / MainActivity.standarLLA) * 100;
                llaCount.setText(Double.toString(lla));
                setRightLabelLLA(lla);
                hitunganKalori.setVisibility(View.VISIBLE);
            }

            if(!kkalView.getText().toString().equals("") && !beratBadanView.getText().toString().equals("")){

                Double kalori = Double.parseDouble(beratBadanView.getText().toString()) * Double.parseDouble(kkalView.getText().toString());
                totKalori.setText(Double.toString(kalori));
                cairan.setVisibility(View.VISIBLE);
                totKal.setVisibility(View.VISIBLE);
            }

            Log.d("mlview", "afterTextChanged: " + mlView.getText().toString());
            Log.d("beratView", "afterTextChanged: " + beratBadanView.getText().toString());
            if(!mlView.getText().toString().equals("") && !beratBadanView.getText().toString().equals("")){
                double cair = Double.parseDouble(beratBadanView.getText().toString()) * Double.parseDouble(mlView.getText().toString());
                totCair.setText(Double.toString(cair));
                btn.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setRightLabelBMI (double bmiResult){
        if(bmiResult < 18.5){
            bmiStatus.setText("Underweight");
        } else if(18.5 <= bmiResult && bmiResult <= 24.9){
            bmiStatus.setText("Healthy");
        } else if(25.0 <= bmiResult && bmiResult <= 29.9){
            bmiStatus.setText("Overweight");
        } else {
            bmiStatus.setText("Obese");
        }
    }

    public void setRightLabelLLA (double llaResult){
        if(llaResult < 90){
            bmiStatus.setText("Underweight");
        } else if(90 <= llaResult && llaResult <= 110){
            bmiStatus.setText("Healthy");
        } else if(110 <= llaResult && llaResult <= 120){
            bmiStatus.setText("Overweight");
        } else {
            bmiStatus.setText("Obese");
        }
    }
}

