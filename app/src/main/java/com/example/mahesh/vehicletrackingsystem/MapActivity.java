package com.example.mahesh.vehicletrackingsystem;

/**
 * Created by MAHESH on 12/15/2015.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mahesh.vehicletrackingsystem.app.AppController;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapActivity extends AppCompatActivity {

    GoogleMap googleMap;
    private ProgressDialog pDialog;
    ArrayList<LatLng> points;
    LatLng polyline;
    double lat;
    double lng;

String Latitude;
    String Longitude;
    String custid,vehid,sped;

    Spinner spin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.map);

        Intent i=getIntent();
        Latitude=i.getStringExtra("latin");
        Longitude=i.getStringExtra("lagin");
        custid=i.getStringExtra("cid");
        vehid=i.getStringExtra("vid");
        sped=i.getStringExtra("spd");

        pDialog=new ProgressDialog(this);
        LatLng latLng = new LatLng(13.0285, 77.5650);

        ///Latitude.replaceAll("\"\\\\d+.*\", \"\"","");
       // Longitude.replaceAll("\"\\\\d+.*\", \"\"","");



       // double latitude = i.getDoubleExtra("latin", 0);

        // Receiving longitude from MainActivity screen
       // double longitude = i.getDoubleExtra("lagin", 0);
        Log.d("LATITUDE",Latitude);
        Log.d("LONGITUDE",Longitude);

        Log.d("CUSTOMER",custid);
        Log.d("VEHICLE",vehid);
        Log.d("SPEED",sped);



        spin = (Spinner) findViewById(R.id.spinner);



        //LatLng position = new LatLng(latitude, longitude);

        // Instantiating MarkerOptions class
       // MarkerOptions options = new MarkerOptions();

        // Setting position for the MarkerOptions
        //options.position(position);



        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //points = new ArrayList<LatLng>();


        //points.add(new LatLng(13.0319572, 77.560509));
       // points.add(new LatLng(12.9906324, 77.553344));
       // points.add(new LatLng(12.9698858, 77.5332796));
       // points.add(new LatLng(12.9241437, 77.5218127));

        // Getting reference to the SupportMapFragment of activity_main.xml
        //SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        // Getting GoogleMap object from the fragment
        //googleMap = fm.getMap();

        // Enabling MyLocation Layer of Google Map
        //googleMap.setMyLocationEnabled(true);


        /*if (googleMap != null) {
            return;
        }
        googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        if (googleMap == null) {
            return;
        }
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(true);*/


        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());


        if(status!= ConnectionResult.SUCCESS){ // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        }else { // Google Play Services are available
            SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            // Getting Google Map
            googleMap = fragment.getMap();
            //if (!Latitude .equals("")&&Longitude.equals("")) {
                googleMap.addMarker(new MarkerOptions().position(new LatLng(13.0285, 77.5650)).title("Marker").snippet("Snippet"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));

                // Enable MyLocation Layer of Google Map
                //googleMap.setMyLocationEnabled(true);

           // }

            // Enabling MyLocation in Google Map
            //googleMap.setMyLocationEnabled(true);

            //setUpMap();


            //LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

            //MarkerOptions options = new MarkerOptions();

            // Setting position for the MarkerOptions
            //options.position(point);
        }


            //MarkerOptions markerOptions = new MarkerOptions();
        //Marker pos_Marker =  googleMap.addMarker(new MarkerOptions().position(starting).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_laumcher)).title("Starting Location").draggable(false));


        //googleMap.addMarker(new MarkerOptions().position(new LatLng(lat , lng)).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_pin)).title("Marker"));
        //googleMap.addMarker(markerOptions);

       // PolylineOptions polylineOptions = new PolylineOptions();

        // Create polyline options with existing LatLng ArrayList
       // polylineOptions.addAll(points);
       // polylineOptions
               // .width(5)
               // .color(Color.RED);

        // Adding multiple points in map using polyline and arraylist
       // googleMap.addPolyline(polylineOptions);


        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }
    private void setUpMap() {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(Latitude), Double.valueOf(Longitude))).title("Marker").snippet("Snippet"));

        // Enable MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(true);

        // Get LocationManager object from System Service LOCATION_SERVICE
       // LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
       // Criteria criteria = new Criteria();

        // Get the name of the best provider
       // String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        //Location myLocation = locationManager.getLastKnownLocation(provider);

        // set map type
        //googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Get latitude of the current location
        //double latitude = myLocation.getLatitude();

        // Get longitude of the current location
        //double longitude = myLocation.getLongitude();

        // Create a LatLng object for the current location
        //LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
       // googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));
       // googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!").snippet("Consider yourself located"));
    }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
