package com.example.preeti.bartfare;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity {

    public Spinner sourceSpin;
    public Spinner destSpin;
    public Button buttonCalculate;
    public TextView textview;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;


    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    ArrayList<String> myList;

    Intent infoShow = null;
    public Stations stations =  new Stations();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoShow = new Intent(MainActivity.this,DrawerLayoutTile.class);
        final Button info = (Button)findViewById(R.id.info);
        buttonCalculate = (Button) findViewById(R.id.button);
        sourceSpin = (Spinner) findViewById(R.id.spinner1);
        destSpin = (Spinner) findViewById(R.id.spinner2);
        textview = (TextView) findViewById(R.id.textView2);
        final String urlKey = "&key=MW9S-E7SL-26DU-VV8V";
        String url = "http://api.bart.gov/api/stn.aspx?cmd=stns";
        final String urlMain = url +urlKey;
        new BartNameCall().execute(urlMain);        


        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stations.setFullNa(sourceSpin.getSelectedItem().toString());
                stations.setFullNa2(destSpin.getSelectedItem().toString());

                String get1 = stations.changeToAbbr(stations.getFullNa());
                String get2 = stations.changeToAbbr(stations.getFullNa2());
                String url1 = get1;
                String url2 = get2;
                String url = "http://api.bart.gov/api/sched.aspx?cmd=fare";
                String finalUrl = url + "&orig="+ url1 + "&dest="+ url2 +"&date=today" + urlKey;
                new BartApiCall().execute(finalUrl);
            }
        });
        info.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              startActivity(infoShow);
            }
            });
    }

    public void showFare(String str) {
        textview.setText("$" + str);
    }

   private class BartNameCall extends AsyncTask<String,Void,ArrayList<String>>{

        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> fullNameList = new ArrayList<String>();
        ArrayList<String> AbbreList = new ArrayList<String>();
        ArrayList<String> cityList = new ArrayList<String>();
        ArrayList<String> latList = new ArrayList<String>();
        ArrayList<String> longList = new ArrayList<String>();
        String fuNa = null;
        String abbr = null;
        String city = null;
        String longg =null;
        String lat = null;
        @Override
        protected ArrayList<String> doInBackground(String... params) {
            String urlString= params[0];

            InputStream in = null;

            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
//                in = new BufferedInputStream(urlConnection.getInputStream());
            } catch (Exception e ) {
                System.out.println(e.getMessage());

            }


            XmlPullParserFactory pullParserFactory;

            try {
                pullParserFactory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = pullParserFactory.newPullParser();

                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                int eventType = parser.getEventType();


                while( eventType!= XmlPullParser.END_DOCUMENT) {
                    String name = null;

                    switch(eventType)
                    {
                        case XmlPullParser.START_TAG:
                            name = parser.getName();

                            if( name.equals("name")) {
                                fuNa = parser.nextText();
                                fullNameList.add(fuNa);
                            }
                            if (name.equals("abbr")) {
                                abbr = parser.nextText();
                                AbbreList.add(abbr);
                            }
                            if (name.equals("city")){
                            city = parser.nextText();
                            cityList.add(city);
                            }
                            if (name.equals("gtfs_longitude")){
                                longg = parser.nextText();
                                longList.add(longg);
                            }
                           else if (name.equals("gtfs_latitude")){
                                    lat = parser.nextText();
                                    latList.add(lat);
                            }
                        break;

                        case XmlPullParser.END_TAG:
                            break;
                    }

                    eventType = parser.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return list;

        }

        @Override
        protected void onPostExecute(ArrayList<String> arrayList) {
            ArrayAdapter dataAdapter = new ArrayAdapter(MainActivity.this,R.layout.spinner_item,fullNameList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ArrayAdapter dataAdapter1 = new ArrayAdapter(MainActivity.this,R.layout.spinner_item, AbbreList);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sourceSpin.setAdapter(dataAdapter);
            destSpin.setAdapter(dataAdapter);
            stations.setListA(fullNameList);
            stations.setListB(AbbreList);
            infoShow = new Intent(MainActivity.this, DrawerLayoutTile.class);
            infoShow.putExtra("FuNameLst", fullNameList);
            infoShow.putExtra("AbbrLst",AbbreList);
            infoShow.putExtra("City",cityList);
            infoShow.putExtra("Latitude",latList);
            infoShow.putExtra("Longitude",longList);



        }
    }

    //
    class BartApiCall extends AsyncTask<String,Void,String>{
        String fare = null;
        @Override
        protected String doInBackground(String... params) {
            String urlString= params[0];

            InputStream in = null;

            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
            } catch (Exception e ) {
                System.out.println(e.getMessage());

            }


            XmlPullParserFactory pullParserFactory;

            try {
                pullParserFactory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = pullParserFactory.newPullParser();

                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                int eventType = parser.getEventType();


                while( eventType!= XmlPullParser.END_DOCUMENT) {
                    String name = null;

                    switch(eventType)
                    {
                        case XmlPullParser.START_TAG:
                            name = parser.getName();

                            if( name.equals("fare")) {
                                fare = parser.nextText();
                            }
                            break;

                        case XmlPullParser.END_TAG:
                            break;
                    }

                    eventType = parser.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return fare;
        }

        @Override
        protected void onPostExecute(String str) {
            str = fare;
            showFare(str);
                   }
    }
}

