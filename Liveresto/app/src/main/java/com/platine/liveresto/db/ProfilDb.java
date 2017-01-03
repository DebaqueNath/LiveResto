package com.platine.liveresto.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nath on 27/12/2016.
 */
public class ProfilDb extends SQLiteOpenHelper {

        //Les champs de la table
        private static final String id="ID";
        private static final String name="Name";
        private static final String type="Type";
        private static final String distance="Distance";
        private static final String atmosphere="Atmosphere";
        private static final String startBudget="StartBudget";
        private static final String endBudget="EndBudget";
        private static final String payment="Payment";
        private static final String places="Places";
        private static final String waitingTime="WaitingName";
        private static final String terrace="Terrace";
        private static final String airConditionner="AirConditionner";
        private static final String days="Days";
        private static final String startSchedule="StartSchedule";
        private static final String endSchedule="EndSchedule";


        //Création de la table
        private static final String TABLE_NAME = "profil";
        private static final String TABLE_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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
                        airConditionner+ " INTEGER NOT NULL " +
                        days+ " TEXT, " +
                        startSchedule+ " REAL, " +
                        endSchedule+ " REAL, " + ")";

        //Suppression de la table
        private static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        //Constructeur
        public ProfilDb(Context context, String dataBaseName, int dataBaseVersion) {
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
