package com.nutriapp.nutriapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button btnSubmit;
    EditText beratBadanView, tinggiBadanView, bmiView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        beratBadanView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        beratBadanView = (EditText) findViewById(R.id.beratBadan);
        tinggiBadanView = (EditText) findViewById(R.id.tinggiBadan);
        bmiView = (EditText) findViewById(R.id.bmi);

        Button button = findViewById(R.id.btnNext);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                int beratBadan = Integer.parseInt(beratBadanView.getText().toString());
//                int tinggiBadan = Integer.parseInt(tinggiBadanView.getText().toString());
//                String bmi = (beratBadan * tinggiBadan) + "";
//                Toast.makeText(getApplicationContext(), bmi, Toast.LENGTH_SHORT).show();
//                bmiView.setText(bmi);

                Intent intent = new Intent(getApplicationContext(), Parenteral.class);
                intent.putExtra("asd", "asdasd");
                startActivityForResult(intent, 200);
//                startActivity(intent);
//                startActivity(new Intent(getApplicationContext(), MakananExternalActivity.class));
            }
        });
    }




}
