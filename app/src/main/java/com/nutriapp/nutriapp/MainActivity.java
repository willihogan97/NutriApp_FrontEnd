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
import java.text.DecimalFormat;
import steelkiwi.com.library.DotsLoaderView;

public class MainActivity extends AppCompatActivity {

    private static double standarLLA;
    private static String standarHB;
    private static double multiplierHB;
    private static String JK;

    Button btnnext;
    EditText beratBadanView, tinggiBadanView, llaView, skinFoldView, kkalView, ageView, mlView, karbo, protein, lemak;
    LinearLayout percentage, cairan, totKal, normalKal, bmiLayout, hitunganKalori, btn, llaResult, stressFactor;
    TextView bmi, bmiStatus, llaStatus, totKalori, totCair, llaCount, skinfoldStatus;
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
        ageView = findViewById(R.id.age);
        karbo =  findViewById(R.id.karbohidrat);
        protein =  findViewById(R.id.protein);
        lemak =  findViewById(R.id.lemak);
        bmi =  findViewById(R.id.bmi);
        totKalori =  findViewById(R.id.totalKal);
        totCair =  findViewById(R.id.cairan);
        bmiStatus =  findViewById(R.id.bmiStatus);
        llaCount =  findViewById(R.id.llaCount);
        llaStatus =  findViewById(R.id.llaStatus);
        skinfoldStatus = findViewById(R.id.skinFoldStatus);
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
        final String[] jenisKelamin = getResources().getStringArray(R.array.spinnerJK);
        String[] sktivitas = getResources().getStringArray(R.array.spinnerAktivitas);

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
                    standarHB = "66;13.7;5;6.8";
                    JK= "laki";
                } else {
                    llaResult.setVisibility(View.VISIBLE);
                    standarLLA = 28.5;
                    standarHB = "655;9.6;1.8;4.7";
                    JK = "perempuan";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        skinFoldView.addTextChangedListener(new GenericTextWatcher(skinFoldView));
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
            ageView.addTextChangedListener(new GenericTextWatcher(ageView));
            final Spinner u =  findViewById(R.id.multiplierDropdown);
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, sktivitas);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            u.setAdapter(adapter);

            u.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    switch (u.getSelectedItem().toString()) {
                        case "Extra active (very hard exercise/sports and physical job or 2x training)":
                            multiplierHB = 1.9;
                            break;
                        case "Lightly active (light exercise/sports 1–3 days/week)":
                            multiplierHB = 1.375;
                            break;
                        case "Moderately active (moderate exercise/sports 3–5 days/week)":
                            multiplierHB = 1.55;
                            break;
                        case "Very active (hard exercise/sports 6–7 days a week)":
                            multiplierHB = 1.725;
                            break;
                        default:
                            multiplierHB = 1.2;
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) { }
            });
        }

        mlView.addTextChangedListener(new GenericTextWatcher(mlView));
        karbo.addTextChangedListener(new GenericTextWatcher(karbo));
        protein.addTextChangedListener(new GenericTextWatcher(protein));
        lemak.addTextChangedListener(new GenericTextWatcher(lemak));

        kkalView.addTextChangedListener(new GenericTextWatcher(kkalView));
        ageView.addTextChangedListener(new GenericTextWatcher(ageView));

        Button button = findViewById(R.id.btnNext);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(checkValidity()) {
                    Intent intent = new Intent(getApplicationContext(), Parenteral.class);
                    InfoPribadi infoPribadi = new InfoPribadi(checkNull(beratBadanView.getText().toString()), checkNull(tinggiBadanView.getText().toString()),
                            checkNull(skinFoldView.getText().toString()), checkNull(llaView.getText().toString()), Double.parseDouble(bmi.getText().toString()),
                            checkNull(ageView.getText().toString()), Double.parseDouble(totKalori.getText().toString()),
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
        if(!skinFoldView.getText().toString().equals("")){
            if(!ageView.getText().toString().equals("") && Double.parseDouble(ageView.getText().toString()) < 1) {
                Toast.makeText(getApplicationContext(), "Umur belum diisi", Toast.LENGTH_LONG).show();
                return false;
            } else if(Double.parseDouble(skinFoldView.getText().toString()) < 1) {
                Toast.makeText(getApplicationContext(), "Skin Fold belum terisi dengan benar", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if(!mlView.getText().toString().equals("") && !beratBadanView.getText().toString().equals("")){
            if(Double.parseDouble(mlView.getText().toString()) <= 35 && Double.parseDouble(mlView.getText().toString()) >= 45) {
                Toast.makeText(getApplicationContext(), "ml/hari harus dalam range 35-45", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if(!karbo.getText().toString().equals("") && !protein.getText().toString().equals("") && !lemak.getText().toString().equals("")){
            if((Double.parseDouble(karbo.getText().toString()) + Double.parseDouble(protein.getText().toString()) +
                    Double.parseDouble(lemak.getText().toString())) < 100) {
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

            if(!skinFoldView.getText().toString().equals("") && !ageView.getText().toString().equals("")){
                skinfoldStatus.setText(Double.toString(checkSF(Double.parseDouble(skinFoldView.getText().toString()), JK, Double.parseDouble(ageView.getText().toString()))));
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

            if(!ageView.getText().toString().equals("") && !beratBadanView.getText().toString().equals("") && !tinggiBadanView.getText().toString().equals("")){
                String[] multiplier = MainActivity.standarHB.split(";");
                Double bmr = Double.parseDouble(multiplier[0]) +
                        (Double.parseDouble(beratBadanView.getText().toString()) * Double.parseDouble(multiplier[1])) +
                        (Double.parseDouble(tinggiBadanView.getText().toString()) * Double.parseDouble(multiplier[2])) +
                        (Double.parseDouble(ageView.getText().toString()) * Double.parseDouble(multiplier[3]));
                Double kalori = bmr * MainActivity.multiplierHB;
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

    public double checkSF (double sfValue, String gender, double ageValue){
        if(gender.equals("laki")){
            if(ageValue >= 16 && ageValue <= 29) {
                if (sfValue == 20) {
                    return 8.1;
                } else if (sfValue == 22) {
                    return 9.2;
                } else if (sfValue == 24) {
                    return 10.2;
                } else if (sfValue == 26) {
                    return 11.2;
                } else if (sfValue == 28) {
                    return 12.1;
                } else if (sfValue == 30) {
                    return 12.9;
                } else if (sfValue == 35) {
                    return 14.7;
                } else if (sfValue == 40) {
                    return 16.3;
                } else if (sfValue == 45) {
                    return 17.7;
                } else if (sfValue == 50) {
                    return 19;
                } else if (sfValue == 55) {
                    return 20.2;
                } else if (sfValue == 60) {
                    return 21.2;
                } else if (sfValue == 65) {
                    return 22.2;
                } else if (sfValue == 70) {
                    return 23.2;
                } else if (sfValue == 75) {
                    return 24;
                } else if (sfValue == 80) {
                    return 24.8;
                } else if (sfValue == 85) {
                    return 25.6;
                } else if (sfValue == 90) {
                    return 26.3;
                } else if (sfValue == 95) {
                    return 27;
                } else if (sfValue == 100) {
                    return 27.6;
                } else if (sfValue == 110) {
                    return 28.8;
                } else if (sfValue == 120) {
                    return 29.9;
                } else if (sfValue == 130) {
                    return 31.0;
                } else if (sfValue == 140) {
                    return 31.9;
                } else if (sfValue == 150) {
                    return 32.8;
                } else if (sfValue == 160) {
                    return 33.6;
                } else if (sfValue == 170) {
                    return 34.4;
                } else if (sfValue == 180) {
                    return 35.2;
                } else if (sfValue == 190) {
                    return 35.9;
                } else {
                    return 36.5;
                }
            } else if (ageValue >= 30 && ageValue <= 49){
                if (sfValue == 20) {
                    return 12.1;
                } else if (sfValue == 22) {
                    return 13.2;
                } else if (sfValue == 24) {
                    return 14.2;
                } else if (sfValue == 26) {
                    return 15.2;
                } else if (sfValue == 28) {
                    return 16.1;
                } else if (sfValue == 30) {
                    return 16.9;
                } else if (sfValue == 35) {
                    return 18.7;
                } else if (sfValue == 40) {
                    return 20.3;
                } else if (sfValue == 45) {
                    return 21.8;
                } else if (sfValue == 50) {
                    return 23;
                } else if (sfValue == 55) {
                    return 24.2;
                } else if (sfValue == 60) {
                    return 25.3;
                } else if (sfValue == 65) {
                    return 26.3;
                } else if (sfValue == 70) {
                    return 27.2;
                } else if (sfValue == 75) {
                    return 28;
                } else if (sfValue == 80) {
                    return 28.8;
                } else if (sfValue == 85) {
                    return 29.6;
                } else if (sfValue == 90) {
                    return 30.3;
                } else if (sfValue == 95) {
                    return 31;
                } else if (sfValue == 100) {
                    return 31.7;
                } else if (sfValue == 110) {
                    return 32.9;
                } else if (sfValue == 120) {
                    return 34;
                } else if (sfValue == 130) {
                    return 35;
                } else if (sfValue == 140) {
                    return 36;
                } else if (sfValue == 150) {
                    return 36.8;
                } else if (sfValue == 160) {
                    return 37.7;
                } else if (sfValue == 170) {
                    return 38.5;
                } else if (sfValue == 180) {
                    return 39.2;
                } else if (sfValue == 190) {
                    return 39.9;
                } else {
                    return 40.6;
                }
            } else {
                if (sfValue == 20) {
                    return 12.5;
                } else if (sfValue == 22) {
                    return 13.9;
                } else if (sfValue == 24) {
                    return 15.1;
                } else if (sfValue == 26) {
                    return 16.3;
                } else if (sfValue == 28) {
                    return 17.4;
                } else if (sfValue == 30) {
                    return 18.5;
                } else if (sfValue == 35) {
                    return 10.8;
                } else if (sfValue == 40) {
                    return 22.8;
                } else if (sfValue == 45) {
                    return 24.7;
                } else if (sfValue == 50) {
                    return 26.3;
                } else if (sfValue == 55) {
                    return 27.8;
                } else if (sfValue == 60) {
                    return 29.1;
                } else if (sfValue == 65) {
                    return 30.4;
                } else if (sfValue == 70) {
                    return 31.5;
                } else if (sfValue == 75) {
                    return 32.6;
                } else if (sfValue == 80) {
                    return 33.7;
                } else if (sfValue == 85) {
                    return 34.6;
                } else if (sfValue == 90) {
                    return 35.5;
                } else if (sfValue == 95) {
                    return 36.5;
                } else if (sfValue == 100) {
                    return 37.3;
                } else if (sfValue == 110) {
                    return 38.8;
                } else if (sfValue == 120) {
                    return 40.2;
                } else if (sfValue == 130) {
                    return 41.5;
                } else if (sfValue == 140) {
                    return 42.8;
                } else if (sfValue == 150) {
                    return 43.9;
                } else if (sfValue == 160) {
                    return 45;
                } else if (sfValue == 170) {
                    return 46;
                } else if (sfValue == 180) {
                    return 47;
                } else if (sfValue == 190) {
                    return 47.9;
                } else {
                    return 48.8;
                }
            }
        } else {
            if(ageValue >= 16 && ageValue <= 29) {
                if (sfValue == 14) {
                    return 9.4;
                } else if (sfValue == 16) {
                    return 11.2;
                } else if (sfValue == 18) {
                    return 12.7;
                } else if (sfValue == 20) {
                    return 14.1;
                } else if (sfValue == 22) {
                    return 15.4;
                } else if (sfValue == 24) {
                    return 16.5;
                } else if (sfValue == 26) {
                    return 17.6;
                } else if (sfValue == 28) {
                    return 18.6;
                } else if (sfValue == 30) {
                    return 19.5;
                } else if (sfValue == 35) {
                    return 21.6;
                } else if (sfValue == 40) {
                    return 23.4;
                } else if (sfValue == 45) {
                    return 25;
                } else if (sfValue == 50) {
                    return 26.5;
                } else if (sfValue == 55) {
                    return 27.8;
                } else if (sfValue == 60) {
                    return 29.1;
                } else if (sfValue == 65) {
                    return 30.2;
                } else if (sfValue == 70) {
                    return 31.2;
                } else if (sfValue == 75) {
                    return 32.2;
                } else if (sfValue == 80) {
                    return 33.1;
                } else if (sfValue == 85) {
                    return 34;
                } else if (sfValue == 90) {
                    return 34.8;
                } else if (sfValue == 95) {
                    return 35.6;
                } else if (sfValue == 100) {
                    return 36.3;
                } else if (sfValue == 110) {
                    return 37.7;
                } else if (sfValue == 120) {
                    return 39;
                } else if (sfValue == 130) {
                    return 40.2;
                } else if (sfValue == 140) {
                    return 41.3;
                } else if (sfValue == 150) {
                    return 42.3;
                } else if (sfValue == 160) {
                    return 43.2;
                } else if (sfValue == 170) {
                    return 44.6;
                } else if (sfValue == 180) {
                    return 45;
                } else if (sfValue == 190) {
                    return 45.8;
                } else {
                    return 46.6;
                }
            } else if (ageValue >= 30 && ageValue <= 49){
                if (sfValue == 14) {
                    return 14.1;
                } else if (sfValue == 16) {
                    return 15.7;
                } else if (sfValue == 18) {
                    return 17.1;
                } else if (sfValue == 20) {
                    return 18.4;
                } else if (sfValue == 22) {
                    return 19.5;
                } else if (sfValue == 24) {
                    return 20.6;
                } else if (sfValue == 26) {
                    return 21.5;
                } else if (sfValue == 28) {
                    return 22.4;
                } else if (sfValue == 30) {
                    return 23.3;
                } else if (sfValue == 35) {
                    return 25.2;
                } else if (sfValue == 40) {
                    return 26.8;
                } else if (sfValue == 45) {
                    return 28.3;
                } else if (sfValue == 50) {
                    return 29.6;
                } else if (sfValue == 55) {
                    return 30.8;
                } else if (sfValue == 60) {
                    return 31.9;
                } else if (sfValue == 65) {
                    return 32.9;
                } else if (sfValue == 70) {
                    return 33.9;
                } else if (sfValue == 75) {
                    return 34.7;
                } else if (sfValue == 80) {
                    return 35.6;
                } else if (sfValue == 85) {
                    return 36.3;
                } else if (sfValue == 90) {
                    return 37.1;
                } else if (sfValue == 95) {
                    return 37.8;
                } else if (sfValue == 100) {
                    return 38.5;
                } else if (sfValue == 110) {
                    return 39.7;
                } else if (sfValue == 120) {
                    return 40.8;
                } else if (sfValue == 130) {
                    return 41.9;
                } else if (sfValue == 140) {
                    return 42.9;
                } else if (sfValue == 150) {
                    return 43.8;
                } else if (sfValue == 160) {
                    return 44.7;
                } else if (sfValue == 170) {
                    return 45.5;
                } else if (sfValue == 180) {
                    return 46.2;
                } else if (sfValue == 190) {
                    return 46.9;
                } else {
                    return 47.6;
                }
            } else {
                if (sfValue == 14) {
                    return 17;
                } else if (sfValue == 16) {
                    return 18.6;
                } else if (sfValue == 18) {
                    return 20.1;
                } else if (sfValue == 20) {
                    return 21.4;
                } else if (sfValue == 22) {
                    return 22.6;
                } else if (sfValue == 24) {
                    return 23.7;
                } else if (sfValue == 26) {
                    return 24.8;
                } else if (sfValue == 28) {
                    return 25.7;
                } else if (sfValue == 30) {
                    return 26.6;
                } else if (sfValue == 35) {
                    return 28.6;
                } else if (sfValue == 40) {
                    return 30.3;
                } else if (sfValue == 45) {
                    return 31.9;
                } else if (sfValue == 50) {
                    return 33.2;
                } else if (sfValue == 55) {
                    return 34.6;
                } else if (sfValue == 60) {
                    return 35.7;
                } else if (sfValue == 65) {
                    return 36.7;
                } else if (sfValue == 70) {
                    return 37.7;
                } else if (sfValue == 75) {
                    return 38.6;
                } else if (sfValue == 80) {
                    return 39.5;
                } else if (sfValue == 85) {
                    return 40.4;
                } else if (sfValue == 90) {
                    return 41.1;
                } else if (sfValue == 95) {
                    return 41.9;
                } else if (sfValue == 100) {
                    return 42.6;
                } else if (sfValue == 110) {
                    return 43.9;
                } else if (sfValue == 120) {
                    return 45.1;
                } else if (sfValue == 130) {
                    return 46.2;
                } else if (sfValue == 140) {
                    return 47.3;
                } else if (sfValue == 150) {
                    return 48.2;
                } else if (sfValue == 160) {
                    return 49.1;
                } else if (sfValue == 170) {
                    return 50;
                } else if (sfValue == 180) {
                    return 50.8;
                } else if (sfValue == 190) {
                    return 51.6;
                } else {
                    return 52.3;
                }
            }
        }
    }
}

