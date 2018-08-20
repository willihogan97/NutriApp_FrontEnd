package com.nutriapp.nutriapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nutriapp.nutriapp.object.JadwalMakananExternal;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MakananExternalActivity extends AppCompatActivity{

    private LinearLayout parentLinearLayout;
    public static Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makanan_external);

        //For Spinner
        spinner = (Spinner)findViewById(R.id.spinnerTipeMakananExternal);
        String[] countries = getResources().getStringArray(R.array.spinnerTipeMakananExternal);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout, countries);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.d("item yang dipilih", spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

//        getSupportActionBar().setHomeButtonEnabled(true);


        parentLinearLayout = (LinearLayout) findViewById(R.id.listMakanan);
        Intent intent = getIntent();
        String bmi = intent.getStringExtra("asd");
        Toast.makeText(getApplicationContext(), bmi, Toast.LENGTH_SHORT).show();

        Button button = findViewById(R.id.btnPlus);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("asd", "onClick: " );
                Intent intent = new Intent(getApplicationContext(), TambahJadwalExternal.class);

                startActivityForResult(intent, 200);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200) {

            if(resultCode == Activity.RESULT_OK) {
                JadwalMakananExternal result = data.getParcelableExtra(TambahJadwalExternal.EXTRA_DATA);

                // Tambah child di UI
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.makanan_external_row, null);
                TextView jam = (TextView) rowView.findViewById(R.id.jam);
                TextView karbo = (TextView) rowView.findViewById(R.id.karbo);
                TextView protein = (TextView) rowView.findViewById(R.id.protein);
                TextView lemak = (TextView) rowView.findViewById(R.id.lemak);
                TextView kalori = (TextView) rowView.findViewById(R.id.kalori);
                jam.setText(result.getJam());
                karbo.setText(result.getKarbo());
                protein.setText(result.getProtein());
                lemak.setText(result.getLemak());
                kalori.setText(result.getTotalKalori() + "");

                parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
            } else {
                // AnotherActivity was not successful. No data to retrieve.
            }
        }
    }
}
