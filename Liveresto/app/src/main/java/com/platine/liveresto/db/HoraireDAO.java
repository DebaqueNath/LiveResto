package com.platine.liveresto.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.platine.liveresto.model.Horaire;
import com.platine.liveresto.model.Restaurant;

import java.util.ArrayList;

/**
 * Created by Nathanael on 04/01/2017.
 */

public class HoraireDAO {

    //Info BDD
    private static int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME ="Liveresto";
    private static final String TABLE_NAME = "horaire";

    //Les champs de la table
    private static final String id="ID";
    private static final String idRestaurant="IDRestaurant";
    private static final String schedule="Schedule";

    //DatabaseHandler
    private LiveRestoDb liveRestoDb = null;

    public HoraireDAO(Context context){
        this.liveRestoDb = new LiveRestoDb(context,DATABASE_NAME,DATABASE_VERSION);
    }

    //Add horaire in Database
    public long putHoraire(Horaire horaire) {
        SQLiteDatabase db = this.liveRestoDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(idRestaurant,horaire.getIdRestaurant());
        values.put(schedule,horaire.getSchedule());
        long retour = db.insert(TABLE_NAME,null,values);
        liveRestoDb.close();
        return retour;
    }

    //Get shedule for a restaurant
    public ArrayList<Horaire> getSchedule(Restaurant restaurant){
        ArrayList<Horaire> schedule = new ArrayList<>();
        SQLiteDatabase db = this.liveRestoDb.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+" where "+idRestaurant+" = ?",new String[] {""+restaurant.getId()});
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            schedule.add(new Horaire(cursor.getInt(0),cursor.getInt(1),cursor.getString(2)));
            cursor.moveToNext();
        }
        liveRestoDb.close();
        return schedule;
    }
}
