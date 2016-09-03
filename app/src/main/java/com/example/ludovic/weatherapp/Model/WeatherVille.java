package com.example.ludovic.weatherapp.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ludovic on 29/07/16.
 */
public class WeatherVille {

    private double coord_lat;
    private double coord_lon;

    private int weather_id;
    private String weather_main;
    private String weather_description;
    private String weather_icon;

    private int sys_type;
    private int sys_id;
    private double sys_message;
    private String sys_country;
    private long sys_sunrise;
    private long sys_sunset;

    private double main_temp;
    private int main_pressure;
    private int main_humidity;
    private double main_temp_min;
    private double main_temp_max;

    private double wind_speed;
    private int wind_deg;

    private int cloud_all;

    private int id;
    private int visibility;
    private long dt;
    private String name;


    private JSONObject jsonObject;

    public WeatherVille(){

    }

    public WeatherVille(JSONObject jsonObject) throws JSONException {

        JSONObject jsonObjectCoord = jsonObject.getJSONObject("coord");

        this.coord_lat = jsonObjectCoord.getDouble("lat");
        this.coord_lon = jsonObjectCoord.getDouble("lon");

        JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);

        this.weather_id = jsonObjectWeather.getInt("id");
        this.weather_main = jsonObjectWeather.getString("main");
        this.weather_description = jsonObjectWeather.getString("description");
        this.weather_icon = jsonObjectWeather.getString("icon");

        JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");

        this.sys_type = jsonObjectSys.getInt("type");
        this.sys_id = jsonObjectSys.getInt("id");
        this.sys_message = jsonObjectSys.getDouble("message");
        this.sys_country = jsonObjectSys.getString("country");
        this.sys_sunrise = jsonObjectSys.getLong("sunrise");
        this.sys_sunset = jsonObjectSys.getLong("sunset");

        JSONObject jsonObjectMain = jsonObject.getJSONObject("main");

        this.main_temp = jsonObjectMain.getDouble("temp");
        this.main_pressure = jsonObjectMain.getInt("pressure");
        this.main_humidity = jsonObjectMain.getInt("humidity");
        this.main_temp_min = jsonObjectMain.getDouble("temp_min");
        this.main_temp_max = jsonObjectMain.getDouble("temp_max");

        JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");

        this.wind_speed = jsonObjectWind.getDouble("speed");
        this.wind_deg = jsonObjectWind.getInt("deg");

        JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");

        this.cloud_all = jsonObjectCloud.getInt("all");


        this.id = jsonObject.getInt("id");
        this.visibility = jsonObject.getInt("visibility");
        this.dt = jsonObject.getLong("dt");
        this.name = jsonObject.getString("name");

        this.jsonObject = jsonObject;

    }

    public double getCoord_Lat() {
        return coord_lat;
    }

    public double getCoord_Lon() {
        return coord_lon;
    }

    public double getMain_temp() {
        return main_temp;
    }

    public String getWeather_Description() {
        return weather_description;
    }

    public String getWeather_Icon() {
        return weather_icon;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setCoord_Lat(double lat) {
        this.coord_lat = lat;
    }

    public void setCoord_Lon(double lon) {
        this.coord_lon = lon;
    }

    public void setMain_temp(double main_temp) {
        this.main_temp = main_temp;
    }

    public void setWeather_Description(String description) {
        this.weather_description = description;
    }

    public void setWeather_Icon(String icon) {
        this.weather_icon = icon;
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
                +" lat: "+coord_lat
                +" lon: "+coord_lon;
    }


    public int getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(int weather_id) {
        this.weather_id = weather_id;
    }

    public String getWeather_main() {
        return weather_main;
    }

    public void setWeather_main(String weather_main) {
        this.weather_main = weather_main;
    }

    public int getSys_type() {
        return sys_type;
    }

    public void setSys_type(int sys_type) {
        this.sys_type = sys_type;
    }

    public int getSys_id() {
        return sys_id;
    }

    public void setSys_id(int sys_id) {
        this.sys_id = sys_id;
    }

    public double getSys_message() {
        return sys_message;
    }

    public void setSys_message(double sys_message) {
        this.sys_message = sys_message;
    }

    public String getSys_country() {
        return sys_country;
    }

    public void setSys_country(String sys_country) {
        this.sys_country = sys_country;
    }

    public long getSys_sunrise() {
        return sys_sunrise;
    }

    public void setSys_sunrise(long sys_sunrise) {
        this.sys_sunrise = sys_sunrise;
    }

    public long getSys_sunset() {
        return sys_sunset;
    }

    public void setSys_sunset(long sys_sunset) {
        this.sys_sunset = sys_sunset;
    }

    public int getMain_pressure() {
        return main_pressure;
    }

    public void setMain_pressure(int main_pressure) {
        this.main_pressure = main_pressure;
    }

    public int getMain_humidity() {
        return main_humidity;
    }

    public void setMain_humidity(int main_humidity) {
        this.main_humidity = main_humidity;
    }

    public double getMain_temp_min() {
        return main_temp_min;
    }

    public void setMain_temp_min(double main_temp_min) {
        this.main_temp_min = main_temp_min;
    }

    public double getMain_temp_max() {
        return main_temp_max;
    }

    public void setMain_temp_max(double main_temp_max) {
        this.main_temp_max = main_temp_max;
    }
}
