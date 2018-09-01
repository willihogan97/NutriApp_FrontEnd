package com.nutriapp.nutriapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import steelkiwi.com.library.DotsLoaderView;

public class TambahJadwalExternal extends AppCompatActivity {

    public static final String EXTRA_DATA = "EXTRA_DATA";

    ArrayList<MakananExternal> listMakanan, listKarbo, listProtein;
    List<MakananExternal> listAll, listKarboSpinner, listProteinSpinner, listLemakSpinner, listSayuranSpinner, listBuahSpinner, listSusuSpinner, listMinyakSpinner;
    JadwalMakananExternal jadwalMakanan;
    Spinner spinner;
    TextView totalKalSeluruh;
    double totalKalSeluruhDouble = 0;
    DecimalFormat dec;
    DotsLoaderView dotsLoaderView;

    public static EditText buttonPickTime;

    //ListView
    private ListView list_item;
    MyCustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_jadwal_external);

        dec = new DecimalFormat("#.0");

        dotsLoaderView = findViewById(R.id.loader);
        @SuppressLint("StaticFieldLeak") AsyncTask<String, String, String> loaderAsync = new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                dotsLoaderView.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                TambahJadwalExternal.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        addAllMandatoryFood();
                        dotsLoaderView.hide();
                    }
                });
                return "done";
            }
        };
        loaderAsync.execute();

        //Inisiasi ListView
        mAdapter = new MyCustomAdapter();
        list_item = (ListView) findViewById(R.id.list_item);

        //Buat spinner
        spinner = (Spinner)findViewById(R.id.spinnerTipeMakananExternal);
        String[] isiSpinner = getResources().getStringArray(R.array.spinnerTipeMakananExternal);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout, isiSpinner);
        spinner.setAdapter(adapter);

        //Ambil data dari database
        getDataFromDatabase();

        //inisiasi jadwal makanan baru
        jadwalMakanan = new JadwalMakananExternal();
        listMakanan = new ArrayList<MakananExternal>();

        totalKalSeluruh = (TextView) findViewById(R.id.totalKal);
        buttonPickTime = (EditText) findViewById(R.id.pickTime);

        final View buttonPlus = findViewById(R.id.btnPlus);
        final View buttonOk = findViewById(R.id.btnOk);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahJadwalExternal.this);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Apa anda yakin untuk menyimpan makanan external ini ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        double karbo = 0;
                        double protein = 0;
                        double lemak = 0;
                        double kalori = 0;
                        boolean isSaveable = true;

                        //Menghitung total semua makanan yang ada pada satu jadwal
                        for (int i = 0 ; i < mAdapter.getCount() ; i++) {
                            View a = getViewByPosition(i, list_item);
                            TextView namaMakanan = (TextView) a.findViewById(R.id.namaMakanan);
                            TextView jumlah = (TextView) a.findViewById(R.id.jumlah);
                            Spinner itemSpinner = (Spinner) a.findViewById(R.id.spinner);
                            MakananExternal makanan = (MakananExternal) itemSpinner.getSelectedItem();
                            //Itung buat dapetin itu semua
                            if (!jumlah.getText().toString().equals("")) {
                                double jumlahInt = Integer.parseInt(jumlah.getText().toString());
                                double urt = makanan.getUrt();
                                double pengali = jumlahInt / urt;
                                karbo += (makanan.getKarbohidrat() * pengali);
                                protein += (makanan.getProtein() * pengali);
                                lemak += (makanan.getLemak() * pengali);
                                kalori += (makanan.getKalori() * pengali);
                            } else {
                                if (i <= 7) {
                                    CharSequence text = "Isi seluruh mandatory";
                                    int duration = Toast.LENGTH_SHORT;
                                    isSaveable = false;
                                    Toast.makeText(getApplicationContext(), text, duration).show();
                                }
                            }
                        }

                        if(isSaveable && buttonPickTime.getText().toString().equals("")) {
                            CharSequence text = "Isi Jam";
                            int duration = Toast.LENGTH_SHORT;
                            isSaveable = false;
                            Toast.makeText(getApplicationContext(), text, duration).show();
                        }

                        if(isSaveable) {
                            jadwalMakanan.setJam(buttonPickTime.getText().toString());
                            jadwalMakanan.setKarbo(karbo);
                            jadwalMakanan.setProtein(protein);
                            jadwalMakanan.setLemak(lemak);
                            jadwalMakanan.setTotalKalori(kalori);
                            jadwalMakanan.setCara(spinner.getSelectedItem().toString());
                            final Intent data = new Intent();
                            data.putExtra(EXTRA_DATA, jadwalMakanan);
                            setResult(Activity.RESULT_OK, data);
                            finish();
                        }
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

        //Untuk menampilkan time picker
        buttonPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });


    }

    private void getDataFromDatabase() {

        String myUrl = "http://nutriapp-backend.herokuapp.com/api/external/all";

        //String to place our result in
        listAll = new ArrayList<>();
        String result;
        HttpGetRequest newGetReq = new HttpGetRequest();
        try {
            Log.d("getdatagasi", "getDataFromDatabase: ");
            listKarboSpinner = new ArrayList<>();
            listProteinSpinner = new ArrayList<>();
            listLemakSpinner = new ArrayList<>();
            listMinyakSpinner = new ArrayList<>();
            listSusuSpinner = new ArrayList<>();
            listBuahSpinner = new ArrayList<>();
            listSayuranSpinner = new ArrayList<>();
            result = newGetReq.execute(myUrl).get();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                String nama = jsonArray.getJSONObject(i).getString("nama");
                double karbohidrat = jsonArray.getJSONObject(i).getDouble("karbohidrat");
                double protein = jsonArray.getJSONObject(i).getDouble("protein");
                double lemak = jsonArray.getJSONObject(i).getDouble("lemak");
                double urt = jsonArray.getJSONObject(i).getDouble("urt");
                int tipe = jsonArray.getJSONObject(i).getInt("tipe");
                double kalori = jsonArray.getJSONObject(i).getDouble("kalori");
                MakananExternal makanan = new MakananExternal(tipe, kalori, karbohidrat, protein, urt, lemak, nama);
                listAll.add(makanan);
            }
            for (MakananExternal makanan : listAll) {
                if(makanan.getJenis()==1) {
                    listKarboSpinner.add(makanan);
                } else if(makanan.getJenis()==2) {
                    listProteinSpinner.add(makanan);
                } else if(makanan.getJenis()==3) {
                    listLemakSpinner.add(makanan);
                } else if(makanan.getJenis()==4) {
                    listSayuranSpinner.add(makanan);
                } else if(makanan.getJenis()==5) {
                    listBuahSpinner.add(makanan);
                } else if(makanan.getJenis()==6) {
                    listSusuSpinner.add(makanan);
                } else if(makanan.getJenis()==7) {
                    listMinyakSpinner.add(makanan);
                }
            }
            mAdapter.notifyDataSetChanged();
            list_item.invalidateViews();
            list_item.invalidate();
            list_item.refreshDrawableState();
            Log.d("iniape", "getDataFromDatabase: " + listSusuSpinner.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                Log.d("masuksini", "onActivityResult: ");
                getDataFromDatabase();
                mAdapter.notifyDataSetChanged();
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
                String namaTipe = mData.get(position);
                convertView = mInflater.inflate(R.layout.tambah_jadwal_external_row, null);
                TextView namaMakanan = (TextView) convertView.findViewById(R.id.namaMakanan);
                namaMakanan.setText(namaTipe);
                final Spinner spinner = (Spinner) convertView.findViewById(R.id.spinner);
                ArrayList<MakananExternal> listSpinner = new ArrayList<>();

//                1 = Karbohidrat
//                2 = protein
//                3 = lemak
//                4 = sayuran
//                5 = buah/gula
//                6 = susu
//                7 = minyak

                ArrayAdapter<MakananExternal> adapter;
                if(namaTipe.equalsIgnoreCase("karbohidrat")) {
                    adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, listKarboSpinner);
                } else if(namaTipe.equalsIgnoreCase("protein")) {
                    adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, listProteinSpinner);
                } else if(namaTipe.equalsIgnoreCase("lemak")) {
                    adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, listLemakSpinner);
                } else if(namaTipe.equalsIgnoreCase("sayuran")) {
                    adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, listSayuranSpinner);
                } else if(namaTipe.equalsIgnoreCase("buah/gula")) {
                    adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, listBuahSpinner);
                } else if(namaTipe.equalsIgnoreCase("susu")) {
                    adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, listSusuSpinner);
                } else if(namaTipe.equalsIgnoreCase("minyak")) {
                    adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, listMinyakSpinner);
                } else {
                    adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, listAll);
                }

                String[] countries = getResources().getStringArray(R.array.spinnerTipeMakananExternal);

                adapter.setDropDownViewResource(R.layout.spinner_layout);
                spinner.setAdapter(adapter);


                final TextView kaloriView = convertView.findViewById(R.id.kaloriTotalSatuan);

                final EditText jumlahView = (EditText) convertView.findViewById(R.id.jumlah);
                jumlahView.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void afterTextChanged(Editable s) {

                        double kaloriSebelumnya;
                        if(!kaloriView.getText().toString().equals("")) {
                            kaloriSebelumnya = Double.parseDouble(kaloriView.getText().toString());
                        } else {
                            kaloriSebelumnya = 0;
                        }

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

                            MakananExternal a = (MakananExternal) spinner.getSelectedItem();
                            double kal = a.getKalori();
                            double urt = a.getUrt();
                            int jumlah = Integer.parseInt(jumlahView.getText().toString());
//                            double total = kal * jumlah;
                            double total = kal * jumlah / urt;
                            kaloriView.setText(dec.format(total));
                            totalKalSeluruhDouble = totalKalSeluruhDouble - kaloriSebelumnya + total;
                            totalKalSeluruh.setText(dec.format(totalKalSeluruhDouble));
                        } else {
                            totalKalSeluruhDouble = totalKalSeluruhDouble - kaloriSebelumnya;
                            totalKalSeluruh.setText(dec.format(totalKalSeluruhDouble));
                            kaloriView.setText("0");

                            //itung lagi kalori total
                            //kalo bisa delete listviewnya
                        }
                    }
                });

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        double kaloriSebelumnya;
                        if(!kaloriView.getText().toString().equals("")) {
                            kaloriSebelumnya = Double.parseDouble(kaloriView.getText().toString());
                        } else {
                            kaloriSebelumnya = 0;
                        }
                        if(!jumlahView.getText().toString().equals("")) {
                            MakananExternal a = (MakananExternal) spinner.getSelectedItem();
                            double kal = a.getKalori();
                            double urt = a.getUrt();
                            int jumlah = Integer.parseInt(jumlahView.getText().toString());
                            double total = kal * jumlah / urt;
                            kaloriView.setText(dec.format(total));
                            totalKalSeluruhDouble = totalKalSeluruhDouble - kaloriSebelumnya + total;
                            totalKalSeluruh.setText(dec.format(totalKalSeluruhDouble));
                        } else {
                            kaloriView.setText("0");
                            totalKalSeluruhDouble = totalKalSeluruhDouble - kaloriSebelumnya;
                            totalKalSeluruh.setText(dec.format(totalKalSeluruhDouble));

                            //itung lagi kalori total
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
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

