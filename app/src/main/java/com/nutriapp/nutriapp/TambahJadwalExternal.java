package com.nutriapp.nutriapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.View.MeasureSpec;

import com.google.gson.Gson;
import com.nutriapp.nutriapp.object.JadwalMakananExternal;
import com.nutriapp.nutriapp.object.MakananExternal;

import java.util.ArrayList;
import java.util.Calendar;

public class TambahJadwalExternal extends AppCompatActivity {

    public static final String EXTRA_DATA = "EXTRA_DATA";

    ArrayList<MakananExternal> listMakanan;
    JadwalMakananExternal jadwalMakanan;

    public static EditText totalKalori, pickTime, buttonPickTime;

    //ListView
    private ListView list_item;
    MyCustomAdapter mAdapter;

    //Test doang nanti diganti
    private static String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_jadwal_external);

        //Inisiasi ListView
        mAdapter = new MyCustomAdapter();
        list_item = (ListView) findViewById(R.id.list_item);


        //inisiasi jadwal makanan baru
        jadwalMakanan = new JadwalMakananExternal();
        listMakanan = new ArrayList<MakananExternal>();

        totalKalori = (EditText) findViewById(R.id.totalKal);
        pickTime = (EditText) findViewById(R.id.pickTime);

        final View buttonPlus = findViewById(R.id.btnPlus);
        final View buttonOk = findViewById(R.id.btnOk);

        addAllMandatoryFood();

        //Melakukan penambahan makanan
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddFood.class);
                startActivityForResult(intent, 200);
            }
        });

        //Melakukan Penyimpanan jadwal baru
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int karbo = 0;
                int protein = 0;
                int lemak = 0;
                int kalori = 0;

                //Menghitung total semua makanan yang ada pada satu jadwal
                for (int i = 0 ; i < mAdapter.getCount() ; i++) {
                    View a = getViewByPosition(i, list_item);
                    TextView namaMakanan = (TextView) a.findViewById(R.id.namaMakanan);
                    TextView jumlah = (TextView) a.findViewById(R.id.jumlah);
                    Spinner itemSpinner = (Spinner) a.findViewById(R.id.spinner);

                    //Itung buat dapetin itu semua
                    //karbo +=
                    //protein +=
                    //lemak +=
                    //kalori +=

                    Log.d("namamakanan", "onClick: " + namaMakanan.getText().toString());
                    Log.d("jumlah", "onClick: " + jumlah.getText().toString());
                    Log.d("itemSpinner", "onClick: " + itemSpinner.getSelectedItem().toString());
                }

                jadwalMakanan.setJam(pickTime.getText().toString());
                jadwalMakanan.setKarbo(karbo + "");
                jadwalMakanan.setProtein(protein + "");
                jadwalMakanan.setLemak(lemak + "");
                jadwalMakanan.setTotalKalori(kalori);
                final Intent data = new Intent();
                data.putExtra(EXTRA_DATA, jadwalMakanan);
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });

        //Untuk menampilkan time picker
        buttonPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });
    }

    private void addAllMandatoryFood() {
        mAdapter.addItem("Karbohidrat");
        mAdapter.addItem("Protein");
        mAdapter.addItem("Lemak");
        mAdapter.addItem("Sayuran");
        mAdapter.addItem("Buah/Gula");
        mAdapter.addItem("Susu");
        mAdapter.addItem("Minyak");
        list_item.setAdapter(mAdapter);
        ListUtils.setDynamicHeight(list_item);
    }

    //Masi belom jalan nanti cari lagi
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("asdasdasdasd", "onBackPressed: asdasdasdasd");
        finish();
    }

    //Ini nanti ga melakukan apa"
    //Nanti terakhir apus aja
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200) {
            if(resultCode == Activity.RESULT_OK) {
//                final String result = data.getStringExtra(TambahJadwalExternal.EXTRA_DATA);
//                Gson gson = new Gson();
//                MakananExternal makanan =  gson.fromJson(result, MakananExternal.class);
//                listMakanan.add(makanan);
//                jadwalMakanan.setKarbo(makanan.getKarbohidrat());
//                jadwalMakanan.setLemak(makanan.getUrt());
//                jadwalMakanan.setProtein(makanan.getProtein());
//                jadwalMakanan.setTotalKalori(10);

            } else {
                // AnotherActivity was not successful. No data to retrieve.
            }
        }
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

    private class MyCustomAdapter extends BaseAdapter {

        private ArrayList<String> mData = new ArrayList<String>();
        private LayoutInflater mInflater;
        private ArrayList<Boolean> isType = new ArrayList<>();

        public MyCustomAdapter() {
            mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void addItem(final String item) {
            mData.add(item);
            isType.add(false);
            notifyDataSetChanged();
            ListUtils.setDynamicHeight(list_item);
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

                convertView = mInflater.inflate(R.layout.tambah_jadwal_external_row, null);
                TextView namaMakanan = (TextView) convertView.findViewById(R.id.namaMakanan);
                namaMakanan.setText(mData.get(position));
                Spinner spinner = (Spinner) convertView.findViewById(R.id.spinner);
                String[] countries = getResources().getStringArray(R.array.spinnerTipeMakananExternal);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, countries);
                adapter.setDropDownViewResource(R.layout.spinner_layout);
                spinner.setAdapter(adapter);

                final EditText jumlahView = (EditText) convertView.findViewById(R.id.jumlah);
                jumlahView.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void afterTextChanged(Editable s) {

                        if(!jumlahView.getText().toString().equals("")) {
                            isType.set(position, true);
                            Boolean isAllTyped = true;
                            for (Boolean typed : isType) {
                                if(!typed) {
                                    isAllTyped = false;
                                }
                            }
                            if(isAllTyped) {
                                String newMakanan = "Tambahan";
                                mAdapter.addItem(newMakanan);
                            }
                        }
                    }
                });
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
            int desiredWidth = MeasureSpec.makeMeasureSpec(mListView.getWidth(), MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }
}

