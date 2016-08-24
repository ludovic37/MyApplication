package com.example.ludovic.weatherapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ludovic.weatherapp.Model.WeatherVille;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ludovic on 24/08/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "Meteo";
    private static final int DATABASE_VERSION = 1;


    private static final String TABLE_CITY = "City";
    private static final String KEY_ID = "id";
    private static final String KEY_ID_CITY = "id_city";
    private static final String KEY_NAME = "name";
    private static final String KEY_TEMP = "temp";
    private static final String KEY_DESC = "desc"; //description
    private static final String KEY_RES_ICON = "res_icon";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";

    private static final  String  CREATE_TABLE_CITY =  "CREATE TABLE "  +  TABLE_CITY
            +  "("
            +  KEY_ID +  " INTEGER PRIMARY KEY,"
            +  KEY_ID_CITY +  " INTEGER,"
            +  KEY_NAME +  " TEXT,"
            +  KEY_TEMP +  " TEXT,"
            +  KEY_DESC +  " TEXT,"
            +  KEY_RES_ICON +  " INTEGER,"
            +  KEY_LAT +  " DECIMAL (3, 10),"
            +  KEY_LNG +  " DECIMAL (3, 10)"
            +  ")" ;


    public DataBaseHelper(Context context) {
        super (context,  DATABASE_NAME,  null ,  DATABASE_VERSION);
    }
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( CREATE_TABLE_CITY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS " +   TABLE_CITY);
        onCreate(sqLiteDatabase);

    }

    public void insert (WeatherVille weatherVille){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_CITY, weatherVille.getId()); // Contact Name
        values.put(KEY_NAME, weatherVille.getName()); // Contact Name
        values.put(KEY_TEMP, weatherVille.getTemp()); // Contact Name
        values.put(KEY_DESC, weatherVille.getDescription()); // Contact Name
        values.put(KEY_RES_ICON, weatherVille.getIcon()); // Contact Name
        values.put(KEY_LAT, weatherVille.getLat()); // Contact Name
        values.put(KEY_LNG, weatherVille.getLon()); // Contact Phone Number

        // Inserting Row
        db.insert(TABLE_CITY, null, values);
        db.close(); // Closing database connection

        Log.d("Tag","insert");
    }

    public void deleteAll (){

        SQLiteDatabase db = this.getWritableDatabase();


        db.execSQL("DELETE FROM "+TABLE_CITY);
        db.close();

        Log.d("Tag","delete");
    }

    public ArrayList<WeatherVille> getAllVille() {

        Log.d("TAG","Star Select");

        ArrayList<WeatherVille> contactList = new ArrayList<WeatherVille>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CITY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                WeatherVille weatherVille = new WeatherVille();

                weatherVille.setId(Integer.parseInt(cursor.getString(1)));
                weatherVille.setName(cursor.getString(2));
                weatherVille.setTemp(Double.parseDouble(cursor.getString(3)));
                weatherVille.setDescription(cursor.getString(4));
                weatherVille.setIcon(cursor.getString(5));
                weatherVille.setLat(Double.parseDouble(cursor.getString(6)));
                weatherVille.setLon(Double.parseDouble(cursor.getString(7)));


                //contact.setID(Integer.parseInt(cursor.getString(0)));
                //contact.setName(cursor.getString(1));
                //contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list

                contactList.add(weatherVille);
                Log.d("TAG",weatherVille.toString());


            } while (cursor.moveToNext());

        }

        // return contact list
        return contactList;
    }

}
