package com.nutriapp.nutriapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button btnnext;
    EditText beratBadanView, tinggiBadanView, llaView, skinFoldView, kkalView, stressFactorView, mlView;
    LinearLayout percentage, cairan, totKal, stressFactor, normalKal, bmiLayout, otherBMI, normalBMI, hitunganKalori;
    TextView bmi, bmiStatus, llastatus, totKalori, totCair;

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

        percentage.setVisibility(View.INVISIBLE);
        cairan.setVisibility(View.INVISIBLE);
        totKal.setVisibility(View.INVISIBLE);
        stressFactor.setVisibility(View.INVISIBLE);
        normalKal.setVisibility(View.INVISIBLE);
        bmiLayout.setVisibility(View.INVISIBLE);
        otherBMI.setVisibility(View.INVISIBLE);
        normalBMI.setVisibility(View.INVISIBLE);

        String[] tipeBMI = getResources().getStringArray(R.array.spinnerBMI);
        String[] tipeHitunganKalori = getResources().getStringArray(R.array.spinnerBMI);

        final Spinner s = (Spinner) findViewById(R.id.bmidropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tipeBMI);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(!s.getSelectedItem().toString().equals("Berat - Tinggi Badan")) {
                    normalBMI.setVisibility(View.VISIBLE);
                } else {
                    otherBMI.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        if(!(beratBadanView.getText().toString().equals("") && tinggiBadanView.getText().toString().equals(""))){
            bmiLayout.setVisibility(View.VISIBLE);
            double countBMI = Double.parseDouble(beratBadanView.getText().toString()) / Math.pow(Double.parseDouble(tinggiBadanView.getText().toString()), 2);
            bmi.setText(Double.toString(countBMI));

            if(countBMI < 18.5){
                bmiStatus.setText("Underweight");
            } else if(18.5 <= countBMI && countBMI <= 24.9){
                bmiStatus.setText("Healthy");
            } else if(25.0 <= countBMI && countBMI <= 29.9){
                bmiStatus.setText("Overweight");
            } else {
                bmiStatus.setText("Obese");
            }
        }

        if(!(llaView.getText().toString().equals("") && skinFoldView.getText().toString().equals(""))){
            bmiLayout.setVisibility(View.VISIBLE);
            double countBMI = Double.parseDouble(beratBadanView.getText().toString()) / Math.pow(Double.parseDouble(tinggiBadanView.getText().toString()), 2);
            bmi.setText(Double.toString(countBMI));

            if(countBMI < 18.5){
                bmiStatus.setText("Underweight");
            } else if(18.5 <= countBMI && countBMI <= 24.9){
                bmiStatus.setText("Healthy");
            } else if(25.0 <= countBMI && countBMI <= 29.9){
                bmiStatus.setText("Overweight");
            } else {
                bmiStatus.setText("Obese");
            }

            hitunganKalori.setVisibility(View.VISIBLE);
        }

        final Spinner r = (Spinner) findViewById(R.id.hitunganKaloriDropdown);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tipeHitunganKalori);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        r.setAdapter(adapter);

        r.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(!s.getSelectedItem().toString().equals("Normal")) {
                    normalKal.setVisibility(View.VISIBLE);
                } else {
                    stressFactor.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        if(!kkalView.getText().toString().equals("")){
            totKal.setVisibility(View.VISIBLE);
            Double kalori = Double.parseDouble(beratBadanView.getText().toString()) * Double.parseDouble(kkalView.getText().toString());
            totKalori.setText(Double.toString(kalori));
        }

        if(totKal.getVisibility() == View.VISIBLE){
            cairan.setVisibility(View.VISIBLE);
            double cair = Double.parseDouble(beratBadanView.getText().toString()) * Double.parseDouble(mlView.getText().toString());
            totCair.setText(Double.toString(cair));
        }

//        beratBadanView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        beratBadanView = (EditText) findViewById(R.id.beratBadan);
//        tinggiBadanView = (EditText) findViewById(R.id.tinggiBadan);
//        bmiView = (EditText) findViewById(R.id.bmi);

        Button button = findViewById(R.id.btnNext);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                int beratBadan = Integer.parseInt(beratBadanView.getText().toString());
//                int tinggiBadan = Integer.parseInt(tinggiBadanView.getText().toString());
//                String bmi = (beratBadan * tinggiBadan) + "";
//                Toast.makeText(getApplicationContext(), bmi, Toast.LENGTH_SHORT).show();
//                bmiView.setText(bmi);

                Intent intent = new Intent(getApplicationContext(), Parenteral.class);
                intent.putExtra("asd", "asdasd");
                startActivityForResult(intent, 200);
//                startActivity(intent);
//                startActivity(new Intent(getApplicationContext(), MakananExternalActivity.class));
            }
        });
    }




}
