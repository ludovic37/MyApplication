package com.example.ludovic.weatherapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ludovic.weatherapp.Model.WeatherVille;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ludovic on 22/08/16.
 */
public class RecyclerViewAdapterFavoris extends RecyclerView.Adapter<RecyclerViewAdapterFavoris.DataObjectHolder> {

    private MyClickListener myClickListener;

    Context context;
    private LayoutInflater inflater;
    ArrayList<WeatherVille> weatherVille;


    public RecyclerViewAdapterFavoris(Context context, ArrayList<WeatherVille> weatherVille) {
        inflater = LayoutInflater.from(context);
        this.weatherVille = weatherVille;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }




    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favoris, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        holder.ville.setText(weatherVille.get(position).getName());
        holder.meteo.setText(weatherVille.get(position).getDescription());
        holder.temp.setText(weatherVille.get(position).getTemp() + "");
        Picasso.with(context).load("http://openweathermap.org/img/w/" + weatherVille.get(position).getIcon() + ".png").fit().into(holder.icon);

    }

    @Override
    public int getItemCount() {
        return weatherVille.size();
    }

    public interface MyClickListener {
        void onItemClick(View v);
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView ville;
        TextView meteo;
        TextView temp;
        ImageView icon;


        public DataObjectHolder(View itemView) {
            super(itemView);
            ville = (TextView) itemView.findViewById(R.id.textViewFavorisVille);
            meteo = (TextView) itemView.findViewById(R.id.textViewFavorisMeteo);
            temp = (TextView) itemView.findViewById(R.id.textViewFavorisTemp);
            icon = (ImageView) itemView.findViewById(R.id.imageViewFavoris);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick( v);
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
}
