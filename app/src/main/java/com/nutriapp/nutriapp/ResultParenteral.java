package com.nutriapp.nutriapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nutriapp.nutriapp.object.InfoPribadi;
import com.nutriapp.nutriapp.object.JadwalMakananExternal;
import com.nutriapp.nutriapp.object.MakananExternal;
import com.nutriapp.nutriapp.object.Parenteral;
import com.nutriapp.nutriapp.object.TabelMakanan;
import com.nutriapp.nutriapp.object.TotalMakananExternal;

import java.text.DecimalFormat;
import java.util.ArrayList;

@SuppressLint("Registered")
public class ResultParenteral extends AppCompatActivity {

    InfoPribadi infoPribadi;
    Parenteral parenteral;
    TotalMakananExternal makananExternal;
    ArrayList<JadwalMakananExternal> listJadwalMakananExternal;
    ArrayList<TabelMakanan> tabelMakananKirim;
    private ListView parentLinearLayout;
    MyCustomAdapter mAdapter;

    public static final String INFO = "INFO_PRIBADI";
    public static final String PARENTERAL = "PARENTERAL";
    public static final String MAKANANEXTERNAL = "MAKANANEXTERNAL";
    public static final String LISTMAKANANEXTERNAL = "LISTMAKANANEXTERNAL";

    ArrayList<TabelMakanan> tabelMakananTotal;

    DecimalFormat dec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_external);

        dec = new DecimalFormat("#.0");

        parentLinearLayout = findViewById(R.id.list_item);

        mAdapter = new MyCustomAdapter();

        Intent intent = getIntent();
        parenteral = intent.getParcelableExtra(com.nutriapp.nutriapp.Parenteral.PARENTERAL);
        infoPribadi = intent.getParcelableExtra(MainActivity.INFO);
        makananExternal = intent.getParcelableExtra(MakananExternalActivity.MAKANANEXTERNAL);
        tabelMakananKirim = intent.getParcelableArrayListExtra(MakananExternalActivity.TABELMAKANANTOTAL);

        for (int i = 0; i < tabelMakananKirim.size(); i++) {
            mAdapter.addItem("tambahan");
        }

        Button buttonCreateNew = findViewById(R.id.buttonCreateNew);
        buttonCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Result.class);
                intent.putExtra(MAKANANEXTERNAL, makananExternal);
                intent.putExtra(INFO, infoPribadi);
                intent.putExtra(PARENTERAL, parenteral);
                startActivityForResult(intent, 200);
                startActivity(intent);
            }
        });
    }

    //Adapter untuk kebutuhan ListView
    private class MyCustomAdapter extends BaseAdapter {

        private ArrayList<String> mData = new ArrayList<>();
        private LayoutInflater mInflater;

        public MyCustomAdapter() {
            mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void addItem(final String item) {
            mData.add(item);
            notifyDataSetChanged();
            ResultParenteral.ListUtils.setDynamicHeight(parentLinearLayout);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public String getItem(int position) {
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
                TextView jam = convertView.findViewById(R.id.jam);
                TextView namaMakanan = convertView.findViewById(R.id.namaMakanan);
                EditText jumlah = convertView.findViewById(R.id.jumlah);
                jam.setText(tabelMakananKirim.get(position).getJam());
                namaMakanan.setText(tabelMakananKirim.get(position).getSpinner());
                jumlah.setText(tabelMakananKirim.get(position).getBerat());
            }

//            holder.textView.setText(mData.get(position));
            return convertView;
        }
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