package com.example.locationtrackk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Initialize variable
    Spinner spType;
    Button btFind;
    SupportMapFragment supportMapFragment;
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLat = 0, currentLong = 0;
    PlacesClient placesClient;


    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this
                ,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            //when permission granted
            //call method
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //when success
                    if (location != null){
                        //when location is not equal
                        //Get current Latitude
                        currentLat = location.getLatitude();
                        //Get current longitude
                        currentLong = location.getLongitude();

                        System.out.print("latitude "+ currentLat);
                        System.out.print("longitude "+ currentLong);
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                //When map is ready
                                map = googleMap;
                                // Zoom current location on map
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(currentLat,currentLong), 10
                                ));
                            }
                        });
                    }
                }
            });


        }else {
            //when permission denied
            //Request permission
            ActivityCompat.requestPermissions(MainActivity.this
                    ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44 );
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Assign variable
        spType = findViewById(R.id.sp_type);
        btFind = findViewById(R.id.bt_find);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);

        //Initialize array of place type
        String[] placeTypeList = {"atm","bank","hospital","movie_theater","restaurant"};
        //Initialize array of place name
        String[] placeNameList = {"ATM","Bank","Hospital","Movie Theater","Restaurant"};

        //set adapter on spinner
        spType.setAdapter(new ArrayAdapter<>(MainActivity.this
            , android.R.layout.simple_spinner_dropdown_item,placeNameList));


        //Initialize fuesed location provider client
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //Check permission
        btFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get selected position of spinner
                System.out.println("Hello");
                Toast.makeText(MainActivity.this, currentLat+"", Toast.LENGTH_SHORT).show();
                int i = spType.getSelectedItemPosition();
                //Initialize url
                String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" + //Url
                "?location=" + currentLat + ","+currentLong + //Location Latitude and Logitude
                "&radius=5000" + //Nearby radius
                "&types=" + placeTypeList[i] + //place type
                "&sensor=true" + //Sensor
                "&key=" + getResources().getString(R.string.google_map_key); //google map key

                //Execute place task method to download json data
                new PlaceTask().execute(url);


            }
        });

        this.getLocation();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 44){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //When permission granted
                //call method
               // getCurrentLocation();
            }
        }
    }

    private class PlaceTask extends AsyncTask<String,Integer,String> {
        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            try {
                //Initialize data
                data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //Execute parser task
            new ParserTask().execute(s);
        }
    }

    private String downloadUrl(String string) throws IOException {

        //Initialize url
        URL url = new URL(string);
        //Initialize connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //connect connection
        connection.connect();
        //Initialize input stream
        InputStream stream = connection.getInputStream();
        //Initialize buffer reader
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        //Initialize string builder
        StringBuilder builder = new StringBuilder();
        //Initialize string variable
        String line = "";
        //Use while loop
        while ((line=reader.readLine()) != null){
            //Append line
            builder.append(line);
        }
        //Get append data
        String data = builder.toString();
        //Close reader
        reader.close();
        //Return data
        return data;
    }

    private class ParserTask extends AsyncTask<String,Integer, List<HashMap<String,String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            //Create json parser class
            JsonParser jsonParser = new JsonParser();
            //Initialize hash map list
            List<HashMap<String,String>> maoList = null;
            JSONObject object = null;
            try {
                //Initialize json object
                object = new JSONObject(strings[0]);
                //Parse json object
                maoList = jsonParser.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Return map list

            return maoList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            //Clear map
            map.clear();
            //Use for loop
            for (int i = 0; i<hashMaps.size(); i++){
                //Initialize hash map
                HashMap<String,String> hashMapList = hashMaps.get(i);
                //Get latitude
                double lat = Double.parseDouble(hashMapList.get("lat"));
                //Get longitude
                double lng = Double.parseDouble(hashMapList.get("lng"));
                //Get name
                String name = hashMapList.get("name");
                //Concat latitude and longitude
                LatLng latLng = new LatLng(lat,lng);
                //Initialize marker options
                MarkerOptions options = new MarkerOptions();
                //Set position
                options.position(latLng);
                //set title
                options.title(name);
                //Add marker on map
                map.addMarker(options);



            }
        }
    }
}