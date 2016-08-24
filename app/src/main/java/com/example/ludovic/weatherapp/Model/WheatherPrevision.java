package com.example.ludovic.weatherapp.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ludovic on 29/07/16.
 */
public class WheatherPrevision {

    private String name;
    private double lat;
    private double lon;
    private double[] min;
    private double[] max;
    private long[] dt;
    private String[] icon;

    private  int cnt;

    public WheatherPrevision(JSONObject data) throws JSONException {

        if (data.getInt("cod") == 200){

            Log.d("TAG",data.toString());

            JSONObject jsonObjectCity = data.getJSONObject("city");
            JSONObject jsonObjectCoord = jsonObjectCity.getJSONObject("coord");
            JSONArray jsonArray = data.getJSONArray("list");
            this.lat = jsonObjectCoord.getDouble("lat");
            this.lon = jsonObjectCoord.getDouble("lon");
            this.name = jsonObjectCity.getString("name");

            this.cnt = data.getInt("cnt");
            min = new double[cnt];
            max = new double[cnt];
            dt = new long[cnt];
            icon = new String[cnt];

            for (int i = 0; i<cnt ;i++){
                JSONObject jsonObjectList = jsonArray.getJSONObject(i);
                JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("temp");
                JSONArray jsonArrayicon = jsonObjectList.getJSONArray("weather");
                JSONObject jsonObjectWeather = jsonArrayicon.getJSONObject(0);

                this.min[i] = jsonObjectTemp.getDouble("min");
                this.max[i] = jsonObjectTemp.getDouble("max");
                this.dt[i] = jsonObjectList.getLong("dt") * 1000;
                this.icon[i] = jsonObjectWeather.getString("icon");

            }
        }
        else
            throw new JSONException("");
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public double getCnt() {
        return cnt;
    }

    public double[] getMin() {
        return min;
    }

    public double[] getMax() {
        return max;
    }

    public long[] getDt() {
        return dt;
    }

    public String[] getIcon() {
        return icon;
    }
}
