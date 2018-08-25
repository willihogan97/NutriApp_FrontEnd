package com.nutriapp.nutriapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.nutriapp.nutriapp.object.InfoPribadi;
import com.nutriapp.nutriapp.object.JadwalMakananExternal;
import com.nutriapp.nutriapp.object.MakananExternal;

@SuppressLint("Registered")
public class Parenteral extends AppCompatActivity {

    Button next;
    EditText volumeView;
    LinearLayout selection, volume, detail;
    InfoPribadi infoPribadi;

    public static final String INFO = "INFO_PRIBADI";
    public static final String PARENTERAL = "PARENTERAL";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parenteral);

        selection = (LinearLayout) findViewById(R.id.selection);
        volume = (LinearLayout) findViewById(R.id.volume);
        detail = (LinearLayout) findViewById(R.id.detail);

        volume.setVisibility(View.INVISIBLE);
        selection.setVisibility(View.VISIBLE);
        detail.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        infoPribadi = intent.getParcelableExtra(MainActivity.INFO);

        String[] arraySpinner = getResources().getStringArray(R.array.spinnerTipeMakananExternal);
        final Spinner s = (Spinner) findViewById(R.id.parenteralSelection);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(s.getSelectedItem().toString() != "Biasa") {
                    volume.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        volumeView = (EditText) findViewById(R.id.editVolume);
        volumeView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable s) {
                detail.setVisibility(View.VISIBLE);
            }
        });

        next = findViewById(R.id.buttonNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MakananExternalActivity.class);
                intent.putExtra(INFO, infoPribadi);
                com.nutriapp.nutriapp.object.Parenteral parenteral = new com.nutriapp.nutriapp.object.Parenteral(s.getSelectedItem().toString(), volumeView.getText().toString());
                intent.putExtra(PARENTERAL, parenteral);
                startActivityForResult(intent, 200);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200) {

            if(resultCode == Activity.RESULT_OK) {

            } else {
                // AnotherActivity was not successful. No data to retrieve.
            }
        }
    }
}
