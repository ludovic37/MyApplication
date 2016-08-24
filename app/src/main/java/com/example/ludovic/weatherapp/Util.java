package com.example.ludovic.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Ludovic on 28/07/16.
 */
public class Util {

    public static String APIKEY = "d32d2d9784630a43b8479698062afe1f";
    //public static Context context = this;

    public static boolean isActiveNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public static String converTime(long timestamp){
        SimpleDateFormat formater = new SimpleDateFormat("EE");
        //System.out.println(formater.format(timestamp));
        return formater.format(timestamp);
    }

    public static String requestApi(String urlApi) throws IOException {

        URL url = new URL(urlApi);
        HttpURLConnection connection = null;
        connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String response = "";
        String line = "";
        while ((line = reader.readLine()) != null) {
            response += line;
        }
        reader.close();
        return response;

    }
}
