package com.nutriapp.nutriapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

import com.google.gson.Gson;
import com.nutriapp.nutriapp.object.InfoPribadi;
import com.nutriapp.nutriapp.object.MakananExternal;

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
        skinFoldView = findViewById(R.id.SkinFold);

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

        kkalView.addTextChangedListener(new GenericTextWatcher(kkalView));
        stressFactorView.addTextChangedListener(new GenericTextWatcher(stressFactorView));
        multiplier.addTextChangedListener(new GenericTextWatcher(multiplier));

        Button button = findViewById(R.id.btnNext);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(checkValidity()) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setTitle(R.string.app_name);
//                    builder.setMessage("Apa anda yakin untuk menyimpan makanan baru ini ?");
//                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.dismiss();
//                            Intent intent = new Intent(getApplicationContext(), Parenteral.class);
//                            InfoPribadi infoPribadi = new InfoPribadi(checkNull(beratBadanView.getText().toString()), checkNull(tinggiBadanView.getText().toString()),
//                                    checkNull(skinFoldView.getText().toString()), checkNull(llaView.getText().toString()), Double.parseDouble(bmi.getText().toString()),
//                                    checkNull(stressFactorView.getText().toString()), Double.parseDouble(totKalori.getText().toString()),
//                                    Double.parseDouble(totCair.getText().toString()), checkNull(karbo.getText().toString()), checkNull(protein.getText().toString()),  checkNull(lemak.getText().toString()));
//                            intent.putExtra(INFO, infoPribadi);
//                            startActivityForResult(intent, 200);
//                        }
//                    });
//                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.dismiss();
//                        }
//                    });
//                    AlertDialog alert = builder.create();
//                    alert.show();

                    Intent intent = new Intent(getApplicationContext(), Parenteral.class);
                    InfoPribadi infoPribadi = new InfoPribadi(checkNull(beratBadanView.getText().toString()), checkNull(tinggiBadanView.getText().toString()),
                            checkNull(skinFoldView.getText().toString()), checkNull(llaView.getText().toString()), Double.parseDouble(bmi.getText().toString()),
                            checkNull(stressFactorView.getText().toString()), Double.parseDouble(totKalori.getText().toString()),
                            Double.parseDouble(totCair.getText().toString()), checkNull(karbo.getText().toString()), checkNull(protein.getText().toString()),  checkNull(lemak.getText().toString()));
                    intent.putExtra(INFO, infoPribadi);
                    startActivityForResult(intent, 200);
                }
            }
        });
    }

    private boolean checkValidity() {
        if(!beratBadanView.getText().toString().equals("") && !tinggiBadanView.getText().toString().equals("")) {
            if (Double.parseDouble(beratBadanView.getText().toString()) < 1 | Double.parseDouble(tinggiBadanView.getText().toString()) < 1) {
                Toast.makeText(getApplicationContext(), "Berat badan / tinggi badan tidak sesuai", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        if(!kkalView.getText().toString().equals("") && !beratBadanView.getText().toString().equals("")){
            if(Double.parseDouble(llaView.getText().toString()) < 1) {
                Toast.makeText(getApplicationContext(), "LLA tidak sesuai", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if(!kkalView.getText().toString().equals("") && !beratBadanView.getText().toString().equals("")){
            if(Double.parseDouble(kkalView.getText().toString()) <= 20 && Double.parseDouble(kkalView.getText().toString()) >= 35) {
                Toast.makeText(getApplicationContext(), "Kkal harus dalam range 20-35", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        if(!stressFactorView.getText().toString().equals("") && !multiplier.getText().toString().equals("")){
            if(Double.parseDouble(stressFactorView.getText().toString()) <= 20 && Double.parseDouble(multiplier.getText().toString()) >= 35) {
                Toast.makeText(getApplicationContext(), "Stress factor dan pengali tidak sesuai", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if(!mlView.getText().toString().equals("") && !beratBadanView.getText().toString().equals("")){
            if(Double.parseDouble(mlView.getText().toString()) >= 35 && Double.parseDouble(mlView.getText().toString()) <= 45) {
                Toast.makeText(getApplicationContext(), "ml/hari harus dalam range 35-45", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if(!karbo.getText().toString().equals("") && !protein.getText().toString().equals("") && !lemak.getText().toString().equals("")){
            if((Double.parseDouble(karbo.getText().toString()) + Double.parseDouble(protein.getText().toString()) +
                    Double.parseDouble(lemak.getText().toString())) <= 100) {
                Toast.makeText(getApplicationContext(), "Belum semua kalori terpakai untuk karbohidrat, protein, dan lemak", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
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
                double countBMI = Double.parseDouble(beratBadanView.getText().toString()) / Math.pow(Double.parseDouble(tinggiBadanView.getText().toString()) / 100, 2);
                bmi.setText(dec.format(countBMI));
                setRightLabelBMI(countBMI);
                hitunganKalori.setVisibility(View.VISIBLE);
            }

            if(!llaView.getText().toString().equals("")){
                Double lla = Double.parseDouble(llaView.getText().toString()) * 100 / MainActivity.standarLLA;
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
                Double kalori = Double.parseDouble(stressFactorView.getText().toString()) * Double.parseDouble(multiplier.getText().toString());
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

    public double checkNull (String string){
        Log.d("check",string);
        if(string.equals("")){
            Log.d("MyApp","I am");
            return 0;
        } else {
            Log.d("MyApp","I am here");
            return Double.parseDouble(string);
        }
    }
}

