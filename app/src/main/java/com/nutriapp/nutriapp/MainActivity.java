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

import com.nutriapp.nutriapp.object.InfoPribadi;

import java.text.DecimalFormat;

import steelkiwi.com.library.DotsLoaderView;

public class MainActivity extends AppCompatActivity {

    private static double standarLLA;
    Button btnnext;
    EditText beratBadanView, tinggiBadanView, llaView, skinFoldView, kkalView, stressFactorView, multiplier, mlView, karbo, protein, lemak;
    LinearLayout percentage, cairan, totKal, normalKal, bmiLayout, hitunganKalori, btn, llaResult, stressFactor;
    TextView bmi, bmiStatus, llaStatus, totKalori, totCair, llaCount;
    public static final String INFO = "INFO_PRIBADI";
    DotsLoaderView dotsLoaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        percentage = findViewById(R.id.percentage);
        cairan = findViewById(R.id.c);
        totKal = findViewById(R.id.totKal);
        normalKal = findViewById(R.id.normalKal);
        bmiLayout = findViewById(R.id.bmiLayout);
        hitunganKalori = findViewById(R.id.hitunganKalori);
        btn = findViewById(R.id.btn);
        stressFactor = findViewById(R.id.stressFactor);
        llaResult = findViewById(R.id.llaResult);
        beratBadanView = findViewById(R.id.beratBadan);
        tinggiBadanView = findViewById(R.id.tinggiBadan);
        kkalView = findViewById(R.id.kalori);
        mlView =  findViewById(R.id.mlkal);
        llaView =  findViewById(R.id.lla);
        stressFactorView = findViewById(R.id.sheerFac);
        multiplier = findViewById(R.id.multiply);
        karbo =  findViewById(R.id.karbohidrat);
        protein =  findViewById(R.id.protein);
        lemak =  findViewById(R.id.lemak);
        bmi =  findViewById(R.id.bmi);
        totKalori =  findViewById(R.id.totalKal);
        totCair =  findViewById(R.id.cairan);
        bmiStatus =  findViewById(R.id.bmiStatus);
        llaCount =  findViewById(R.id.llaCount);
        llaStatus =  findViewById(R.id.llaStatus);
        dotsLoaderView = findViewById(R.id.loader);

        percentage.setVisibility(View.GONE);
        cairan.setVisibility(View.GONE);
        totKal.setVisibility(View.GONE);
        normalKal.setVisibility(View.GONE);
        bmiLayout.setVisibility(View.GONE);
        btn.setVisibility(View.GONE);
        stressFactor.setVisibility(View.GONE);
        hitunganKalori.setVisibility(View.GONE);
        llaResult.setVisibility(View.GONE);

        String[] tipeHitunganKalori = getResources().getStringArray(R.array.spinnerHitunganKalori);
        String[] jenisKelamin = getResources().getStringArray(R.array.spinnerJK);

        beratBadanView.addTextChangedListener(new GenericTextWatcher(beratBadanView));
        tinggiBadanView.addTextChangedListener(new GenericTextWatcher(tinggiBadanView));

        final Spinner t = findViewById(R.id.jkdropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, jenisKelamin);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        t.setAdapter(adapter);

        t.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        final Spinner r =  findViewById(R.id.hitunganKaloriDropdown);
        adapter = new ArrayAdapter<>(this,
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

        if(normalKal.getVisibility() == View.VISIBLE) {
            kkalView.addTextChangedListener(new GenericTextWatcher(kkalView));
        }

        if(stressFactor.getVisibility() == View.VISIBLE){
            stressFactorView.addTextChangedListener(new GenericTextWatcher(stressFactorView));
            multiplier.addTextChangedListener(new GenericTextWatcher(multiplier));
        }
        mlView.addTextChangedListener(new GenericTextWatcher(mlView));
        karbo.addTextChangedListener(new GenericTextWatcher(karbo));
        protein.addTextChangedListener(new GenericTextWatcher(protein));
        lemak.addTextChangedListener(new GenericTextWatcher(lemak));

        Button button = findViewById(R.id.btnNext);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), Parenteral.class);
            InfoPribadi infoPribadi = new InfoPribadi(0, 0, 0, 0, 0, 0, Double.parseDouble(totKalori.getText().toString()), Double.parseDouble(totCair.getText().toString()), 0, 0, 0);

//            InfoPribadi infoPribadi = new InfoPribadi(checkNull(beratBadanView), checkNull(tinggiBadanView),
//                    checkNull(skinFoldView), checkNull(llaView), Double.parseDouble(bmi.getText().toString()),
//                    checkNull(stressFactorView), Double.parseDouble(totKalori.getText().toString()),
//                    Double.parseDouble(totCair.getText().toString()), checkNull(karbo), checkNull(protein),  checkNull(lemak));
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
            DecimalFormat dec = new DecimalFormat("#.0");
            if(!beratBadanView.getText().toString().equals("") && !tinggiBadanView.getText().toString().equals("")) {
                //lakukan itung
                bmiLayout.setVisibility(View.VISIBLE);
                double countBMI = Double.parseDouble(beratBadanView.getText().toString()) / Math.pow(Double.parseDouble(tinggiBadanView.getText().toString())/100, 2);
                bmi.setText(dec.format(countBMI));
                setRightLabelBMI(countBMI);
                hitunganKalori.setVisibility(View.VISIBLE);
            }

            if(!llaView.getText().toString().equals("")){
                Double lla = Double.parseDouble(llaView.getText().toString()) * 100 / MainActivity.standarLLA ;
                llaCount.setText(dec.format(lla));
                setRightLabelLLA(lla);
                hitunganKalori.setVisibility(View.VISIBLE);
            }

            if(!kkalView.getText().toString().equals("") && !beratBadanView.getText().toString().equals("")){
                Double kalori = Double.parseDouble(beratBadanView.getText().toString()) * Double.parseDouble(kkalView.getText().toString());
                totKalori.setText(Double.toString(kalori));
                cairan.setVisibility(View.VISIBLE);
                totKal.setVisibility(View.VISIBLE);
            }

            if(!stressFactorView.getText().toString().equals("") && !multiplier.getText().toString().equals("")){
                Double kalori = Double.parseDouble(beratBadanView.getText().toString()) * Double.parseDouble(kkalView.getText().toString());
                totKalori.setText(Double.toString(kalori));
                cairan.setVisibility(View.VISIBLE);
                totKal.setVisibility(View.VISIBLE);
            }

            if(!mlView.getText().toString().equals("") && !beratBadanView.getText().toString().equals("")){
                double cair = Double.parseDouble(beratBadanView.getText().toString()) * Double.parseDouble(mlView.getText().toString());
                totCair.setText(Double.toString(cair));
                percentage.setVisibility(View.VISIBLE);
            }

            if(!karbo.getText().toString().equals("") && !protein.getText().toString().equals("") && !lemak.getText().toString().equals("")){
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
            llaStatus.setText("Underweight");
        } else if(90 <= llaResult && llaResult <= 110){
            llaStatus.setText("Healthy");
        } else if(110 <= llaResult && llaResult <= 120){
            llaStatus.setText("Overweight");
        } else {
            llaStatus.setText("Obese");
        }
    }

    public double checkNull (EditText string){
        Log.d("check",string.getText().toString());
        if(string.getText().toString().matches("")){
            Log.d("MyApp","I am");
            return 0;
        } else {
            Log.d("MyApp","I am here");
            return Double.parseDouble(string.getText().toString());
        }
    }
}

