package com.nutriapp.nutriapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nutriapp.nutriapp.object.InfoPribadi;
import com.nutriapp.nutriapp.object.JadwalMakananExternal;
import com.nutriapp.nutriapp.object.Parenteral;
import com.nutriapp.nutriapp.object.TabelMakanan;
import com.nutriapp.nutriapp.object.TotalMakananExternal;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MakananExternalActivity extends AppCompatActivity{

    private ListView parentLinearLayout;
    public TextView totalKarboJadwal, totalProteinJadwal, totalLemakJadwal, totalKaloriJadwal, sisaKalori;
    ArrayList<JadwalMakananExternal> listJadwalMakananExternal = new ArrayList<>();
    ArrayList<TabelMakanan> tabelMakananKirim = new ArrayList<>();

    double totalVolumeOral;

    MyCustomAdapter mAdapter;
    InfoPribadi infoPribadi;
    Parenteral parenteral;
    DecimalFormat dec;

    public static final String INFO = "INFO_PRIBADI";
    public static final String PARENTERAL = "PARENTERAL";
    public static final String MAKANANEXTERNAL = "MAKANANEXTERNAL";
    public static final String TABELMAKANANTOTAL = "TABELMAKANANTOTAL";
    public static final String VOLUMEORAL = "VOLUMEORAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makanan_external);

        dec = new DecimalFormat("#.0");

        //Untuk ListView
        mAdapter = new MyCustomAdapter();
        parentLinearLayout = findViewById(R.id.list_view);

        //Untuk textView
        totalKarboJadwal = findViewById(R.id.totalKarboJadwal);
        totalProteinJadwal = findViewById(R.id.totalProteinJadwal);
        totalLemakJadwal = findViewById(R.id.totalLemakJadwal);
        totalKaloriJadwal = findViewById(R.id.totalKaloriJadwal);
        sisaKalori = findViewById(R.id.sisaKalori);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(MakananExternalActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Apa anda yakin untuk menyimpan makanan external ini ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), ResultParenteral.class);
                        double totalKarbo = 0;
                        double totalProtein = 0;
                        double totalLemak = 0;
                        double totalKalori = 0;

                        for (int i = 0 ; i < listJadwalMakananExternal.size() ; i++) {
                            totalKalori += listJadwalMakananExternal.get(i).getTotalKalori();
                            totalKarbo += listJadwalMakananExternal.get(i).getKarbo();
                            totalProtein += listJadwalMakananExternal.get(i).getProtein();
                            totalLemak += listJadwalMakananExternal.get(i).getLemak();
                        }
                        TotalMakananExternal makananExternal = new TotalMakananExternal(totalKarbo + "",totalProtein + "",totalLemak + "",totalKalori + "");
                        intent.putExtra(MAKANANEXTERNAL, makananExternal);
                        intent.putExtra(INFO, infoPribadi);
                        intent.putExtra(PARENTERAL, parenteral);
                        intent.putExtra(TABELMAKANANTOTAL, tabelMakananKirim);
                        intent.putExtra(VOLUMEORAL, totalVolumeOral);
                        startActivityForResult(intent, 200);
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    //Untuk mendapatkan balasan data dari tambah jadwal exter
    //Nanti apus karena ga diperlukan
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200) {

            if(resultCode == Activity.RESULT_OK) {
                JadwalMakananExternal jadwalMakanan = data.getParcelableExtra(TambahJadwalExternal.EXTRA_DATA);
                ArrayList<TabelMakanan> tabelMakananBaru = data.getParcelableArrayListExtra(TambahJadwalExternal.TABELMAKANANTOTAL);
                for (int i = 0; i < tabelMakananBaru.size(); i++) {
                    tabelMakananKirim.add(tabelMakananBaru.get(i));
                }
                totalVolumeOral += data.getDoubleExtra(TambahJadwalExternal.VOLUMEORAL, 0);
                listJadwalMakananExternal.add(jadwalMakanan);
                calculateAndShowTotal();
                mAdapter.addItem(jadwalMakanan);
                parentLinearLayout.setAdapter(mAdapter);
            }
        }
    }

    private void calculateAndShowTotal() {
        double totalKal = 0;
        double totalKarbo = 0;
        double totalProtein = 0;
        double totalLemak = 0;

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
                TextView kalori = convertView.findViewById(R.id.kalori);
                TextView karbo = convertView.findViewById(R.id.karbo);
                TextView jam = convertView.findViewById(R.id.jam);
                TextView protein = convertView.findViewById(R.id.protein);
                TextView lemak = convertView.findViewById(R.id.lemak);
                TextView cara = convertView.findViewById(R.id.cara);
                kalori.setText(dec.format(jadwal.getTotalKalori()));
                karbo.setText(dec.format(jadwal.getKarbo()));
                jam.setText(jadwal.getJam());
                protein.setText(dec.format(jadwal.getProtein()));
                lemak.setText(dec.format(jadwal.getLemak()));
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
