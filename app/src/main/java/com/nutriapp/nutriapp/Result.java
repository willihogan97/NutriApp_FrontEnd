package com.nutriapp.nutriapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.nutriapp.nutriapp.object.InfoPribadi;
import com.nutriapp.nutriapp.object.MakananExternal;
import com.nutriapp.nutriapp.object.Parenteral;
import com.nutriapp.nutriapp.object.TotalMakananExternal;

@SuppressLint("Registered")
public class Result extends AppCompatActivity {

    InfoPribadi infoPribadi;
    Parenteral parenteral;
    TotalMakananExternal makananExternal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        Intent intent = getIntent();
        parenteral = intent.getParcelableExtra(com.nutriapp.nutriapp.Parenteral.PARENTERAL);
        infoPribadi = intent.getParcelableExtra(MainActivity.INFO);
        makananExternal = intent.getParcelableExtra(MakananExternalActivity.MAKANANEXTERNAL);
        Log.d("info", "onCreate: " + infoPribadi.toString());
        Log.d("parenteral", "onCreate: " + parenteral.toString());
        Log.d("makanan", "onCreate: " + makananExternal.toString());

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