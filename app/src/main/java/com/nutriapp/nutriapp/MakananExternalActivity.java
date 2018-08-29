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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MakananExternalActivity extends AppCompatActivity{

    private ListView parentLinearLayout;
    public TextView totalKarboJadwal, totalProteinJadwal, totalLemakJadwal, totalKaloriJadwal, sisaKalori;
    public static Spinner spinner;

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

        //Untuk ListView
        mAdapter = new MyCustomAdapter();
        parentLinearLayout = (ListView) findViewById(R.id.list_view);

        //Untuk textView
        totalKarboJadwal = (TextView) findViewById(R.id.totalKarboJadwal);
        totalProteinJadwal = (TextView) findViewById(R.id.totalProteinJadwal);
        totalLemakJadwal = (TextView) findViewById(R.id.totalLemakJadwal);
        totalKaloriJadwal = (TextView) findViewById(R.id.totalKaloriJadwal);
        sisaKalori = (TextView) findViewById(R.id.sisaKalori);

        //Untuk Spinner
        spinner = (Spinner)findViewById(R.id.spinnerTipeMakananExternal);
        String[] isiSpinner = getResources().getStringArray(R.array.spinnerTipeMakananExternal);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout, isiSpinner);

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

        //Ambil data intent
        Intent intent = getIntent();
        infoPribadi = intent.getParcelableExtra(MainActivity.INFO);
        parenteral = intent.getParcelableExtra(com.nutriapp.nutriapp.Parenteral.PARENTERAL);

        //Button menuju tambah jadwal external
        Button button = findViewById(R.id.btnPlus);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), TambahJadwalExternal.class);

            startActivityForResult(intent, 200);
            }
        });

        //Button untuk mengimpan makanan external yang baru
        Button btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Result.class);
                double totalKarbo = 0;
                double totalProtein = 0;
                double totalLemak = 0;
                double totalKalori = 0;
//                for (int i = 0 ; i < mAdapter.getCount() ; i++) {
//                    View a = getViewByPosition(i, parentLinearLayout);
//                    TextView karbo = (TextView) a.findViewById(R.id.karbo);
//                    TextView lemak = (TextView) a.findViewById(R.id.lemak);
//                    TextView kalori = (TextView) a.findViewById(R.id.kalori);
//                    TextView protein = (TextView) a.findViewById(R.id.protein);
//                    if (!karbo.getText().toString().equals("")) {
//                        totalKarbo += Integer.parseInt(karbo.getText().toString());
//                    }
//                    if (!protein.getText().toString().equals("")) {
//                        totalProtein += Integer.parseInt(protein.getText().toString());
//                    }
//                    if (!lemak.getText().toString().equals("")) {
//                        totalLemak += Integer.parseInt(lemak.getText().toString());
//                    }
//                    if (!kalori.getText().toString().equals("")) {
//                        totalKalori += Integer.parseInt(kalori.getText().toString());
//                    }
//                }

                for (int i = 0 ; i < listJadwalMakananExternal.size() ; i++) {
                    totalKalori += listJadwalMakananExternal.get(i).getTotalKalori();
                    totalKarbo += listJadwalMakananExternal.get(i).getKarbo();
                    totalProtein += listJadwalMakananExternal.get(i).getProtein();
                    totalLemak += listJadwalMakananExternal.get(i).getLemak();
                }

                String jenis = spinner.getSelectedItem().toString();
                TotalMakananExternal makananExternal = new TotalMakananExternal(totalKarbo + "",totalProtein + "",totalLemak + "",totalKalori + "");
                intent.putExtra(MAKANANEXTERNAL, makananExternal);
                intent.putExtra(INFO, infoPribadi);
                intent.putExtra(PARENTERAL, parenteral);
                startActivityForResult(intent, 200);
            }
        });

    }

    //Untuk mendapatkan balasan data dari tambah jadwal exter
    //Nanti apus karena ga diperlukan
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200) {

            if(resultCode == Activity.RESULT_OK) {
                JadwalMakananExternal jadwalMakanan = data.getParcelableExtra(TambahJadwalExternal.EXTRA_DATA);
                listJadwalMakananExternal.add(jadwalMakanan);
                calculateAndShowTotal();
                mAdapter.addItem(jadwalMakanan);
                parentLinearLayout.setAdapter(mAdapter);
            } else {
                // AnotherActivity was not successful. No data to retrieve.
            }
        }
    }

    private void calculateAndShowTotal() {
        double totalKal = 0;
        double totalKarbo = 0;
        double totalProtein = 0;
        double totalLemak = 0;
        DecimalFormat dec = new DecimalFormat("#.0");
        for (JadwalMakananExternal jadwal : listJadwalMakananExternal) {
            totalKal += jadwal.getTotalKalori();
            totalKarbo += jadwal.getKarbo();
            totalLemak += jadwal.getLemak();
            totalProtein += jadwal.getProtein();
        }
        double remain = infoPribadi.getTotalKalori() - parenteral.getCalories() - (totalKal);
        String sisaText = dec.format((remain)) + "Kkal";
        sisaKalori.setText(sisaText);
        totalKaloriJadwal.setText(dec.format(totalKal));
        totalKarboJadwal.setText(dec.format(totalKarbo));
        totalProteinJadwal.setText(dec.format(totalProtein));
        totalLemakJadwal.setText(dec.format(totalLemak));
    }

    //Untuk ambil view dari ListView
    public View getViewByPosition(int position, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition =firstListItemPosition + listView.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition ) {
            return listView.getAdapter().getView(position, listView.getChildAt(position), listView);
        } else {
            final int childIndex = position - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    //Adapter untuk kebutuhan ListView
    private class MyCustomAdapter extends BaseAdapter {

        private ArrayList<JadwalMakananExternal> mData = new ArrayList<JadwalMakananExternal>();
        private LayoutInflater mInflater;

        public MyCustomAdapter() {
            mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void addItem(final JadwalMakananExternal item) {
            mData.add(item);
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
                TextView cara = (TextView) convertView.findViewById(R.id.cara);
                kalori.setText(jadwal.getTotalKalori() + "");
                karbo.setText(jadwal.getKarbo() + "");
                jam.setText(jadwal.getJam());
                protein.setText(jadwal.getProtein() + "");
                lemak.setText(jadwal.getLemak() + "");
                cara.setText(jadwal.getCara());

                //Kalo tiba" perlu aja
//                textView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        final LinearLayout rlLayout = (LinearLayout) v.getParent();
//
//                        TextView tv = (TextView) rlLayout.findViewById(R.id.lemak);
//
//
//                        TextView tv = (TextView) v.findViewById(R.id.kalori);
//                        String value = tv.getText().toString();
//                        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
//                    }
//                });
            }

//            holder.textView.setText(mData.get(position));
            return convertView;
        }
    }

    //Untuk auto height dari listview
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
