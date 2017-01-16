package com.platine.liveresto.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nath on 27/12/2016.
 */
public class LiveRestoDb extends SQLiteOpenHelper {

        //Les champs de la table Restaurant
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


        //Création de la table restaurant
        private static final String TABLE_NAME1 = "restaurant";
        private static final String TABLE_CREATE1 =
                "CREATE TABLE " + TABLE_NAME1 + " (" +
                        id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        name + " TEXT, " +
                        adress + " TEXT, "+
                        phoneNumber + " TEXT, "+
                        website+ " TEXT, " +
                        picture+ " TEXT, " +
                        longitude+ " REAL NOT NULL, " +
                        latitude+ " REAL NOT NULL, " +
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
                        airConditionner+ " INTEGER "+ " );";

        //Suppression de la table
        private static final String TABLE_DROP1 = "DROP TABLE IF EXISTS " + TABLE_NAME1 + ";";


        //Les champs de la table horaire
        private static final String id2="ID";
        private static final String idRestaurant="IDRestaurant";
        private static final String schedule="Schedule";


        //Création de la table horaire
        private static final String TABLE_NAME2 = "horaire";
        private static final String TABLE_CREATE2 =
                "CREATE TABLE " + TABLE_NAME2 + " (" +
                        id2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        idRestaurant + " INTEGER, " +
                        schedule+ " TEXT, " +
                        " FOREIGN KEY ("+idRestaurant+") REFERENCES "+TABLE_NAME1+"(id));";

        //Suppression de la table
        private static final String TABLE_DROP2 = "DROP TABLE IF EXISTS " + TABLE_NAME2 + ";";


        //TABLE PROFIL
        //Les champs de la table
        private static final String id3="ID";
        private static final String distance="Distance";
        private static final String days="Days";
        private static final String startSchedule="StartSchedule";
        private static final String endSchedule="EndSchedule";


        //Création de la table
        private static final String TABLE_NAME3 = "profil";
        private static final String TABLE_CREATE3 =
                "CREATE TABLE " + TABLE_NAME3 + " (" +
                        id3 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        name + " TEXT NOT NULL UNIQUE, " +
                        type+ " TEXT, " +
                        distance+ " INTEGER, " +
                        atmosphere+ " TEXT, " +
                        startBudget+ " INTEGER NOT NULL, " +
                        endBudget+ " INTEGER NOT NULL, " +
                        payment+ " TEXT, " +
                        places+ " INTEGER, " +
                        waitingTime+ " INTEGER, " +
                        terrace+ " INTEGER NOT NULL, " +
                        airConditionner+ " INTEGER NOT NULL, " +
                        days+ " TEXT, " +
                        startSchedule+ " REAL, " +
                        endSchedule+ " REAL " + ");";

        //Suppression de la table
        private static final String TABLE_DROP3 = "DROP TABLE IF EXISTS " + TABLE_NAME3 + ";";

        //Constructeur
        public LiveRestoDb(Context context, String dataBaseName, int dataBaseVersion) {
            super(context, dataBaseName, null, dataBaseVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                db.execSQL(TABLE_CREATE1);
                db.execSQL(TABLE_CREATE2);
                db.execSQL(TABLE_CREATE3);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(TABLE_DROP1);
            db.execSQL(TABLE_DROP2);
            db.execSQL(TABLE_DROP3);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(TABLE_DROP1);
            db.execSQL(TABLE_DROP2);
            db.execSQL(TABLE_DROP3);
            onCreate(db);
        }


}
