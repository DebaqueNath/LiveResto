package com.platine.liveresto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fixtures();

        //TEST AFFICHAGE LISTE DES RESTAURANTS A FAIRE ICI
        RestaurantDAO restaurantDao = new RestaurantDAO(getApplicationContext());
        ArrayList<Restaurant> liste = restaurantDao.getRestaurants();

        HoraireDAO horaireDao = new HoraireDAO(getApplicationContext());
        ArrayList<Horaire> liste2 = horaireDao.getSchedule(liste.get(0));
        ArrayList<Horaire> liste3 = horaireDao.getSchedule(liste.get(1));

        System.out.println("Liste des restaurants :"+ liste.toString());
        System.out.println("Horaires du restaurant 1 : "+liste2.toString());
        System.out.println("Horaires du restaurant 2 : "+liste3.toString());

    }

    //Add restaurant to database
    public void fixtures() {
        //Restaurant
        RestaurantDAO restaurantDao = new RestaurantDAO(getApplicationContext());
        Restaurant r1 = new Restaurant("Quick", "5 rue des fleurs 59000 Lille", "0656546576", "www.quick.fr", "/img/r1.png", 3.121059, 50.616862, false, false, "Fast-Food", "Jeune", 2, 11, "cartebancaire,especes,cheque", 10, 10, true, true);
        Restaurant r2 = new Restaurant("KFC", "34 rue des épaules 59000 Lille", "0627678789", "www.kfc.fr", "/img/r2.png", 3.071162, 50.636491, false, false, "Fast-Food", "Jeune", 2, 12, "cartebancaire,especes,cheque,ticketsrestaurant", 5, 10, false, true);
        //Add restaurant
        restaurantDao.putRestaurant(r1);
        restaurantDao.putRestaurant(r2);

        //Schedule
        HoraireDAO horaireDao = new HoraireDAO(getApplicationContext());
        Horaire h1 = new Horaire(1,"LU 8,30 14,30");
        Horaire h2 = new Horaire(1,"LU 19,30 22,30");
        Horaire h3 = new Horaire(2,"MA 8,30 14,30");
        Horaire h4 = new Horaire(2,"MA 19,30 22,30");
        //Add schedule
        horaireDao.putHoraire(h1);
        horaireDao.putHoraire(h2);
        horaireDao.putHoraire(h3);
        horaireDao.putHoraire(h4);
    }
}
