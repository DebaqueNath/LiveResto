package com.platine.liveresto.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.platine.liveresto.R;
import com.platine.liveresto.model.Filtre;
import com.platine.liveresto.model.Horaire;
import com.platine.liveresto.model.HoraireDAO;
import com.platine.liveresto.model.Restaurant;
import com.platine.liveresto.model.RestaurantDAO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    //Objet filtre global correspondant aux filtres courant
    Filtre filterGlobal;
    SharedPreferences sharedPrefs;
    public static final String PREFS_FILTER = "FilterPrefs";
    public static final int FILTRESCODE = 42;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Récupération des SharedPreferences
        this.sharedPrefs = getSharedPreferences(PREFS_FILTER, 0);
        SharedPreferences.Editor editor = this.sharedPrefs.edit();
        ArrayList<String> days = new ArrayList<>();
        String tmp = this.sharedPrefs.getString("days","");
        String[] split = tmp.split(" ");
        for (String s : split) {
            days.add(s);
        }
        ArrayList<String> type = new ArrayList<>();
        tmp = this.sharedPrefs.getString("type","");
        split = tmp.split(" ");
        for (String s : split) {
            type.add(s);
        }
        ArrayList<String> payment = new ArrayList<>();
        tmp = this.sharedPrefs.getString("payment","");
        split = tmp.split(" ");
        for (String s : split) {
            payment.add(s);
        }
        ArrayList<String> atmosphere = new ArrayList<>();
        tmp = this.sharedPrefs.getString("atmosphere","");
        split = tmp.split(" ");
        for (String s : split) {
            atmosphere.add(s);
        }
        this.filterGlobal = new Filtre(sharedPrefs.getFloat("distance",(float)50.0),days,sharedPrefs.getFloat("hourBegin",(float)0.0),sharedPrefs.getFloat("hourEnd",(float)0.0),type,sharedPrefs.getInt("startBudget",0),sharedPrefs.getInt("endBudget",0),payment,atmosphere,sharedPrefs.getInt("places",0),sharedPrefs.getInt("waitingTime",0),sharedPrefs.getBoolean("terrace",false),sharedPrefs.getBoolean("airConditionner",false));
        

        // ******************** DB  ********************
        fixtures();

        //TEST requête BDD par rapport aux filtres
       /* RestaurantDAO rdao = new RestaurantDAO(getApplicationContext());

        ArrayList<String> days = new ArrayList<>();
        ArrayList<String> type = new ArrayList<>();
        ArrayList<String> payment = new ArrayList<>();
        ArrayList<String> atmosphere = new ArrayList<>();
        Filtre filtreTest = new Filtre(20,days,0.0, 0.0, type, 0, 0, payment, atmosphere, 0, 10, false, false);

        ArrayList<Restaurant> liste = filtreTest.getRestaurantsFilter(rdao.getRestaurants());

        for (Restaurant r : liste) {
            System.out.println("RESTAURANT");
            System.out.println(r);
        }*/

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }


    protected void onStop(){
        super.onStop();

        //Sauvegarde des sharedPreferences
        SharedPreferences.Editor editor = this.sharedPrefs.edit();
        float distance = (float)this.filterGlobal.getDistanceMax();
        String days="";
        for (String d: this.filterGlobal.getDays()) {
            days+=d+" ";
        }
        float hourBegin = (float)this.filterGlobal.getHourBegin();
        float hourEnd = (float) this.filterGlobal.getHourEnd();
        String type="";
        for (String d: this.filterGlobal.getType()) {
            type+=d+" ";
        }
        int startBudget = this.filterGlobal.getStartBudget();
        int endBudget = this.filterGlobal.getEndBudget();
        String payment="";
        for (String d: this.filterGlobal.getPayment()) {
            payment+=d+" ";
        }
        String atmosphere="";
        for (String d: this.filterGlobal.getAtmosphere()) {
            atmosphere+=d+" ";
        }
        int places = this.filterGlobal.getPlaces();
        int waitingTime = this.filterGlobal.getWaitingTime();
        boolean terrace = this.filterGlobal.isTerrace();
        boolean airConditionner = this.filterGlobal.isAirConditionner();
        editor.putFloat("distance",distance);
        editor.putString("days",days);
        editor.putFloat("hourBegin",hourBegin);
        editor.putFloat("hourEnd",hourEnd);
        editor.putString("type",type);
        editor.putInt("startBudget",startBudget);
        editor.putInt("endBudget",endBudget);
        editor.putString("payment",payment);
        editor.putString("atmosphere",atmosphere);
        editor.putInt("places",places);
        editor.putInt("waitingTime",waitingTime);
        editor.putBoolean("terrace",terrace);
        editor.putBoolean("airConditionner",airConditionner);
        editor.commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = getApplicationContext();
        switch (item.getItemId()) {
            case R.id.filtres:
                Intent filtres = new Intent(context, FiltreActivity.class);
                startActivityForResult(filtres, FILTRESCODE);
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        RestaurantDAO restosDAO = new RestaurantDAO(getApplicationContext());
        ArrayList<Restaurant> allRestos = restosDAO.getRestaurants();

        LatLng firstRestaurantPosition = new LatLng(allRestos.get(0).getLatitude(), allRestos.get(0).getLongitude());
        mMap.addMarker(new MarkerOptions().position(firstRestaurantPosition).title(allRestos.get(0).getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(firstRestaurantPosition));
        allRestos.remove(0);

        for(Restaurant resto : allRestos) {
            LatLng position = new LatLng(resto.getLatitude(), resto.getLongitude());
            mMap.addMarker(new MarkerOptions().position(position).title(resto.getName()));
        }
    }


    /**
     * Add restaurant to database
     */
    public void fixtures() {

        // Restaurant
        RestaurantDAO restaurantDao = new RestaurantDAO(getApplicationContext());

        //Si la base est vide on la remplit
        if(restaurantDao.getRestaurants().isEmpty()) {
            Restaurant r1 = new Restaurant("Quick", "5 rue des fleurs 59000 Lille", "0656546576", "www.quick.fr", "r1", 3.121059, 50.616862, false, false, "Fast-Food", "Musical", 2, 11, "cartebancaire,especes,cheque", 10, 10, true, true);
            Restaurant r2 = new Restaurant("KFC", "34 rue des épaules 59000 Lille", "0627678789", "www.kfc.fr", "r2", 3.071162, 50.636491, false, false, "Fast-Food", "Jeune", 2, 12, "cartebancaire,especes,cheque,ticketsrestaurant", 5, 15, false, true);
            //Add restaurant
            restaurantDao.putRestaurant(r1);
            restaurantDao.putRestaurant(r2);

            // Schedule
            HoraireDAO horaireDao = new HoraireDAO(getApplicationContext());

            Horaire h1 = new Horaire(1, "LU 08.30 14.30");
            Horaire h2 = new Horaire(1, "LU 19.30 22.30");
            Horaire h3 = new Horaire(2, "MA 08.30 14.30");
            Horaire h4 = new Horaire(2, "MA 17.30 19.30");
            //Add schedule

            horaireDao.putHoraire(h1);
            horaireDao.putHoraire(h2);
            horaireDao.putHoraire(h3);
            horaireDao.putHoraire(h4);
        }
    }
}
