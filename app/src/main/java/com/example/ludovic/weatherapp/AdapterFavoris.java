package com.example.ludovic.weatherapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ludovic.weatherapp.Model.WeatherVille;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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


        //Log.d("lol", "-------------> pos : " +i );
        ViewHolder holder;

        if (convertView == null) {

            // Nouvel objet avec les composants de l’item
            holder = new ViewHolder();
            // La nouvelle vue est initialisé avec le layout d’un item
            convertView = inflater.inflate(R.layout.item_favoris, null);
            // Initialisation des attributs de notre objet ViewHolder

            holder.ville = (TextView) convertView.findViewById(R.id.textViewFavorisVille);
            holder.meteo = (TextView) convertView.findViewById(R.id.textViewFavorisMeteo);
            holder.temp = (TextView) convertView.findViewById(R.id.textViewFavorisTemp);
            holder.icon = (ImageView) convertView.findViewById(R.id.imageViewFavoris);
            // On lie l’objet ViewHolder à la vue pour le sauvegarder grace à setTag
            convertView.setTag(holder);
        } else {
            // On initialise l’objet ViewHolder grace au tag de la vue qui avait été
            // sauvegardé
            holder = (ViewHolder) convertView.getTag();
        }

        //convertView = inflater.inflate(R.layout.items_listeview, null);

        holder.ville.setText(weatherVille.get(i).getName());
        holder.meteo.setText(weatherVille.get(i).getDescription());
        holder.temp.setText(weatherVille.get(i).getTemp() + "");
        Picasso.with(context).load("http://openweathermap.org/img/w/" + weatherVille.get(i).getIcon() + ".png").fit().into(holder.icon);

        return convertView;

    }

    public class ViewHolder {
        TextView ville;
        TextView meteo;
        TextView temp;
        ImageView icon;
    }
}
