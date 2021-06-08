package com.example.guideapp;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Location extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng colomboLatLon = new LatLng(6.927079, 79.861244);
        mMap.addMarker(new MarkerOptions()
                .position(colomboLatLon)
                .title("Marker in Colombo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(colomboLatLon));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                colomboLatLon, 10
        ));
        LatLng kandyLatLon = new LatLng(07.18, 80.43);
        mMap.addMarker(new MarkerOptions()
                .position(colomboLatLon)
                .title("Marker in Kandy"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kandyLatLon));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                colomboLatLon, 10
        ));


        //When map is loaded
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //When clicked on map
                //Initialize marker options
                MarkerOptions markerOptions = new MarkerOptions();
                //Set position of marker
                markerOptions.position(latLng);
                //Set title of marker
                markerOptions.title(latLng.latitude + ":" + latLng.longitude);
                //Remove all marker
                googleMap.clear();
                //Animation to zoom the marker
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        latLng, 10
                ));
                //Add marker on map
                googleMap.addMarker(markerOptions);

            }
        });

    }
}