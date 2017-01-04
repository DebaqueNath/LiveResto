package com.platine.liveresto;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
    private RestaurantDb restaurantDb = null;

    public RestaurantDAO(Context context){
        this.restaurantDb = new RestaurantDb(context,DATABASE_NAME,DATABASE_VERSION);
    }

    //Add restaurant in Database
    public long putRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = this.restaurantDb.getWritableDatabase();
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
        restaurantDb.close();
        return retour;
    }

}
