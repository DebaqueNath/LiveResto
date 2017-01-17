package com.platine.liveresto.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Nathanael on 04/01/2017.
 */

public class RestaurantDAO {

    //Info BDD
    private static int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME ="Liveresto";
    private static final String TABLE_NAME = "restaurant";

    //Les champs de la table
    private static final String id="ID";
    private static final String name="Name";
    private static final String adress="Adress";
    private static final String phoneNumber="PhoneNumber";
    private static final String website="Website";
    private static final String picture="Picture";
    private static final String longitude="Longitude";
    private static final String latitude="Latitude";
    private static final String favorite="Favorite";
    private static final String historic="Historic";
    private static final String type="Type";
    private static final String atmosphere="Atmosphere";
    private static final String startBudget="StartBudget";
    private static final String endBudget="EndBudget";
    private static final String payment="Payment";
    private static final String places="Places";
    private static final String waitingTime="WaitingName";
    private static final String terrace="Terrace";
    private static final String airConditionner="AirConditionner";

    //DatabaseHandler
    private LiveRestoDb liveRestoDb = null;

    private Context context;

    public RestaurantDAO(Context context){
        this.liveRestoDb = new LiveRestoDb(context,DATABASE_NAME,DATABASE_VERSION);
        this.context = context;
    }

    //Add restaurant in Database
    public long putRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = this.liveRestoDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(name, restaurant.getName());
        values.put(adress,restaurant.getAdress());
        values.put(phoneNumber,restaurant.getPhoneNumber());
        values.put(website,restaurant.getWebsite());
        values.put(picture,restaurant.getPicture());
        values.put(longitude,restaurant.getLongitude());
        values.put(latitude,restaurant.getLatitude());
        values.put(favorite,restaurant.isFavorite());
        values.put(historic,restaurant.isHistoric());
        values.put(type,restaurant.getType());
        values.put(atmosphere,restaurant.getAtmosphere());
        values.put(startBudget,restaurant.getStartBudget());
        values.put(endBudget,restaurant.getEndBudget());
        values.put(payment,restaurant.getPayment());
        values.put(places,restaurant.getPlaces());
        values.put(waitingTime,restaurant.getWaitingTime());
        values.put(terrace,restaurant.isTerrace());
        values.put(airConditionner,restaurant.isAirConditionner());
        long retour = db.insert(TABLE_NAME,null,values);
        liveRestoDb.close();
        return retour;
    }

    //Get restaurant's list
    public ArrayList<Restaurant> getRestaurants(){
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        SQLiteDatabase db = this.liveRestoDb.getReadableDatabase();
        String[] fields = {
                id,
                name,
                adress,
                phoneNumber,
                website,
                picture,
                longitude,
                latitude,
                favorite,
                historic,
                type,
                atmosphere,
                startBudget,
                endBudget,
                payment,
                places,
                waitingTime,
                terrace,
                airConditionner
        };

        Cursor cursor = db.query(TABLE_NAME,fields,null,null,null,null,null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            //Add schedule to restaurant
            HoraireDAO horaireDao = new HoraireDAO(this.context);
            Restaurant r = new Restaurant(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getDouble(6),cursor.getDouble(7),(cursor.getInt(8)==1)?true:false,(cursor.getInt(9)==1)?true:false,cursor.getString(10),cursor.getString(11),cursor.getInt(12),cursor.getInt(13),cursor.getString(14),cursor.getInt(15),cursor.getInt(16),(cursor.getInt(17)==1)?true:false,(cursor.getInt(18)==1)?true:false);
            r.setShedule(horaireDao.getSchedule(r));
            restaurants.add(r);

            cursor.moveToNext();
        }

        liveRestoDb.close();
        return restaurants;
    }


}
