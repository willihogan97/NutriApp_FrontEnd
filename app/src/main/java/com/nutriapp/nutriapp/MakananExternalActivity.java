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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nutriapp.nutriapp.object.InfoPribadi;
import com.nutriapp.nutriapp.object.JadwalMakananExternal;
import com.nutriapp.nutriapp.object.Parenteral;
import com.nutriapp.nutriapp.object.TotalMakananExternal;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MakananExternalActivity extends AppCompatActivity{

    private ListView parentLinearLayout;
    public static Spinner spinner;
    private static String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain"
    };

    ArrayList<JadwalMakananExternal> listJadwalMakananExternal = new ArrayList<>();


    MyCustomAdapter mAdapter;

    public static final String INFO = "INFO_PRIBADI";
    public static final String PARENTERAL = "PARENTERAL";
    public static final String MAKANANEXTERNAL = "MAKANANEXTERNAL";
    InfoPribadi infoPribadi;
    Parenteral parenteral;

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
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                Log.d("item yang dipilih", spinner.getSelectedItem().toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // your code here
//            }
//
//        });

        parentLinearLayout = (ListView) findViewById(R.id.list_view);
        Intent intent = getIntent();
        infoPribadi = intent.getParcelableExtra(MainActivity.INFO);
//        Log.d("tesajajaja", "onCreate: " + infoPribadi.toString());
        parenteral = intent.getParcelableExtra(com.nutriapp.nutriapp.Parenteral.PARENTERAL);

        Button button = findViewById(R.id.btnPlus);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), TambahJadwalExternal.class);

            startActivityForResult(intent, 200);
            }
        });

        final View btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Result.class);
                TotalMakananExternal makananExternal = new TotalMakananExternal("a","a","a","a",0);
                intent.putExtra(MAKANANEXTERNAL, makananExternal);
                intent.putExtra(INFO, infoPribadi);
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
                JadwalMakananExternal result = data.getParcelableExtra(TambahJadwalExternal.EXTRA_DATA);

                mAdapter.addItem(result);
            } else {
                // AnotherActivity was not successful. No data to retrieve.
            }
        }
    }

    private class MyCustomAdapter extends BaseAdapter {

        private ArrayList<JadwalMakananExternal> mData = new ArrayList<JadwalMakananExternal>();
//        private ArrayList<Boolean> isAdded = new ArrayList<>();
        private LayoutInflater mInflater;

        public MyCustomAdapter() {
            mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void addItem(final JadwalMakananExternal item) {
            mData.add(item);
//            isAdded.add(false);
            notifyDataSetChanged();
            ListUtils.setDynamicHeight(parentLinearLayout);
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
//                if(!this.isAdded.get(position)) {
//                    mAdapter.addItem(jadwal);
//                    this.isAdded.set(position, true);
//                }


//                textView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        final LinearLayout rlLayout = (LinearLayout) v.getParent();
//
//                        TextView tv = (TextView) rlLayout.findViewById(R.id.lemak);
//
//
////                        TextView tv = (TextView) v.findViewById(R.id.kalori);
//                        String value = tv.getText().toString();
//                        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
//                    }
//                });
            }

//            holder.textView.setText(mData.get(position));
            return convertView;
        }
    }

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }
}
