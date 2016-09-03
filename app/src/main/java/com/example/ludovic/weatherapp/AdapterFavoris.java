package com.example.ludovic.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ludovic.weatherapp.Model.WeatherVille;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ludovic on 29/07/16.
 */
public class AdapterFavoris extends BaseAdapter {

    Context context;
    private LayoutInflater inflater;
    ArrayList<WeatherVille> weatherVille;

    public AdapterFavoris(Context context, ArrayList<WeatherVille> weatherVille) {
        inflater = LayoutInflater.from(context);
        this.weatherVille = weatherVille;
    }


    @Override
    public int getCount() {
        return weatherVille.size();
    }

    @Override
    public Object getItem(int i) {
        return weatherVille.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_favoris, null);

            holder.ville = (TextView) convertView.findViewById(R.id.textViewFavorisVille);
            holder.meteo = (TextView) convertView.findViewById(R.id.textViewFavorisMeteo);
            holder.temp = (TextView) convertView.findViewById(R.id.textViewFavorisTemp);
            holder.icon = (ImageView) convertView.findViewById(R.id.imageViewFavoris);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ville.setText(weatherVille.get(i).getName());
        holder.meteo.setText(weatherVille.get(i).getWeather_Description());
        holder.temp.setText(weatherVille.get(i).getMain_temp() + "");
        Picasso.with(context).load("http://openweathermap.org/img/w/" + weatherVille.get(i).getWeather_Icon() + ".png").fit().into(holder.icon);

        return convertView;

    }

    public class ViewHolder {
        TextView ville;
        TextView meteo;
        TextView temp;
        ImageView icon;
    }
}
