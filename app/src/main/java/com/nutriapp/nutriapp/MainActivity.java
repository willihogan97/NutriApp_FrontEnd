package com.nutriapp.nutriapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nutriapp.nutriapp.object.InfoPribadi;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button btnnext, btncobaapi;
    EditText beratBadanView, tinggiBadanView, llaView, skinFoldView, kkalView, stressFactorView, mlView;
    LinearLayout percentage, cairan, totKal, stressFactor, normalKal, bmiLayout, otherBMI, normalBMI, hitunganKalori, btn, tipeHitungan;
    TextView bmi, bmiStatus, llastatus, totKalori, totCair;
    public static final String INFO = "INFO_PRIBADI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        percentage = (LinearLayout) findViewById(R.id.percentage);
        cairan = (LinearLayout) findViewById(R.id.c);
        totKal = (LinearLayout) findViewById(R.id.totKal);
        stressFactor = (LinearLayout) findViewById(R.id.stressFactor);
        normalKal = (LinearLayout) findViewById(R.id.normalKal);
        bmiLayout = (LinearLayout) findViewById(R.id.bmiLayout);
        otherBMI = (LinearLayout) findViewById(R.id.otherBMI);
        normalBMI = (LinearLayout) findViewById(R.id.normalBMI);
        hitunganKalori = (LinearLayout) findViewById(R.id.hitunganKalori);
        btn = (LinearLayout) findViewById(R.id.btn);
        beratBadanView = (EditText) findViewById(R.id.beratBadan);
        tinggiBadanView = (EditText) findViewById(R.id.tinggiBadan);
        kkalView = (EditText) findViewById(R.id.kalori);
        mlView = (EditText) findViewById(R.id.mlkal);
        bmi = (TextView) findViewById(R.id.bmi);
        totKalori = (TextView) findViewById(R.id.totalKal);
        totCair = (TextView) findViewById(R.id.cairan);
        bmiStatus = (TextView) findViewById(R.id.bmiStatus);

        percentage.setVisibility(View.GONE);
        cairan.setVisibility(View.GONE);
        totKal.setVisibility(View.GONE);
        stressFactor.setVisibility(View.GONE);
        normalKal.setVisibility(View.GONE);
        bmiLayout.setVisibility(View.GONE);
        otherBMI.setVisibility(View.GONE);
        normalBMI.setVisibility(View.GONE);
        btn.setVisibility(View.GONE);
        hitunganKalori.setVisibility(View.GONE);

        String[] tipeBMI = getResources().getStringArray(R.array.spinnerBMI);
        String[] tipeHitunganKalori = getResources().getStringArray(R.array.spinnerHitunganKalori);

        final Spinner s = (Spinner) findViewById(R.id.bmidropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tipeBMI);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(s.getSelectedItem().toString().equals("Berat - Tinggi Badan")) {
                    normalBMI.setVisibility(View.VISIBLE);
                    otherBMI.setVisibility(View.GONE);
                } else {
                    otherBMI.setVisibility(View.VISIBLE);
                    normalBMI.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        beratBadanView.addTextChangedListener(new GenericTextWatcher(beratBadanView));
        tinggiBadanView.addTextChangedListener(new GenericTextWatcher(tinggiBadanView));

//        if(!(llaView.getText().toString().equals("") && skinFoldView.getText().toString().equals(""))){
//            bmiLayout.setVisibility(View.VISIBLE);
//            double countBMI = Double.parseDouble(beratBadanView.getText().toString()) / Math.pow(Double.parseDouble(tinggiBadanView.getText().toString()), 2);
//            bmi.setText(Double.toString(countBMI));
//
//            if(countBMI < 18.5){
//                bmiStatus.setText("Underweight");
//            } else if(18.5 <= countBMI && countBMI <= 24.9){
//                bmiStatus.setText("Healthy");
//            } else if(25.0 <= countBMI && countBMI <= 29.9){
//                bmiStatus.setText("Overweight");
//            } else {
//                bmiStatus.setText("Obese");
//            }
//
//            hitunganKalori.setVisibility(View.VISIBLE);
//        }

        final Spinner r = (Spinner) findViewById(R.id.hitunganKaloriDropdown);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tipeHitunganKalori);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        r.setAdapter(adapter);

        r.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(r.getSelectedItem().toString().equals("Normal")) {
                    normalKal.setVisibility(View.VISIBLE);
                    stressFactor.setVisibility(View.GONE);
                } else {
                    stressFactor.setVisibility(View.VISIBLE);
                    normalKal.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        kkalView.addTextChangedListener(new GenericTextWatcher(kkalView));
        mlView.addTextChangedListener(new GenericTextWatcher(mlView));

        Button button = findViewById(R.id.btnNext);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                int beratBadan = Integer.parseInt(beratBadanView.getText().toString());
//                int tinggiBadan = Integer.parseInt(tinggiBadanView.getText().toString());
//                String bmi = (beratBadan * tinggiBadan) + "";
//                Toast.makeText(getApplicationContext(), bmi, Toast.LENGTH_SHORT).show();
//                bmiView.setText(bmi);

                Intent intent = new Intent(getApplicationContext(), Parenteral.class);
                InfoPribadi infoPribadi = new InfoPribadi(1,1,"a","a","a","a","a","a","100","12","a","a","a");
                intent.putExtra(INFO, infoPribadi);
                startActivityForResult(intent, 200);
//                startActivity(intent);
//                startActivity(new Intent(getApplicationContext(), MakananExternalActivity.class));
            }
        });

        btncobaapi = findViewById(R.id.btncobaapi);
        btncobaapi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Contoh pake volley
                //Belom bisa masuk data asyncnya
                String test = sendGet1("/api/protein/all");
                Log.d("test1", "onClick: " +test);

                //Contoh yang uda bener
                String myUrl = "http://10.0.2.2:8080/api/protein/all";
                //String to place our result in
                String result;
                HttpGetRequest getRequest = new HttpGetRequest();
                //Perform the doInBackground method, passing in our url
                try {
                    result = getRequest.execute(myUrl).get();
                    Log.d("test2", "onClick: " + result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                //Contoh nambahin data
                addDatabase("/api/protein/add");
            }
        });
    }

    private class GenericTextWatcher implements TextWatcher{

        private View view;
        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        public void afterTextChanged(Editable editable1) {
            String bb = editable1.toString();

            if(!beratBadanView.getText().toString().equals("") && !tinggiBadanView.getText().toString().equals("")) {
                //lakukan itung
                bmiLayout.setVisibility(View.VISIBLE);
                DecimalFormat dec = new DecimalFormat("#.0");
                double countBMI = Double.parseDouble(beratBadanView.getText().toString()) / Math.pow(Double.parseDouble(tinggiBadanView.getText().toString())/100, 2);
                bmi.setText(dec.format(countBMI));
                setRightLabelBMI(countBMI);
                hitunganKalori.setVisibility(View.VISIBLE);
            }

            if(!kkalView.getText().toString().equals("") && !beratBadanView.getText().toString().equals("")){

                Double kalori = Double.parseDouble(beratBadanView.getText().toString()) * Double.parseDouble(kkalView.getText().toString());
                totKalori.setText(Double.toString(kalori));
                cairan.setVisibility(View.VISIBLE);
                totKal.setVisibility(View.VISIBLE);
            }

            Log.d("mlview", "afterTextChanged: " + mlView.getText().toString());
            Log.d("beratView", "afterTextChanged: " + beratBadanView.getText().toString());
            if(!mlView.getText().toString().equals("") && !beratBadanView.getText().toString().equals("")){
                double cair = Double.parseDouble(beratBadanView.getText().toString()) * Double.parseDouble(mlView.getText().toString());
                totCair.setText(Double.toString(cair));
                btn.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setRightLabelBMI (double bmiResult){
        if(bmiResult < 18.5){
            bmiStatus.setText("Underweight");
        } else if(18.5 <= bmiResult && bmiResult <= 24.9){
            bmiStatus.setText("Healthy");
        } else if(25.0 <= bmiResult && bmiResult <= 29.9){
            bmiStatus.setText("Overweight");
        } else {
            bmiStatus.setText("Obese");
        }

    }

    public static String sendGet(final String url) {
        StringBuilder result = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {
            String apiUrl = "http://10.0.2.2:8080" + url; // concatenate uri with base url eg: localhost:8080/ + uri
            URL requestUrl = new URL(apiUrl);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.connect(); // no connection is made
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return result.toString();
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
//                        response1 = response.toString();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("asd", "onErrorResponse: asd");

                    }
                });

        req.add(jsonObjectRequest);


        return result.toString();
    }

    public String addDatabase(final String url) {
        StringBuilder result = new StringBuilder();
        String response1 = "";

        //ini kalo pake virtual machine android
        String apiUrl = "http://10.0.2.2:8080" + url;

        //ini kalo pake hape, liat ip laptop lu berapa
        //connect pake wifi yang sama
        //String apiUrl = "http://192.168.1.12" + url;
        JSONObject json = new JSONObject();
        try {
            json.put("id", 10);
            json.put("nama", "student");
            json.put("tipe", 2);
            json.put("urt", "student");
            json.put("karbohidrat", 2);
            json.put("protein", 4);
            json.put("lemak", 5);
            json.put("kalori", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue req = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, apiUrl, json, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("isinya", "sendGet: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("asd", "onErrorResponse: asd");

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
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}

