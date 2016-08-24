package com.example.ludovic.weatherapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ludovic.weatherapp.Model.WeatherVille;
import com.example.ludovic.weatherapp.Model.WheatherPrevision;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Main2Activity extends AppCompatActivity implements LocationListener {

    private Handler mHandler;
    private LocationManager locationManager;
    final int MY_PERMISSIONS_REQUEST_GEOLOC = 123;

    private RelativeLayout mRelativeLayoutInternet;
    private RelativeLayout mRelativeLayoutContenue;
    ProgressDialog myProgressDialog;

    private double lat = 0;
    private double lng = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRelativeLayoutInternet = (RelativeLayout) findViewById(R.id.relativeLayoutInternet);
        mRelativeLayoutContenue = (RelativeLayout) findViewById(R.id.relativeLayoutcontenue);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        mRelativeLayoutInternet.setVisibility(View.GONE);
        mRelativeLayoutContenue.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);

        if (!Util.isActiveNetwork(this)) {
            mRelativeLayoutInternet.setVisibility(View.VISIBLE);
            mRelativeLayoutContenue.setVisibility(View.GONE);
        } else {

            myProgressDialog = ProgressDialog.show(Main2Activity.this, "", "Chargement", true);

            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Main2Activity.this, FavorisActivity.class));
                }
            });

            lat = getIntent().getDoubleExtra("lat", 0);
            lng = getIntent().getDoubleExtra("lon", 0);

            if (lat == 0 && lng == 0) {
                Log.d("TAG","DEBUT GPS");
                getGps();
            } else {
                Log.d("Lat/Lng", lat + "/" + lng);
                dataApi(lat, lng);
            }
        }
    }

    public void dataApi(final double latitude, final double longitude) {

        Log.d("TAG","DEBUT API");

        mHandler = new Handler();

        new Thread() {
            public void run() {
                try {

                    final String response = Util.requestApi("http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=metric&lang=fr&APPID=d32d2d9784630a43b8479698062afe1f");
                    final String response2 = Util.requestApi("http://api.openweathermap.org/data/2.5/forecast/daily?lat=" + latitude + "&lon=" + longitude + "&cnt=4&units=metric&lang=fr&APPID=d32d2d9784630a43b8479698062afe1f");

                    final JSONObject data = new JSONObject(response);
                    final JSONObject data2 = new JSONObject(response2);

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            myProgressDialog.dismiss();
                            updateUIHeader(data);
                            updateUI(data2);
                            Log.d("JSON", response);
                        }
                    });

                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_position:
                // Comportement du bouton "A Propos"

                Toast.makeText(this, "Map position", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Main2Activity.this, MapsActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lng);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateUI(JSONObject data) {

        try {
            WheatherPrevision wheatherPrevision = new WheatherPrevision(data);
            updatePrevision(wheatherPrevision);
            mRelativeLayoutInternet.setVisibility(View.GONE);
            mRelativeLayoutContenue.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            Log.d("TAG 3 day", "error");
        }
    }

    public void updatePrevision(WheatherPrevision wheatherPrevision) {


        double a = wheatherPrevision.getMax()[0];

        Log.d("tag","tag");

        //((TextView) findViewById(R.id.textViewTemperature)).setText(Math.round(weatherVille.getTemp() * 10.0)/10.0 + "°C");

        ((TextView) findViewById(R.id.textViewTempMaxDay1)).setText(Math.round(wheatherPrevision.getMax()[0]*10.0)/10.0 + "°C");
        ((TextView) findViewById(R.id.textViewTempMinDay1)).setText(Math.round(wheatherPrevision.getMin()[0]*10.0)/10.0 + "°C");
        ((TextView) findViewById(R.id.textViewDay1)).setText(Util.converTime(wheatherPrevision.getDt()[0]));

        Picasso.with(this).load("http://openweathermap.org/img/w/" + wheatherPrevision.getIcon()[0] + ".png").fit().into((ImageView) findViewById(R.id.imageViewDay1));
        Picasso.with(this).load("http://openweathermap.org/img/w/" + wheatherPrevision.getIcon()[0] + ".png").fit().into((ImageView) findViewById(R.id.imageViewDay1));


        ((TextView) findViewById(R.id.textViewTempMaxDay2)).setText(Math.round(wheatherPrevision.getMax()[1]*10.0)/10.0 + "°C");
        ((TextView) findViewById(R.id.textViewTempMinDay2)).setText(Math.round(wheatherPrevision.getMin()[1]*10.0)/10.0 + "°C");
        ((TextView) findViewById(R.id.textViewDay2)).setText(Util.converTime(wheatherPrevision.getDt()[1]));
        Picasso.with(this).load("http://openweathermap.org/img/w/" + wheatherPrevision.getIcon()[1] + ".png").fit().into((ImageView) findViewById(R.id.imageViewDay2));


        ((TextView) findViewById(R.id.textViewTempMaxDay3)).setText(Math.round(wheatherPrevision.getMax()[2]*10.0)/10.0 + "°C");
        ((TextView) findViewById(R.id.textViewTempMinDay3)).setText(Math.round(wheatherPrevision.getMin()[2]*10.0)/10.0 + "°C");
        ((TextView) findViewById(R.id.textViewDay3)).setText(Util.converTime(wheatherPrevision.getDt()[2]));
        Picasso.with(this).load("http://openweathermap.org/img/w/" + wheatherPrevision.getIcon()[2] + ".png").fit().into((ImageView) findViewById(R.id.imageViewDay3));

        ((TextView) findViewById(R.id.textViewTempMaxDay4)).setText(Math.round(wheatherPrevision.getMax()[3]*10.0)/10.0 + "°C");
        ((TextView) findViewById(R.id.textViewTempMinDay4)).setText(Math.round(wheatherPrevision.getMin()[3]*10.0)/10.0 + "°C");
        ((TextView) findViewById(R.id.textViewDay4)).setText(Util.converTime(wheatherPrevision.getDt()[3]));
        Picasso.with(this).load("http://openweathermap.org/img/w/" + wheatherPrevision.getIcon()[3] + ".png").fit().into((ImageView) findViewById(R.id.imageViewDay4));

    }

    public void updateUIHeader(JSONObject data) {

        try {

            Log.d("Tag", " ----------- test");
            Log.d("Tag", data.toString());

            WeatherVille weatherVille = new WeatherVille(data);

            lat = weatherVille.getLat();
            lng = weatherVille.getLon();

            ((TextView) findViewById(R.id.textViewCity)).setText(weatherVille.getName());
            ((TextView) findViewById(R.id.textViewMeteo)).setText(weatherVille.getDescription());



            //((TextView) findViewById(R.id.textViewTemperature)).setText(weatherVille.getTemp() + "°C");
            ((TextView) findViewById(R.id.textViewTemperature)).setText(Math.round(weatherVille.getTemp() * 10.0)/10.0 + "°C");
            Picasso.with(this).load("http://openweathermap.org/img/w/" + weatherVille.getIcon() + ".png").fit().centerCrop().into((ImageView) findViewById(R.id.imageViewMeteo));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getGps() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_GEOLOC);
        } else {

            Log.d("TAG","DEBUT provide");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_GEOLOC:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d("GPS", "Activer");
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                } else {
                    Log.d("GPS", "non activer");
                    getGps();
                }
                break;
        }
    }


    @Override
    public void onLocationChanged(Location location) {

        lat = location.getLatitude();
        lng = location.getLongitude();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(this);
            Log.d("TAG", "geoloc recu");
            Log.d("TAG", "geoloc recu");
            dataApi(lat,lng);
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
