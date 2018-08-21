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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nutriapp.nutriapp.object.JadwalMakananExternal;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MakananExternalActivity extends AppCompatActivity{

    private ListView parentLinearLayout;
    public static Spinner spinner;
    private static String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain"
    };

    MyCustomAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makanan_external);

        mAdapter = new MyCustomAdapter();
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


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.coba);
        textView.setAdapter(adapter2);


//        getSupportActionBar().setHomeButtonEnabled(true);


        parentLinearLayout = (ListView) findViewById(R.id.list_view);
        Intent intent = getIntent();
        String bmi = intent.getStringExtra("asd");
//        Toast.makeText(getApplicationContext(), bmi, Toast.LENGTH_SHORT).show();

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
//                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                final View rowView = inflater.inflate(R.layout.makanan_external_row, null);
//                TextView jam = (TextView) rowView.findViewById(R.id.jam);
//                TextView karbo = (TextView) rowView.findViewById(R.id.karbo);
//                TextView protein = (TextView) rowView.findViewById(R.id.protein);
//                TextView lemak = (TextView) rowView.findViewById(R.id.lemak);
//                TextView kalori = (TextView) rowView.findViewById(R.id.kalori);
//                jam.setText(result.getJam());
//                karbo.setText(result.getKarbo());
//                protein.setText(result.getProtein());
//                lemak.setText(result.getLemak());
//                kalori.setText(result.getTotalKalori() + "");


                mAdapter.addItem(result);
                parentLinearLayout.setAdapter(mAdapter);


//                parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
            } else {
                // AnotherActivity was not successful. No data to retrieve.
            }
        }
    }

    private class MyCustomAdapter extends BaseAdapter {

        private ArrayList<JadwalMakananExternal> mData = new ArrayList<JadwalMakananExternal>();
        private LayoutInflater mInflater;

        public MyCustomAdapter() {
            mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void addItem(final JadwalMakananExternal item) {
            mData.add(item);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public JadwalMakananExternal getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
//            NumericViewHolder holder = new NumericViewHolder();
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.makanan_external_row, null);
                Button textView = (Button) convertView.findViewById(R.id.edit);
                JadwalMakananExternal jadwal = getItem(position);
                TextView kalori = (TextView) convertView.findViewById(R.id.kalori);
                TextView karbo = (TextView) convertView.findViewById(R.id.karbo);
                TextView jam = (TextView) convertView.findViewById(R.id.jam);
                TextView protein = (TextView) convertView.findViewById(R.id.protein);
                TextView lemak = (TextView) convertView.findViewById(R.id.lemak);
                kalori.setText(jadwal.getTotalKalori() + "");
                karbo.setText(jadwal.getKarbo());
                jam.setText(jadwal.getJam());
                protein.setText(jadwal.getProtein());
                lemak.setText(jadwal.getLemak());
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final LinearLayout rlLayout = (LinearLayout) v.getParent();

                        TextView tv = (TextView) rlLayout.findViewById(R.id.lemak);


//                        TextView tv = (TextView) v.findViewById(R.id.kalori);
                        String value = tv.getText().toString();
                        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
                    }
                });
            }

//            holder.textView.setText(mData.get(position));
            return convertView;
        }
    }
}
