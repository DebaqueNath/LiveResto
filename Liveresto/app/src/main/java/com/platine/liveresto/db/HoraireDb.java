package com.platine.liveresto.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nath on 27/12/2016.
 */
public class HoraireDb extends SQLiteOpenHelper {

        //Les champs de la table
        private static final String id="ID";
        private static final String idRestaurant="IDRestaurant";
        private static final String schedule="Schedule";



        //Création de la table
        private static final String TABLE_NAME = "horaire";
        private static final String TABLE_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        idRestaurant + " INTEGER FOREIGN KEY, " +
                        schedule+ " TEXT, " + ")";

        //Suppression de la table
        private static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        //Constructeur
        public HoraireDb(Context context, String dataBaseName, int dataBaseVersion) {
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
