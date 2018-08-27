package com.nutriapp.nutriapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nutriapp.nutriapp.object.InfoPribadi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SuppressLint("Registered")
public class Parenteral extends AppCompatActivity {

    Button next;
    EditText volumeView;
    LinearLayout selection, volume, detail;
    TextView karbohidrat, protein, lemak, elektrolit, kalori, sisa;
    InfoPribadi infoPribadi;

    public static final String INFO = "INFO_PRIBADI";
    public static final String PARENTERAL = "PARENTERAL";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parenteral);

        selection = findViewById(R.id.selection);
        volume = findViewById(R.id.volume);
        detail = findViewById(R.id.detail);
        karbohidrat = findViewById(R.id.carbohydrateDetailValue);
        protein = findViewById(R.id.proteinDetailValue);
        lemak = findViewById(R.id.fatDetailVslue);
        elektrolit = findViewById(R.id.electroliteDetailVslue);
        kalori = findViewById(R.id.caloriesDetailVslue);
        sisa = findViewById(R.id.caloriesRemaindersValue);

        selection.setVisibility(View.VISIBLE);
        volume.setVisibility(View.GONE);
        detail.setVisibility(View.GONE);

        Intent intent = getIntent();
        infoPribadi = intent.getParcelableExtra(MainActivity.INFO);
        final String kal = intent.getStringExtra("totalKalori");
        final String cair = intent.getStringExtra("totalKaloriCair");

        String api = sendGet1("/api/parenteral/all");
        String myUrl = "http://10.0.2.2:8080/api/parenteral/all";

        //String to place our result in
        final List<com.nutriapp.nutriapp.object.Parenteral> listAll = new ArrayList<>();
        String result;
        Parenteral.HttpGetRequest getRequest = new Parenteral.HttpGetRequest();

        //Perform the doInBackground method, passing in our url
        try {
            result = getRequest.execute(myUrl).get();
            JSONObject jsnobject = new JSONObject(result);

            JSONArray jsonArrayId = jsnobject.getJSONArray("id");
            JSONArray jsonArrayName = jsnobject.getJSONArray("nama");
            JSONArray jsonArrayVolume = jsnobject.getJSONArray("volume");
            JSONArray jsonArrayKarbohidrat = jsnobject.getJSONArray("karbohidrat");
            JSONArray jsonArrayProtein = jsnobject.getJSONArray("protein");
            JSONArray jsonArrayFat = jsnobject.getJSONArray("lemak");
            JSONArray jsonArrayElectrolite = jsnobject.getJSONArray("elektrolit");
            JSONArray jsonArrayCalories = jsnobject.getJSONArray("kalori");

            for (int i = 0; i < jsonArrayId.length(); i++) {
                com.nutriapp.nutriapp.object.Parenteral parenteral = new com.nutriapp.nutriapp.object.Parenteral(jsonArrayName.getString(i),
                        Double.parseDouble(jsonArrayVolume.getString(i)), Double.parseDouble(jsonArrayKarbohidrat.getString(i)),
                        Double.parseDouble(jsonArrayProtein.getString(i)), Double.parseDouble(jsonArrayFat.getString(i)),
                        Double.parseDouble(jsonArrayElectrolite.getString(i)), Double.parseDouble(jsonArrayCalories.getString(i)));
                listAll.add(parenteral);
            }

            Log.d("getAll", "onClick: " + result);
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }


//        String[] arraySpinner = getResources().getStringArray(R.array.spinnerTipeMakananExternal);
        final Spinner s = findViewById(R.id.parenteralSelection);
        ArrayAdapter<com.nutriapp.nutriapp.object.Parenteral> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listAll);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        final com.nutriapp.nutriapp.object.Parenteral[] parenteralItem = new com.nutriapp.nutriapp.object.Parenteral[0];

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(!s.getSelectedItem().toString().equals("")) {
                    volume.setVisibility(View.VISIBLE);
                    for(int i = 0; i < listAll.size(); i++){
                        if(s.getSelectedItem().toString().equals(listAll.get(i).getName())){
                            parenteralItem[0] = listAll.get(i);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        volumeView = findViewById(R.id.editVolume);
        volumeView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable s) {
                detail.setVisibility(View.VISIBLE);

                DecimalFormat dec = new DecimalFormat("#.0");
                double ratio = Double.parseDouble(volumeView.toString()) / parenteralItem[0].getVolume();
                karbohidrat.setText(dec.format(Double.toString(parenteralItem[0].getCarbohydrate() * ratio)));
                protein.setText(dec.format(Double.toString(parenteralItem[0].getProtein() * ratio)));
                lemak.setText(dec.format(Double.toString(parenteralItem[0].getFat() * ratio)));
                elektrolit.setText(dec.format(Double.toString(parenteralItem[0].getElectrolite() * ratio)));
                kalori.setText(dec.format(Double.toString(parenteralItem[0].getCalories() * ratio)));

                double remain = Double.parseDouble(kal) - (parenteralItem[0].getCalories() * ratio);
                double cairRemain = Double.parseDouble(cair) - Double.parseDouble(volumeView.getText().toString());
                String sisaText = dec.format(Double.toString(remain)) + "Kkal - " + dec.format(Double.toString(cairRemain)) + "ml";
                sisa.setText(sisaText);
            }
        });

        next = findViewById(R.id.buttonNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MakananExternalActivity.class);
                intent.putExtra(INFO, infoPribadi);
                com.nutriapp.nutriapp.object.Parenteral parenteral = new com.nutriapp.nutriapp.object.Parenteral(
                        s.getSelectedItem().toString(), Double.parseDouble(volumeView.getText().toString()),
                        Double.parseDouble(karbohidrat.getText().toString()), Double.parseDouble(protein.getText().toString()),
                                Double.parseDouble(lemak.getText().toString()), Double.parseDouble(elektrolit.getText().toString()),
                                        Double.parseDouble(kalori.getText().toString()));
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

            } else {
                // AnotherActivity was not successful. No data to retrieve.
            }
        }
    }

    public String sendGet1(final String url) {
        StringBuilder result = new StringBuilder();
        String response1 = "";

        //ini kalo pake virtual machine android
        String apiUrl = "http://10.0.2.2:8080" + url;

        //ini kalo pake hape, liat ip laptop lu berapa
        //connect pake wifi yang sama
        //String apiUrl = "http://192.168.1.12" + url;

        RequestQueue req = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Log.e("isinya", "sendGet: " + response.getJSONArray("result").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("error", "onErrorResponse: error");
                    }
                });

        req.add(jsonObjectRequest);


        return result.toString();
    }

    public class HttpGetRequest extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        String result;
        @Override
        protected String doInBackground(String... params){
            String stringUrl = params[0];
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            } catch (ProtocolException | MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}
