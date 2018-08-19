package com.nutriapp.nutriapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.nutriapp.nutriapp.object.MakananExternal;

import java.util.ArrayList;

public class TambahMakananExternal extends AppCompatActivity {

    public static final String EXTRA_DATA = "EXTRA_DATA";
    EditText karbohidratView, kaloriView, proteinView, urtView;
    String karbohidrat, kalori, protein, urt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_makanan_external);

        final View button = findViewById(R.id.btnPlus);
        karbohidratView = (EditText) findViewById(R.id.karbohidrat_view);
        kaloriView = (EditText) findViewById(R.id.kalori_view);
        proteinView = (EditText) findViewById(R.id.protein_view);
        urtView = (EditText) findViewById(R.id.urt_view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Panggil method buat cek uda keisi apa belom

                karbohidrat = karbohidratView.getText().toString();
                kalori = kaloriView.getText().toString();
                protein = proteinView.getText().toString();
                urt = urtView.getText().toString();
                MakananExternal makananBaru = new MakananExternal(kalori, karbohidrat, protein, urt);
                String makanan = (new Gson().toJson(makananBaru));
                final Intent data = new Intent();
                data.putExtra(EXTRA_DATA, makanan);

                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
    }

}
