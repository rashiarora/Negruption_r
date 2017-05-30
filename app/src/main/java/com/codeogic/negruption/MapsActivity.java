package com.codeogic.negruption;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    public static final int REQUEST_CODE=201;

    LocationManager locationManager;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    LatLng latlng;
    List<LatLng> ll;
    List<Address> addresses;


    void initViews() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"Enable permissions in settings",Toast.LENGTH_LONG).show();
        }


        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Retrieving...");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initViews();

        if (checkPermission()){

            // Toast.makeText(this,"Permissions Are Already Granted",Toast.LENGTH_LONG).show();
        }
        else {
            // Toast.makeText(this,"Permissions are not granted ",Toast.LENGTH_LONG).show();
            requestPermission();
        }


    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION}, REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean FineLocationPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean CoarseLocationPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (FineLocationPermission && CoarseLocationPermission ) {

                        Toast.makeText(MapsActivity.this, "Permissions Granted", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(MapsActivity.this,"Permissions Denied",Toast.LENGTH_LONG).show();

                        AlertDialog.Builder builder=new AlertDialog.Builder(this);
                        builder.setTitle("Permissions Required");
                        builder.setMessage("Kindly Grant The Permissions For Proper Working of Application ");

                        builder.setPositiveButton("O K A Y", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                requestPermission();
                             //   Toast.makeText(MapsActivity.this,"Clicked Okay",Toast.LENGTH_LONG).show();


                            }
                        });


                        builder.create().show();

                        // finish();


                    }
                }

                break;
        }
    }

    public boolean checkPermission() {

        int result = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION);


        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED ;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
         progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.GET, Util.RETRIEVE_LOCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("location");

                    String location="";

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jObj = jsonArray.getJSONObject(i);

                        location = jObj.getString("place");
                        //Log.i("location",location);
                        if(Geocoder.isPresent()){
                            try {
                                //String location = "theNameOfTheLocation";
                                Geocoder gc = new Geocoder(getApplicationContext());
                                addresses= gc.getFromLocationName(location, 5); // get the found Address Objects
                                //Log.i("address",addresses.toString());
                                ll = new ArrayList<>(addresses.size());
                                LatLng india = new LatLng(21.7679, 78.8718);
                               // LatLng latlng;// A list to save the coordinates if they are available
                                for(Address a : addresses){
                                    if(a.hasLatitude() && a.hasLongitude()){
                                        latlng = new LatLng(a.getLatitude(), a.getLongitude());
                                        ll.add(latlng);
                                        //Log.i("latlng",latlng.toString());

                                        mMap.addMarker(new MarkerOptions().position(latlng).title(location).snippet(location));
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(india,3.89F));
                                        mMap.setOnInfoWindowClickListener(MapsActivity.this);


                                    }

                                }


                            } catch (IOException e) {
                                e.printStackTrace();

                            }
                        }


                    }

                    progressDialog.dismiss();

                }catch (Exception e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                  //  Toast.makeText(MapsActivity.this,"Some Exception"+ e,Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
              //  Toast.makeText(MapsActivity.this,"Some Error"+ error,Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(request);


    }


    @Override
    public void onInfoWindowClick(Marker marker) {

        String loc = marker.getTitle();
       // Toast.makeText(this, "Info window clicked: "+loc,
              //  Toast.LENGTH_LONG).show();
        Intent intent= new Intent(MapsActivity.this,LocationBasedStory.class);
       intent.putExtra("location",loc);
        startActivity(intent);


    }
}
