package com.platine.liveresto.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.platine.liveresto.R;
import com.platine.liveresto.db.HoraireDAO;
import com.platine.liveresto.db.RestaurantDAO;
import com.platine.liveresto.filtre.FiltreActivity;
import com.platine.liveresto.model.Filtre;
import com.platine.liveresto.model.Horaire;
import com.platine.liveresto.model.Restaurant;
import com.platine.liveresto.restaurant.RestaurantActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    //Current filters
    private Filtre filterGlobal;
    //All restaurants
    private ArrayList<Restaurant> restoListe;
    SharedPreferences sharedPrefs;
    public static final String PREFS_FILTER = "FilterPrefs";
    public static final int FILTRESCODE = 42;
    private GoogleMap mMap;
    private static final int PERMISSION_REQUEST_CODE_LOCATION = 1;

    //Keep information about filter actived
    private boolean distanceActived = false;
    private boolean scheduleActived = false;
    private boolean typeActived = false;
    private boolean budgetActived = false;
    private boolean paymentActived = false;
    private boolean atmosphereActived = false;
    private boolean placesActived = false;
    private boolean waitingTimeActived = false;
    private boolean otherActived = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Verify permission for user location
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission(android.Manifest.permission.ACCESS_FINE_LOCATION, PERMISSION_REQUEST_CODE_LOCATION, getApplicationContext(), MainActivity.this);
        } else {
            loadApplication();
        }
    }

    //Init the activity
    public void loadApplication() {
        //Get SharedPreferences
        this.sharedPrefs = getSharedPreferences(PREFS_FILTER, 0);
        SharedPreferences.Editor editor = this.sharedPrefs.edit();
        ArrayList<String> days = new ArrayList<>();
        String tmp = this.sharedPrefs.getString("days", "");
        if (tmp != "") {
            String[] split = tmp.split(",");
            for (String s : split) {
                if (!s.equals(" ")) {
                    days.add(s);
                }
            }
        }
        ArrayList<String> type = new ArrayList<>();
        tmp = this.sharedPrefs.getString("type", "");
        if (tmp != "") {
            String[] split = tmp.split(",");
            for (String s : split) {
                type.add(s);
            }
        }
        ArrayList<String> payment = new ArrayList<>();
        tmp = this.sharedPrefs.getString("payment", "");
        if (tmp != "") {
            String[] split = tmp.split(",");
            for (String s : split) {
                if (!s.equals(" ")) {
                    payment.add(s);
                }
            }
        }
        ArrayList<String> atmosphere = new ArrayList<>();
        tmp = this.sharedPrefs.getString("atmosphere", "");
        if (tmp != "") {
            String[] split = tmp.split(",");
            for (String s : split) {
                if (!s.equals(" ")) {
                    atmosphere.add(s);
                }
            }
        }

        filterGlobal = new Filtre(sharedPrefs.getFloat("distance", (float) 0.0), days, sharedPrefs.getFloat("hourBegin", (float) 0.0), sharedPrefs.getFloat("hourEnd", (float) 0.0), type, sharedPrefs.getInt("startBudget", 0), sharedPrefs.getInt("endBudget", 0), payment, atmosphere, sharedPrefs.getInt("places", 0), sharedPrefs.getInt("waitingTime", 0), sharedPrefs.getBoolean("terrace", false), sharedPrefs.getBoolean("airConditionner", false));

        // ******************** DATABASE FIXTURES  ********************
        fixtures();

        //Get all restaurants
        RestaurantDAO restosDAO = new RestaurantDAO(getApplicationContext());
        restoListe = restosDAO.getRestaurants() ;

        //Init mapFragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Init toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");
        setSupportActionBar(myToolbar);
    }


    protected void onStop() {
        super.onStop();
        //Save sharedPreferences
        SharedPreferences.Editor editor = this.sharedPrefs.edit();
        float distance = (float) this.filterGlobal.getDistanceMax();
        String days = "";
        for (String d : this.filterGlobal.getDays()) {
            days += d + ",";
        }
        float hourBegin = (float) this.filterGlobal.getHourBegin();
        float hourEnd = (float) this.filterGlobal.getHourEnd();
        String type = "";
        for (String d : this.filterGlobal.getType()) {
            type += d + ",";
        }
        int startBudget = this.filterGlobal.getStartBudget();
        int endBudget = this.filterGlobal.getEndBudget();
        String payment = "";
        for (String d : this.filterGlobal.getPayment()) {
            payment += d + ",";

        }
        String atmosphere = "";
        for (String d : this.filterGlobal.getAtmosphere()) {
            atmosphere += d + ",";
        }
        int places = this.filterGlobal.getPlaces();
        int waitingTime = this.filterGlobal.getWaitingTime();
        boolean terrace = this.filterGlobal.isTerrace();
        boolean airConditionner = this.filterGlobal.isAirConditionner();

        editor.putFloat("distance", distance);
        editor.putString("days", days);
        editor.putFloat("hourBegin", hourBegin);
        editor.putFloat("hourEnd", hourEnd);
        editor.putString("type", type);
        editor.putInt("startBudget", startBudget);
        editor.putInt("endBudget", endBudget);
        editor.putString("payment", payment);
        editor.putString("atmosphere", atmosphere);
        editor.putInt("places", places);
        editor.putInt("waitingTime", waitingTime);
        editor.putBoolean("terrace", terrace);
        editor.putBoolean("airConditionner", airConditionner);
        editor.commit();
    }

    @Override
    /**
     * When coming back from activity filter, get filters informations
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILTRESCODE) {
            if (resultCode == RESULT_OK) {
                Intent i = data;
                distanceActived = i.getBooleanExtra("distanceActived", false);
                scheduleActived = i.getBooleanExtra("scheduleActived", false);
                typeActived = i.getBooleanExtra("typeActived", false);
                budgetActived = i.getBooleanExtra("budgetActived", false);
                paymentActived = i.getBooleanExtra("paymentActived", false);
                atmosphereActived = i.getBooleanExtra("atmosphereActived", false);
                placesActived = i.getBooleanExtra("placesActived", false);
                waitingTimeActived = i.getBooleanExtra("waitingTimeActived", false);
                otherActived = i.getBooleanExtra("otherActived", false);
                filterGlobal = new Filtre(i.getDoubleExtra("distanceFilter", 0.0), i.getStringArrayListExtra("daysFilter"), i.getDoubleExtra("hourBeginFilter", 0.0), i.getDoubleExtra("hourEndFilter", 0.0), i.getStringArrayListExtra("typeFilter"), i.getIntExtra("startBudgetFilter", 0), i.getIntExtra("endBudgetFilter", 0), i.getStringArrayListExtra("paymentFilter"), i.getStringArrayListExtra("atmosphereFilter"), i.getIntExtra("placesFilter", 0), i.getIntExtra("waitingTimeFilter", 0), i.getBooleanExtra("terraceFilter", false), i.getBooleanExtra("airConditionnerFilter", false));
                updateMarkerOnMap();
            }
        }
    }

    /**
     * Toolbar menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    /**
     * When click on filter button in the toolbar menu
     * Start FiltreActivity
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = getApplicationContext();
        switch (item.getItemId()) {
            case R.id.filtres:
                Intent filtres = new Intent(context, FiltreActivity.class);
                filtres.putExtra("distanceFilter", filterGlobal.getDistanceMax());
                filtres.putExtra("daysFilter", filterGlobal.getDays());
                filtres.putExtra("hourBeginFilter", filterGlobal.getHourBegin());
                filtres.putExtra("hourEndFilter", filterGlobal.getHourEnd());
                filtres.putExtra("typeFilter", filterGlobal.getType());
                filtres.putExtra("startBudgetFilter", filterGlobal.getStartBudget());
                filtres.putExtra("endBudgetFilter", filterGlobal.getEndBudget());
                filtres.putExtra("paymentFilter", filterGlobal.getPayment());
                filtres.putExtra("atmosphereFilter", filterGlobal.getAtmosphere());
                filtres.putExtra("placesFilter", filterGlobal.getPlaces());
                filtres.putExtra("waitingTimeFilter", filterGlobal.getWaitingTime());
                filtres.putExtra("terraceFilter", filterGlobal.isTerrace());
                filtres.putExtra("airConditionnerFilter", filterGlobal.isAirConditionner());
                filtres.putExtra("distanceActived", distanceActived);
                filtres.putExtra("scheduleActived", scheduleActived);
                filtres.putExtra("typeActived", typeActived);
                filtres.putExtra("budgetActived", budgetActived);
                filtres.putExtra("paymentActived", paymentActived);
                filtres.putExtra("atmosphereActived", atmosphereActived);
                filtres.putExtra("placesActived", placesActived);
                filtres.putExtra("waitingTimeActived", waitingTimeActived);
                filtres.putExtra("otherActived", otherActived);
                startActivityForResult(filtres, FILTRESCODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    /**
     * Map is loaded
     */
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateMarkerOnMap();
    }

    /**
     * Request permissions to the user
     */
    public void requestPermission(String strPermission, int perCode, Context _c, MainActivity _a) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(_a, strPermission)) {
            loadApplication();
        } else {
            ActivityCompat.requestPermissions(_a, new String[]{strPermission}, perCode);
        }
    }


    @Override
    /**
     * Get result of permissions requested
     */
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadApplication();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access location data.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    /**
     * Update the map with restaurants corresponding to filters
     */
    public void updateMarkerOnMap() {
        mMap.clear();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        //Get user location
        LocationManager lm=(LocationManager)getSystemService(LOCATION_SERVICE);
        Location location = null;
        String provider=lm.getBestProvider(new Criteria(), true);
        if(provider!=null){
            location=lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location!=null){
                updateLocationUser(location);
            }
        }

        //Marker restaurant click -> Call RestaurantActivity
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getApplicationContext(), RestaurantActivity.class);
                String title = marker.getTitle();
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });

        //Get restaurants corresponding to filters
        ArrayList<Restaurant> restoListeFilter = filterGlobal.getRestaurantsFilter(restoListe,location.getLatitude(),location.getLongitude());

        //Add markers for restaurants
       if (!restoListeFilter.isEmpty()) {
            for (Restaurant resto : restoListeFilter) {
                LatLng position = new LatLng(resto.getLatitude(), resto.getLongitude());
                mMap.addMarker(new MarkerOptions().position(position).title(resto.getName())).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_restaurant));
            }
        }
    }

    /**
     * Move the camera on the user location
     * @param location
     */
    public void updateLocationUser(Location location){
        LatLng myLocation = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));
    }


    /**
     * Add restaurants to database
     */
    public void fixtures() {

        RestaurantDAO restaurantDao = new RestaurantDAO(getApplicationContext());

        //If base is empty, we put data in
        if(restaurantDao.getRestaurants().isEmpty()) {

            //Two restaurants for the video
            Restaurant r1 = new Restaurant("Quick", "5 rue des fleurs 59000 Lille", "0656546576", "www.quick.fr", "r1", 3.137273, 50.612136, false, false, "fastfood", "musical", 2, 11, "cartebancaire,espece,cheque", 10, 4, true, true);
            Restaurant r2 = new Restaurant("KFC", "34 rue des épaules 59000 Lille", "0627678789", "www.kfc.fr", "r2", 3.141820, 50.613579, false, false, "fastfood", "jeune", 2, 12, "cartebancaire,espece,cheque,ticketrestaurant", 5, 3, false, true);
            //Add restaurants
            restaurantDao.putRestaurant(r1);
            restaurantDao.putRestaurant(r2);

            //Schedule
            HoraireDAO horaireDao = new HoraireDAO(getApplicationContext());

            Horaire h1 = new Horaire(1, "LU 08.30 21.30");
            Horaire h2 = new Horaire(1, "LU 19.30 22.30");
            Horaire h3 = new Horaire(2, "MA 08.30 14.30");
            Horaire h4 = new Horaire(2, "MA 17.30 19.30");
            Horaire h5 = new Horaire(1, "MA 08.30 21.30");
            Horaire h6 = new Horaire(1, "JE 08.30 21.30");
            Horaire h7 = new Horaire(1, "VE 08.30 21.30");
            Horaire h8 = new Horaire(1, "SA 08.30 21.30");
            Horaire h9 = new Horaire(1, "DI 08.30 21.30");

            //Add schedules
            horaireDao.putHoraire(h1);
            horaireDao.putHoraire(h2);
            horaireDao.putHoraire(h3);
            horaireDao.putHoraire(h4);
            horaireDao.putHoraire(h5);
            horaireDao.putHoraire(h6);
            horaireDao.putHoraire(h7);
            horaireDao.putHoraire(h8);
            horaireDao.putHoraire(h9);


            //Generate random data in database

            //Elements List
            ArrayList<String> adresses = new ArrayList<>();
            adresses.add("marcel pagnol");
            adresses.add("jean bleu");
            adresses.add("michel rognon");
            adresses.add("belle epine");
            adresses.add("des acquets");
            adresses.add("de la route");
            adresses.add("des champs brisés");
            adresses.add("maréchal pétain");
            adresses.add("des os");
            adresses.add("albert leroy");
            adresses.add("platine du soleil");
            adresses.add("des pavés");

            ArrayList<String> villes = new ArrayList<>();
            villes.add("59000 Lille");
            villes.add("59850 Villeneuve d'Ascq");
            villes.add("59120 Hellemmes");
            villes.add("59165 Armentières");
            villes.add("59125 Marquette");
            villes.add("59658 Roubaix");
            villes.add("59359 Tourcoing");
            villes.add("59145 Lomme");
            villes.add("59697 Lambersart");
            villes.add("59694 Loos");
            villes.add("59123 Wasquehal");
            villes.add("59974 Lezennes");
            villes.add("62000 Lens");

            ArrayList<String> tels = new ArrayList<>();
            tels.add("0625626548");
            tels.add("0625656558");
            tels.add("0698756523");
            tels.add("0602659858");
            tels.add("0698784212");
            tels.add("0600152656");

            ArrayList<String> types = new ArrayList<>();
            types.add("pizzeria");
            types.add("halal");
            types.add("brasserie");
            types.add("vegetarien");
            types.add("gastronomique");
            types.add("bio");
            types.add("fastfood");
            types.add("casher");
            types.add("italien");
            types.add("chinois");

            ArrayList<String> atmospheres = new ArrayList<>();
            atmospheres.add("retro");
            atmospheres.add("musical");
            atmospheres.add("jeune");
            atmospheres.add("chic");
            atmospheres.add("romantique");
            atmospheres.add("historique");
            atmospheres.add("spectacle");

            ArrayList<String> payments = new ArrayList<>();
            payments.add("espece");
            payments.add("cheque");
            payments.add("chequevac");
            payments.add("cartebancaire");
            payments.add("ticketrestaurant");

            ArrayList<Integer> waiting = new ArrayList<>();
            waiting.add(0);
            waiting.add(5);
            waiting.add(15);
            waiting.add(30);
            waiting.add(45);
            waiting.add(60);

            ArrayList<String> joursListe = new ArrayList<>();
            joursListe.add("LU");
            joursListe.add("MA");
            joursListe.add("ME");
            joursListe.add("JE");
            joursListe.add("VE");
            joursListe.add("SA");
            joursListe.add("DI");

            ArrayList<String> hours = new ArrayList<>();
            hours.add("00.00");
            hours.add("00.30");
            hours.add("01.00");
            hours.add("01.30");
            hours.add("02.00");
            hours.add("02.30");
            hours.add("03.00");
            hours.add("03.30");
            hours.add("04.00");
            hours.add("04.30");
            hours.add("05.00");
            hours.add("05.30");
            hours.add("06.00");
            hours.add("06.30");
            hours.add("07.00");
            hours.add("07.30");
            hours.add("08.00");
            hours.add("08.30");
            hours.add("09.00");
            hours.add("09.30");
            hours.add("10.00");
            hours.add("10.30");
            hours.add("11.00");
            hours.add("12.30");
            hours.add("12.00");
            hours.add("13.30");
            hours.add("13.00");
            hours.add("14.30");
            hours.add("14.00");
            hours.add("15.30");
            hours.add("15.00");
            hours.add("16.30");
            hours.add("16.00");
            hours.add("17.30");
            hours.add("17.00");
            hours.add("18.30");
            hours.add("18.00");
            hours.add("19.30");
            hours.add("19.00");
            hours.add("20.30");
            hours.add("20.00");
            hours.add("21.30");
            hours.add("21.00");
            hours.add("22.30");
            hours.add("22.00");
            hours.add("23.00");
            hours.add("23.30");
            hours.add("24.00");

            //Fields
            String nom;
            String adresse;
            String tel;
            String site;
            String image = "r3";
            double latitude;
            double longitude;
            String type="";
            ArrayList<String> typetmp;
            String atmosphere="";
            ArrayList<String> atmospheretmp;
            String payment="";
            ArrayList<String> paymenttmp;
            int startBudget;
            int endBudget;
            int places;
            int waitingTimg;
            boolean terrace;
            boolean airConditionner;
            Restaurant r;

            Horaire h;
            String jour="";
            String hourBegin="";
            String hourEnd="";

            //Utils
            Random rand = new Random();
            int rdm,rdm2,rdm5;
            double rdm3,rdm4;

            for(int id = 3; id < 101 ; id++){
                type="";
                atmosphere="";
                payment="";
                nom = "Restaurant "+id;
                rdm = rand.nextInt(adresses.size());
                rdm2 = rand.nextInt(villes.size());
                adresse = id + " rue " + adresses.get(rdm) +" "+villes.get(rdm2);
                rdm = rand.nextInt(tels.size());
                tel = tels.get(rdm);
                site = "www.restaurant"+id+".fr";
                longitude = 3.000000 + (4.000000 - 3.000000) * rand.nextDouble();
                latitude= 50.000000 + (51.000000 - 50.000000) * rand.nextDouble();
                //Types
                rdm = rand.nextInt(9 - 1) + 1;
                typetmp = new ArrayList<>();
                for(int i = 0 ; i < rdm ; i++) {
                    rdm2 = rand.nextInt(types.size());
                    if(i==0){
                        if(!typetmp.contains(types.get(rdm2))) {
                            type += types.get(rdm2);
                        }
                    } else {
                        if(!typetmp.contains(types.get(rdm2))) {
                            type += "," + types.get(rdm2);
                        }
                    }
                    typetmp.add(types.get(rdm2));
                }
                //Atmosphere
                atmospheretmp = new ArrayList<>();
                rdm = rand.nextInt(10);
                for( int i = 0 ; i < rdm ; i++) {
                    rdm2 = rand.nextInt(atmospheres.size());
                    if(i==0){
                        if(!atmospheretmp.contains(atmospheres.get(rdm2))) {
                            atmosphere += atmospheres.get(rdm2);
                        }
                    } else {
                        if(!atmospheretmp.contains(atmospheres.get(rdm2))) {
                            atmosphere += "," + atmospheres.get(rdm2);
                        }
                    }
                    atmospheretmp.add(atmospheres.get(rdm2));
                }
                //Payment
                paymenttmp = new ArrayList<>();
                rdm = rand.nextInt(10);
                for( int i = 0 ; i < rdm ; i++) {
                    rdm2 = rand.nextInt(payments.size());
                    if(i==0) {
                        if(!paymenttmp.contains(payments.get(rdm2))) {
                            payment += payments.get(rdm2);
                        }
                    } else {
                        if(!paymenttmp.contains(payments.get(rdm2))) {
                            payment += "," + payments.get(rdm2);
                        }
                    }
                    paymenttmp.add(payments.get(rdm2));
                }
                startBudget= rand.nextInt(91);
                endBudget= rand.nextInt(91 - startBudget) + startBudget;
                places = rand.nextInt(11);
                waitingTimg = waiting.get(rand.nextInt(6));
                terrace = rand.nextBoolean();
                airConditionner = rand.nextBoolean();

                //Add restaurant in database
                r = new Restaurant(nom, adresse, tel, site, image, longitude, latitude, false, false, type, atmosphere, startBudget, endBudget, payment, places, waitingTimg, terrace, airConditionner);
                restaurantDao.putRestaurant(r);

                //Schedules
                rdm = rand.nextInt(16 - 1 + 1) + 1;
                for( int j = 0 ; j < rdm ; j++) {
                    //Days
                    rdm5 = rand.nextInt(joursListe.size());
                    jour = joursListe.get(rdm5);
                    //Begin Hour
                    rdm5 = rand.nextInt(hours.size());
                    hourBegin = hours.get(rdm5);
                    //End Hour
                    hourEnd = hours.get(rand.nextInt(hours.size() - rdm5) + rdm5);
                    //Add schedule
                    h = new Horaire(id,jour+" "+hourBegin+" "+hourEnd);
                    horaireDao.putHoraire(h);
                }
            }
        }
    }
}
