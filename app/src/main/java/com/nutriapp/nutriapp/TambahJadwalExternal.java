package com.nutriapp.nutriapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nutriapp.nutriapp.object.JadwalMakananExternal;
import com.nutriapp.nutriapp.object.MakananExternal;

import java.util.ArrayList;
import java.util.Calendar;

public class TambahJadwalExternal extends AppCompatActivity {

    public static final String EXTRA_DATA = "EXTRA_DATA";
    ArrayList<MakananExternal> listJadwalMakanan;
    JadwalMakananExternal jadwalMakanan;
    public static EditText totalKalori;
    public static EditText buttonPickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_jadwal_external);

        totalKalori = (EditText) findViewById(R.id.totalKal);



        final View buttonPlus = findViewById(R.id.btnPlus);
        final View buttonOk = findViewById(R.id.btnOk);
        buttonPickTime = (EditText) findViewById(R.id.pickTime);
        listJadwalMakanan = new ArrayList<MakananExternal>();
        // When this button is clicked we want to return a result
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new Intent as container for the result
                Intent intent = new Intent(getApplicationContext(), TambahMakananExternal.class);

                // Start AnotherActivity with the request code
                startActivityForResult(intent, 200);

                // Set the resultCode to Activity.RESULT_OK to
                // indicate a success and attach the Intent
                // which contains our result data

                // With finish() we close the AnotherActivity to
                // return to MainActivity
//                finish();
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
                final Intent data = new Intent();
                data.putExtra(EXTRA_DATA, jadwalMakanan);
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });

        buttonPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200) {
            if(resultCode == Activity.RESULT_OK) {
                final String result = data.getStringExtra(TambahJadwalExternal.EXTRA_DATA);
                Gson gson = new Gson();
                MakananExternal makanan =  gson.fromJson(result, MakananExternal.class);

                jadwalMakanan = new JadwalMakananExternal(makanan.getKarbohidrat(), makanan.getProtein(), makanan.getKalori(), 10);
                listJadwalMakanan.add(makanan);

                Toast.makeText(this, "Result: " + makanan.getKalori(), Toast.LENGTH_LONG).show();
//                parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
            } else {
                // AnotherActivity was not successful. No data to retrieve.
            }
        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            buttonPickTime.setText(hourOfDay + ":" + minute);
        }
    }
}

