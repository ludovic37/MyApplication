package com.example.ludovic.weatherapp.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ludovic on 29/07/16.
 */
public class WeatherVille {

    private double lat;
    private double lon;
    private double temp;
    private int id;

    private String description;
    private String icon;
    private String name;

    private JSONObject jsonObject;

    public WeatherVille(){

    }

    public WeatherVille(JSONObject jsonObject) throws JSONException {

            //Log.d("TAG","-----------");
            //Log.d("TAG",jsonObject.toString());

            JSONArray array = jsonObject.getJSONArray("weather");
            JSONObject weather = array.getJSONObject(0);
            JSONObject main = jsonObject.getJSONObject("main");
            JSONObject coordonner = jsonObject.getJSONObject("coord");
            //JSONObject sys = jsonObject.getJSONObject("sys");

            this.name = jsonObject.getString("name");
            this.description = weather.getString("description");
            this.icon = weather.getString("icon");
            this.temp = main.getDouble("temp");
            this.lat = coordonner.getDouble("lat");
            this.lon = coordonner.getDouble("lon");
            this.id = jsonObject.getInt("id");
            this.jsonObject = jsonObject;

            Log.d("TAG","New Ville");
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public double getTemp() {
        return temp;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public String toString(){
        return "name: "+name
                +" lat: "+lat
                +" lon: "+lon;
    }


}
