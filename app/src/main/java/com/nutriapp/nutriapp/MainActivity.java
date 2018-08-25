package com.nutriapp.nutriapp;

import android.content.Intent;
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

import com.nutriapp.nutriapp.object.InfoPribadi;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    Button btnnext;
    EditText beratBadanView, tinggiBadanView, llaView, skinFoldView, kkalView, stressFactorView, mlView;
    LinearLayout percentage, cairan, totKal, stressFactor, normalKal, bmiLayout, otherBMI, normalBMI, hitunganKalori, btn, tipeHitungan;
    TextView bmi, bmiStatus, llastatus, totKalori, totCair;
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
        beratBadanView = (EditText) findViewById(R.id.beratBadan);
        tinggiBadanView = (EditText) findViewById(R.id.tinggiBadan);
        kkalView = (EditText) findViewById(R.id.kalori);
        btn = (LinearLayout) findViewById(R.id.btn);
        mlView = (EditText) findViewById(R.id.mlkal);
        bmi = (TextView) findViewById(R.id.bmi);
        totKalori = (TextView) findViewById(R.id.totalKal);
        totCair = (TextView) findViewById(R.id.cairan);
        bmiStatus = (TextView) findViewById(R.id.bmiStatus);


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

        String[] tipeBMI = getResources().getStringArray(R.array.spinnerBMI);
        String[] tipeHitunganKalori = getResources().getStringArray(R.array.spinnerHitunganKalori);

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

//        if(!(llaView.getText().toString().equals("") && skinFoldView.getText().toString().equals(""))){
//            bmiLayout.setVisibility(View.VISIBLE);
//            double countBMI = Double.parseDouble(beratBadanView.getText().toString()) / Math.pow(Double.parseDouble(tinggiBadanView.getText().toString()), 2);
//            bmi.setText(Double.toString(countBMI));
//
//            if(countBMI < 18.5){
//                bmiStatus.setText("Underweight");
//            } else if(18.5 <= countBMI && countBMI <= 24.9){
//                bmiStatus.setText("Healthy");
//            } else if(25.0 <= countBMI && countBMI <= 29.9){
//                bmiStatus.setText("Overweight");
//            } else {
//                bmiStatus.setText("Obese");
//            }
//
//            hitunganKalori.setVisibility(View.VISIBLE);
//        }

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
//                int beratBadan = Integer.parseInt(beratBadanView.getText().toString());
//                int tinggiBadan = Integer.parseInt(tinggiBadanView.getText().toString());
//                String bmi = (beratBadan * tinggiBadan) + "";
//                Toast.makeText(getApplicationContext(), bmi, Toast.LENGTH_SHORT).show();
//                bmiView.setText(bmi);

                Intent intent = new Intent(getApplicationContext(), Parenteral.class);
                InfoPribadi infoPribadi = new InfoPribadi(1,1,"a","a","a","a","a","a","a","a","a","a","a");
                intent.putExtra(INFO, infoPribadi);
                startActivityForResult(intent, 200);
//                startActivity(intent);
//                startActivity(new Intent(getApplicationContext(), MakananExternalActivity.class));
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
}

