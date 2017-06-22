package com.example.kriscool.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String jednostki= "and%20u=\"c\"";
    EditText e;
    int index = 0;
    Spinner spin;
    List<String> ulubone = new ArrayList<String>();
    Weather w = new Weather();
    NoteDAO noteDAO;
    String cityUrl = "Moskwa";
    Button confirm;
    EditText latitudeText;
    EditText longtitudeText;
    EditText refreshText;
    EditText city;
    TextView message;
    double latitude = 51.75;
    double longitude = 19.46;
    int refreshtime = 15;
    AlertDialog alterdiagog;
    ViewPager view;
    List<String> fragments;
    View dialog;
    String output = "";
    boolean czyOdczytano = false;
    List<Note> allNotes;
    int indexForDB = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState != null){  getData();}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteDAO = new NoteDAO(this);
        view = (ViewPager) findViewById(R.id.viewPager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        fragments = new ArrayList<>();
        fragments.add(Sun.class.getName());
        fragments.add(moon.class.getName());
        fragments.add(info.class.getName());
        fragments.add(futureday.class.getName());
        fragments.add(wind.class.getName());
        PagerAdapter pagerAdapter = new MainActivity.MyPagerAdapter(getSupportFragmentManager());
        try{
            view.setAdapter(pagerAdapter);
        }catch(Exception e) {}
        setSupportActionBar(toolbar);


        allNotes = noteDAO.getAllNotes();

        for(Note x:allNotes){
            ulubone.add(indexForDB,x.getNoteText());
            index++;
            indexForDB++;
        }


        if(czyOdczytano != true) {
            getDateIn();
        }
    }
    public void getDateIn(){
        if (accessToInternet()) {
            getDataFromInternet();
            czyOdczytano = true;
        } else {
            odczyt();
            getData();
            czyOdczytano = true;
        }
    }
    private void getData() {
        Toast.makeText(getApplicationContext(),
                "Brak połączenia internetowego. Pobieram ostatnia zapisana pozycje", Toast.LENGTH_LONG).show();
                        try{
                            JSONObject responsee = new JSONObject(output);
                            JSONObject query = responsee.getJSONObject("query");
                            JSONObject results = query.getJSONObject("results");
                            JSONObject channel = results.getJSONObject("channel");

                            JSONObject item = channel.getJSONObject("item");
                            JSONObject condition = item.getJSONObject("condition");
                            JSONObject loc = channel.getJSONObject("location");
                            JSONObject atmosphere = channel.getJSONObject("atmosphere");
                            JSONObject wind = channel.getJSONObject("wind");


                            String descript = item.getString("description");
                            String[] descArr  = descript.split("<BR />");

                            d0 = descArr[5];
                            d1 = descArr[6];
                            d2 = descArr[7];
                            d3 = descArr[8];
                            d4 = descArr[9];


                            w.setMiasto(loc.getString("city"));
                            w.setKraj(loc.getString("country"));
                            w.setCisnienie(atmosphere.getString("pressure"));
                            w.setDlugosc(item.getString("lat"));
                            w.setSzerokosc(item.getString("long"));
                            w.setTemperatura(condition.getString("temp"));
                            w.setOpis(item.getString("description"));
                            longitude =  Double.parseDouble(item.getString("lat"));
                            latitude = Double.parseDouble(item.getString("long"));

                            w.setKierunekWiatru(wind.getString("direction"));
                            w.setSilaWiatru(wind.getString("speed"));
                            w.setWidocznosc(atmosphere.getString("visibility"));
                            w.setWilgotnosc(atmosphere.getString("humidity"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


    public void zapis(String string){
        String filename = "myfile";
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void odczyt(){
            FileInputStream inputStream;
            try {
                byte[] b = new byte[3000];
                inputStream = openFileInput("myfile");
                int i = inputStream.read(b);
                if(i == -1){

                } else {

                }
                output = new String(b);
                inputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action) {
            showDialog();
        } else if(id == R.id.action_favorite){
            Toast.makeText(getApplicationContext(),
                    "Refresh", Toast.LENGTH_LONG).show();
            getDateIn();
       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(view.getCurrentItem() == 0){
            super.onBackPressed();
        } else {
            view.setCurrentItem(view.getCurrentItem() - 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }
    Button murica;
    Button europ;
 private void showDialog() {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialog = inflater.inflate(R.layout.dialog, null);
        dialogBuilder.setView(dialog);

     alterdiagog = dialogBuilder.create();
     city = (EditText) dialog.findViewById(R.id.cityDzialaj);
     city.setText(cityUrl);
       murica = (Button) dialog.findViewById(R.id.murica);
       europ = (Button) dialog.findViewById(R.id.euro);
     int a = (int) latitude;
        confirm = (Button) dialog.findViewById(R.id.ConfirmButton);
        e = (EditText) dialog.findViewById(R.id.ulubione);
        spin = (Spinner) dialog.findViewById(R.id.spinner) ;


     murica.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {

            jednostki = "";
             getDataFromInternet();
             alterdiagog.dismiss();

         }
     });

     europ.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {

             jednostki = "and%20u=\"c\"";
             getDataFromInternet();
             alterdiagog.dismiss();

         }
     });
     confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                cityUrl = city.getText().toString();
                getDataFromInternet();
                alterdiagog.dismiss();

            }
        });
     Button add = (Button) dialog.findViewById(R.id.dodaj);
     Button usun = (Button) dialog.findViewById(R.id.skasuj);
     add.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
             boolean isintab = false;
             for(String x:ulubone){
                 if(x.equals( e.getText().toString())){
                     isintab = true;
                 }
             }
            if(index <5 && isintab == false && !("").equals(e.getText().toString())) {
                ulubone.add(index, e.getText().toString());
                index++;
                addNewNote(e.getText().toString());
            }

         }
     });

     usun.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
             int indexusun = 0;
             for(Note x:allNotes) {
                 if (x.getNoteText().equals(e.getText().toString())){
                     ulubone.remove(indexusun);
                 }
                 indexusun++;
             }
             noteDAO.deleteNoteById(indexusun);

         }
     });

     // Creating adapter for spinner
     ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ulubone);

     // Drop down layout style - list view with radio button
     dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

     // attaching data adapter to spinner
     spin.setAdapter(dataAdapter);
     spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

             switch (position) {
                 case 0:
                     break;
                 case 1:
                     cityUrl = spin.getSelectedItem().toString();

                     getDataFromInternet();
                     alterdiagog.dismiss();
                     break;
                 case 2:
                       cityUrl = spin.getSelectedItem().toString();

                     getDataFromInternet();

                     alterdiagog.dismiss();
                     break;
                 case 4:
                      cityUrl = spin.getSelectedItem().toString();

                     getDataFromInternet();

                     alterdiagog.dismiss();
                     break;
                 case 3:
                       cityUrl = spin.getSelectedItem().toString();

                     getDataFromInternet();

                     alterdiagog.dismiss();
                     break;
                 case 5:
                       cityUrl = spin.getSelectedItem().toString();

                     getDataFromInternet();

                     alterdiagog.dismiss();
                     break;
             }
         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {

             // sometimes you need nothing here
         }
     });
     alterdiagog.show();
    }


    public void addNewNote(String a) {
        Note note = new Note();
        if (a.length() > 0) {
            note.setNoteText(a);
        }
        noteDAO.insertNote(note);
    }

    public void removeNote(Note note) {
        noteDAO.deleteNoteById(note.getId());
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public List<String> fragmentsList = new ArrayList<>();
        private int Page = 5;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            fragmentsList = fragments;
        }


        @Override
        public Fragment getItem(int position) {
            return Fragment.instantiate(getBaseContext(), fragmentsList.get(position));
        }
        @Override
        public int getCount() {
            return Page;
        }


    }
    public boolean accessToInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("calosc",output);
        outState.putDouble("latitude",latitude);
        outState.putDouble("longitudes",longitude);
        outState.putInt("refreshtime",refreshtime);
        outState.putString("city",cityUrl);
        outState.putBundle("key",outState);
        outState.putBoolean("czyzapisano",czyOdczytano);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        latitude = savedInstanceState.getDouble("latitude");
        longitude = savedInstanceState.getDouble("longitudes");
        refreshtime = savedInstanceState.getInt("refreshtime");
        cityUrl = savedInstanceState.getString("city");
        output = savedInstanceState.getString("calosc");
        czyOdczytano = savedInstanceState.getBoolean("czyzapisano");


    }
    String d1;

    public String getD1() {
        return d1;
    }

    public void setD1(String d1) {
        this.d1 = d1;
    }

    public String getD2() {
        return d2;
    }

    public void setD2(String d2) {
        this.d2 = d2;
    }

    public String getD3() {
        return d3;
    }

    public void setD3(String d3) {
        this.d3 = d3;
    }

    public String getD4() {
        return d4;
    }

    public void setD4(String d4) {
        this.d4 = d4;
    }

    public String getD0() {
        return d0;
    }

    public void setD0(String d0) {
        this.d0 = d0;
    }

    String d0;
    String d2;
    String d3;
    String d4;
    public void getDataFromInternet(){

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"+cityUrl+"%22)"+ jednostki +"&format=json",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                        String response = new String(responseBody);

                        zapis(response);
                        output = response;
                        try{
                            JSONObject responsee = new JSONObject(response);
                            JSONObject query = responsee.getJSONObject("query");
                            JSONObject results = query.getJSONObject("results");
                            JSONObject channel = results.getJSONObject("channel");

                            JSONObject item = channel.getJSONObject("item");
                            JSONObject condition = item.getJSONObject("condition");
                            JSONObject loc = channel.getJSONObject("location");
                            JSONObject atmosphere = channel.getJSONObject("atmosphere");
                            JSONObject wind = channel.getJSONObject("wind");


                            String descript = item.getString("description");
                            String[] descArr  = descript.split("<BR />");

                            d0 = descArr[5];
                            d1 = descArr[6];
                           d2 = descArr[7];
                           d3 = descArr[8];
                           d4 = descArr[9];


                            w.setMiasto(loc.getString("city"));
                            w.setKraj(loc.getString("country"));
                            w.setCisnienie(atmosphere.getString("pressure"));
                            w.setDlugosc(item.getString("lat"));
                            w.setSzerokosc(item.getString("long"));
                            w.setTemperatura(condition.getString("temp"));
                            w.setOpis(item.getString("description"));
                            longitude =  Double.parseDouble(item.getString("lat"));
                            latitude = Double.parseDouble(item.getString("long"));

                           w.setKierunekWiatru(wind.getString("direction"));
                            w.setSilaWiatru(wind.getString("speed"));
                          w.setWidocznosc(atmosphere.getString("visibility"));
                           w.setWilgotnosc(atmosphere.getString("humidity"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
    }
    public Weather getWheather(){return w;}
    public int getRefTime() { return refreshtime;}
    public double getLatitude(){
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }
}



