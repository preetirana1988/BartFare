package com.example.preeti.bartfare;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

//import com.google.android.maps.MapView;
//import com.google.android.maps.MapView.LayoutParams;

/**
* Created by Preeti on 6/8/2015.
*/
public class InfoFragClass extends Fragment {
    private String name, abs, city,longg, latt;
    private GoogleMap gmap;




    public InfoFragClass() {
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.infofrag, container, false);
        final String url = "http://maps.google.com/maps?q="+latt+","+longg;
        TextView Name = (TextView) view.findViewById(R.id.name);
        TextView Abs = (TextView) view.findViewById(R.id.abs);
        TextView City = (TextView) view.findViewById(R.id.city);
        MapsInitializer.initialize(getActivity());

        gmap = ((MapFragment)getChildFragmentManager().findFragmentById(R.id.map)).getMap();




//        mapView = (MapView) view.findViewById(R.id.map);
//        mapView.onCreate(savedInstanceState);
//        gmap = mapView.getMap();
//        gmap.setMyLocationEnabled(true);
//        final WebView webView = (WebView) view.findViewById(R.id.mapView);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setBuiltInZoomControls(true);
//         webView.loadUrl(url);
//         webView.setWebViewClient(new WebViewClient() {
//         public boolean shouldOverrideUrlLoading(WebView view, String url) {
//             view.loadUrl(url);
//                  return true;
//                        }});

         Button infor = (Button) view.findViewById(R.id.infor);
        infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent show = new Intent(getActivity(), MainActivity.class);
                startActivity(show);
            }
        });

        Name.setText(name);
        Abs.setText(abs);
        City.setText(city);

//        webView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                               Intent show = new Intent(".Brows");
//                 startActivity(show);
//                 return false;
//            }
//        });

        return view;
    }

    public void change(String name, String abs, String city) {

        this.name = name;
        this.abs = abs;
        this.city = city;


    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void getLoc(String latt, String longg){
        this.latt = latt;
        this.longg= longg;
        Float latShow = Float.parseFloat(latt);
        Float longShow = Float.parseFloat(longg);

//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latShow, longShow), 10);
//        gmap.animateCamera(cameraUpdate);

        if(gmap!=null){

              gmap.addMarker(new MarkerOptions().
                      position(new LatLng(latShow, longShow)).
                      title(name).icon(BitmapDescriptorFactory.defaultMarker()));
        }

//        gmap.moveCamera(CameraUpdateFactory.zoomTo(new Float(13)));
    }
//    @Override
//    public void onResume() {
//        mapView.onResume();
//        super.onResume();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        mapView.onLowMemory();
    }
   }