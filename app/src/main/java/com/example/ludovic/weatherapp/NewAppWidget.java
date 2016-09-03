package com.example.ludovic.weatherapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.ludovic.weatherapp.Model.WeatherVille;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider implements LocationListener {

    //private static String city;
    //private static String meteo;
    //private static String temp;
    private double lat;
    private double lng;
    private LocationManager locationManager;

    private WeatherVille weatherVille;

    private Context context;
    private AppWidgetManager appWidgetManager;
    private int[] appWidgetIds;

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        Log.d("TAG","--------------");

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.textViewCityWidget, weatherVille.getName());
        views.setTextViewText(R.id.textViewMeteoWidget, weatherVille.getWeather_Description());
        views.setTextViewText(R.id.textViewTemperatureWidget, weatherVille.getMain_temp()+"");
        Bitmap bitmap = getBitmapFromURL("http://openweathermap.org/img/w/" + weatherVille.getWeather_Icon() + ".png");
        if (bitmap!= null)
            views.setImageViewBitmap(R.id.imageViewMeteoWidget, bitmap);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            // Log exception
            return null;
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        Log.d("TAG","widget new update");

        this.context = context;
        this.appWidgetManager = appWidgetManager;
        this.appWidgetIds = appWidgetIds;

        getGps(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public void getGps(Context context) {

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        lat = location.getLatitude();
        lng = location.getLongitude();

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(this);
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



    public void dataApi(final double latitude, final double longitude) {

        Log.d("TAG","DEBUT API");

        final Handler mHandler = new Handler();

        new Thread() {
            public void run() {
                try {

                    final String response = Util.requestApi("http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=metric&lang=fr&APPID=d32d2d9784630a43b8479698062afe1f");

                    final JSONObject data = new JSONObject(response);
                    weatherVille = new WeatherVille(data);

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            update();
                        }
                    });

                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public void update(){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}

