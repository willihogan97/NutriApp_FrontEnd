package com.nutriapp.nutriapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nutriapp.nutriapp.object.InfoPribadi;
import com.nutriapp.nutriapp.object.Parenteral;
import com.nutriapp.nutriapp.object.TotalMakananExternal;

import java.text.DecimalFormat;

@SuppressLint("Registered")
public class Result extends AppCompatActivity {

    InfoPribadi infoPribadi;
    Parenteral parenteral;
    TotalMakananExternal makananExternal;
    double totalVolumeOral;

    TextView parenteralVolume, parenteralCarbohydrate, parenteralProtein, parenteralFat, parenteralElectrolite, parenteralCalories;
    TextView oralVolume, oralCarbohydrate, oralProtein, oralFat, oralElectrolite, oralCalories;
    DecimalFormat dec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        dec = new DecimalFormat("#.0");

        Intent intent = getIntent();
        parenteral = intent.getParcelableExtra(com.nutriapp.nutriapp.Parenteral.PARENTERAL);
        infoPribadi = intent.getParcelableExtra(MainActivity.INFO);
        makananExternal = intent.getParcelableExtra(MakananExternalActivity.MAKANANEXTERNAL);
        totalVolumeOral = intent.getDoubleExtra(ResultParenteral.VOLUMEORAL, 0);

        parenteralVolume = findViewById(R.id.parenteralVolume);
        parenteralCarbohydrate = findViewById(R.id.parenteralCarbohydrate);
        parenteralProtein = findViewById(R.id.parenteralProtein);
        parenteralFat = findViewById(R.id.parenteralFat);
        parenteralElectrolite = findViewById(R.id.parenteralElectrolite);
        parenteralCalories = findViewById(R.id.parenteralCalories);
        oralVolume = findViewById(R.id.oralVolume);
        oralCarbohydrate = findViewById(R.id.oralCarbohydrate);
        oralProtein = findViewById(R.id.oralProtein);
        oralFat = findViewById(R.id.oralFat);
        oralElectrolite = findViewById(R.id.oralElectrolite);
        oralCalories = findViewById(R.id.oralCalories);

        parenteralVolume.setText(dec.format(parenteral.getVolume()));
        parenteralCarbohydrate.setText(dec.format(parenteral.getCarbohydrate()));
        parenteralProtein.setText(dec.format(parenteral.getProtein()));
        parenteralFat.setText(dec.format(parenteral.getFat()));
        parenteralElectrolite.setText(dec.format(parenteral.getElectrolite()));
        parenteralCalories.setText(dec.format(parenteral.getCalories()));

        oralCarbohydrate.setText(dec.format(Double.parseDouble(makananExternal.getTotalKarbo())));
        oralProtein.setText(dec.format(Double.parseDouble(makananExternal.getTotalProtein())));
        oralFat.setText(dec.format(Double.parseDouble(makananExternal.getTotalLemak())));
        oralCalories.setText(dec.format(Double.parseDouble(makananExternal.getTotalKalori())));
        oralElectrolite.setText("0.0");
        oralVolume.setText(dec.format(totalVolumeOral));


        Button buttonCreateNew = findViewById(R.id.buttonCreateNew);
        buttonCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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

}