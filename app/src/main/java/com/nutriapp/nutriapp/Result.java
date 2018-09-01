package com.nutriapp.nutriapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nutriapp.nutriapp.object.InfoPribadi;
import com.nutriapp.nutriapp.object.MakananExternal;
import com.nutriapp.nutriapp.object.Parenteral;
import com.nutriapp.nutriapp.object.TotalMakananExternal;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

@SuppressLint("Registered")
public class Result extends AppCompatActivity {

    InfoPribadi infoPribadi;
    Parenteral parenteral;
    TotalMakananExternal makananExternal;

    TextView parenteralVolume, parenteralCarbohydrate, parenteralProtein, parenteralFat, parenteralElectrolite, parenteralCalories, parenteralTotal;
    TextView oralVolume, oralCarbohydrate, oralProtein, oralFat, oralElectrolite, oralCalories, oralTotal;
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
        Log.d("info", "onCreate: " + infoPribadi.toString());
        Log.d("parenteral", "onCreate: " + parenteral.toString());
        Log.d("makanan", "onCreate: " + makananExternal.toString());

        parenteralVolume = (TextView) findViewById(R.id.parenteralVolume);
        parenteralCarbohydrate = (TextView) findViewById(R.id.parenteralCarbohydrate);
        parenteralProtein = (TextView) findViewById(R.id.parenteralProtein);
        parenteralFat = (TextView) findViewById(R.id.parenteralFat);
        parenteralElectrolite = (TextView) findViewById(R.id.parenteralElectrolite);
        parenteralCalories = (TextView) findViewById(R.id.parenteralCalories);
        parenteralTotal = (TextView) findViewById(R.id.parenteralTotal);
        oralVolume = (TextView) findViewById(R.id.oralVolume);
        oralCarbohydrate = (TextView) findViewById(R.id.oralCarbohydrate);
        oralProtein = (TextView) findViewById(R.id.oralProtein);
        oralFat = (TextView) findViewById(R.id.oralFat);
        oralElectrolite = (TextView) findViewById(R.id.oralElectrolite);
        oralCalories = (TextView) findViewById(R.id.oralCalories);
        oralTotal = (TextView) findViewById(R.id.oralTotal);

        parenteralVolume.setText(dec.format(parenteral.getVolume()));
        parenteralCarbohydrate.setText(dec.format(parenteral.getCarbohydrate()));
        parenteralProtein.setText(dec.format(parenteral.getProtein()));
        parenteralFat.setText(dec.format(parenteral.getFat()));
        parenteralElectrolite.setText(dec.format(parenteral.getElectrolite()));
        parenteralCalories.setText(dec.format(parenteral.getCalories()));

        oralCarbohydrate.setText(dec.format(makananExternal.getTotalKarbo()));
        oralProtein.setText(dec.format(makananExternal.getTotalProtein()));
        oralFat.setText(dec.format(makananExternal.getTotalLemak()));
        oralCalories.setText(dec.format(makananExternal.getTotalKalori()));


        Button buttonCreateNew = findViewById(R.id.buttonCreateNew);
        buttonCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}