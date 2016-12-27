package com.platine.liveresto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nath on 27/12/2016.
 */
public class RestaurantDb extends SQLiteOpenHelper {

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


        //Création de la table
        private static final String TABLE_NAME = "restaurant";
        private static final String TABLE_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        name + " TEXT, " +
                        adress + " TEXT, "+
                        phoneNumber + " TEXT, "+
                        website+ " TEXT, " +
                        picture+ " TEXT, " +
                        longitude+ " INTEGER NOT NULL, " +
                        latitude+ " INTEGER NOT NULL, " +
                        favorite+ " INTEGER NOT NULL, " +
                        historic+ " INTEGER NOT NULL, " +
                        type+ " TEXT, " +
                        atmosphere+ " TEXT, " +
                        startBudget+ " INTEGER, " +
                        endBudget+ " INTEGER, " +
                        payment+ " TEXT, " +
                        places+ " INTEGER, " +
                        waitingTime+ " INTEGER, " +
                        terrace+ " INTEGER, " +
                        airConditionner+ " INTEGER "+ " )";

        //Suppression de la table
        private static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        //Constructeur
        public RestaurantDb(Context context, String dataBaseName, int dataBaseVersion) {
            super(context, dataBaseName, null, dataBaseVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(TABLE_DROP);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(TABLE_DROP);
            onCreate(db);
        }


}
