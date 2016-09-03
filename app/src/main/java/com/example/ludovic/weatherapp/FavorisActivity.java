package com.example.ludovic.weatherapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ludovic.weatherapp.Model.WeatherVille;
import com.example.ludovic.weatherapp.database.DataBaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class FavorisActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    public AdapterFavoris adapter;

    private Handler mHandler;

    private ListView listView;
    DataBaseHelper dataBaseHelper;

    private ArrayList<WeatherVille> weatherVilleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.listViewFavoris);

        dataBaseHelper = new DataBaseHelper(this);

        mHandler = new Handler();

        weatherVilleList = new ArrayList<>();
        ArrayList<WeatherVille>weatherVilleListId = dataBaseHelper.getAllVille();

        adapter = new AdapterFavoris(FavorisActivity.this, weatherVilleList);

        String listId = null;

        for (WeatherVille weatherVille : weatherVilleListId) {

            String id = weatherVille.getId()+"";
            if (listId == null)
                listId = id;
            else
                listId = listId +","+ id;
        }
        requestApiActu(listId);

        listView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarmDial();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FavorisActivity.this);
                builder.setMessage("Voulez-vous supprimer " + weatherVilleList.get(i).getName())
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                weatherVilleList.remove(i);
                                adapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();
                builder.show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(FavorisActivity.this);
                builder.setMessage("Voulez-vous voir la méteo de " + weatherVilleList.get(i).getName())
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(FavorisActivity.this, Main2Activity.class);
                                intent.putExtra("ville",weatherVilleList.get(i).getName());
                                intent.putExtra("lat",weatherVilleList.get(i).getLat());
                                intent.putExtra("lon",weatherVilleList.get(i).getLon());
                                Log.d("VILLE put",weatherVilleList.get(i).getName());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();
                builder.show();

            }
        });
    }

    public void actuAdapter(String json) throws JSONException {
        JSONObject data = new JSONObject(json);

        Log.d("TAG",data.toString());

        for (WeatherVille weather : weatherVilleList){
            if (data.getString("name").compareTo(weather.getName()) == 0){
                Log.d("TAG","DOUBLE");
                return;
            }
        }
            WeatherVille weatherVille = new WeatherVille(data);
            weatherVilleList.add(weatherVille);
            adapter.notifyDataSetChanged();
            Log.d("TAG", "actu");
    }

    public void actuAdapterActu(String json) throws JSONException {
        JSONObject data = new JSONObject(json);

        JSONArray array = data.getJSONArray("list");
        int count = data.getInt("cnt");

        for (int i = 0;i<count;i++){
            JSONObject weather = array.getJSONObject(i);

            actuAdapter(weather.toString());
        }
        Log.d("TAG", "actu");
    }

    public void alarmDial() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FavorisActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        final View view1 = inflater.inflate(R.layout.alarmdialogview, null);

        builder.setView(view1)
                .setMessage("Ajouter une ville")
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        final String ville = ((EditText) view1.findViewById(R.id.editTextVille)).getText().toString().trim();

                        requestApi(ville);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create();
        builder.show();

    }

    public void requestApi(final String ville){

        new Thread() {
            public void run() {
                try {
                    final String response = Util.requestApi("http://api.openweathermap.org/data/2.5/weather?q=" + ville + "&units=metric&lang=fr&APPID=d32d2d9784630a43b8479698062afe1f");

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                actuAdapter(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("TAG", "Fail");
                }
            }
        }.start();

    }

    public void requestApiActu(final String id){

        new Thread() {
            public void run() {
                try {
                    final String response = Util.requestApi("http://api.openweathermap.org/data/2.5/group?id=" + id + "&units=metric&lang=fr&APPID=d32d2d9784630a43b8479698062afe1f");

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                actuAdapterActu(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("TAG", "Fail");
                }
            }
        }.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        dataBaseHelper.deleteAll();

        for (int i = 0; i < weatherVilleList.size(); i++) {
            dataBaseHelper.insert(weatherVilleList.get(i));
        }
    }
}
