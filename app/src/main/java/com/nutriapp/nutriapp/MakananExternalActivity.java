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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nutriapp.nutriapp.object.JadwalMakananExternal;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MakananExternalActivity extends AppCompatActivity{

    private LinearLayout parentLinearLayout;
    EditText asd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makanan_external);

//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        parentLinearLayout = (LinearLayout) findViewById(R.id.listMakanan);
        Intent intent = getIntent();
        String bmi = intent.getStringExtra("asd");
        Toast.makeText(getApplicationContext(), bmi, Toast.LENGTH_SHORT).show();

        Button button = findViewById(R.id.btnPlus);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                // Add the new row before the add field button.

                Intent intent = new Intent(getApplicationContext(), TambahJadwalExternal.class);

                // Start AnotherActivity with the request code
                startActivityForResult(intent, 200);
//                parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
//                finish();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200) {

            if(resultCode == Activity.RESULT_OK) {
                JadwalMakananExternal result = data.getParcelableExtra(TambahJadwalExternal.EXTRA_DATA);
//                Toast.makeText(this, "Result: " + result, Toast.LENGTH_LONG).show();

                // Tambah child di UI
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.makanan_external_row, null);
                TextView jam = (TextView) rowView.findViewById(R.id.jam);
                TextView karbo = (TextView) rowView.findViewById(R.id.karbo);
                TextView protein = (TextView) rowView.findViewById(R.id.protein);
                TextView lemak = (TextView) rowView.findViewById(R.id.lemak);
                TextView kalori = (TextView) rowView.findViewById(R.id.kalori);
                jam.setText("10:00");
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
