package com.platine.liveresto.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    //Objet filtre global correspondant aux filtres courant
    private Filtre filterGlobal;
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

        System.out.println("AFFICHAGE DES FILTRES : " + filterGlobal);

        // ******************** DB  ********************
        fixtures();

        //Affichage des restaurants crées
       /* System.out.println("Liste des restaurants : ");
        RestaurantDAO rdao = new RestaurantDAO(getApplicationContext());
        ArrayList<Restaurant> liste = rdao.getRestaurants();
        for (Restaurant r : liste) {
            System.out.println(r);
        }*/

        System.out.println("RESTAURANTS ----------------------------------------------------------------------------------------------------------------------");
        //Affichage des restau correspondant aux filtres
        RestaurantDAO rdao = new RestaurantDAO(getApplicationContext());
        ArrayList<Restaurant> liste2 = filterGlobal.getRestaurantsFilter(rdao.getRestaurants());
        for (Restaurant r : liste2) {
            System.out.println(r);
        }

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


    protected void onStop() {
        super.onStop();

        //Sauvegarde des sharedPreferences
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILTRESCODE) {
            if (resultCode == RESULT_OK) {
                Intent i = data;
                filterGlobal = new Filtre(i.getDoubleExtra("distanceFilter", 0.0), i.getStringArrayListExtra("daysFilter"), i.getDoubleExtra("hourBeginFilter", 0.0), i.getDoubleExtra("hourEndFilter", 0.0), i.getStringArrayListExtra("typeFilter"), i.getIntExtra("startBudgetFilter", 0), i.getIntExtra("endBudgetFilter", 0), i.getStringArrayListExtra("paymentFilter"), i.getStringArrayListExtra("atmosphereFilter"), i.getIntExtra("placesFilter", 0), i.getIntExtra("waitingTimeFilter", 0), i.getBooleanExtra("terraceFilter", false), i.getBooleanExtra("airConditionnerFilter", false));
                updateMarkerOnMap();
            }
        }
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
                startActivityForResult(filtres, FILTRESCODE);
                return true;
            default:
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


      /*  if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);*/

        /*LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10F));*/

        updateMarkerOnMap();
    }

    public void updateMarkerOnMap() {
        mMap.clear();

       /* if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);*/

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getApplicationContext(), RestaurantActivity.class);

                String title = marker.getTitle();
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });

        RestaurantDAO restosDAO = new RestaurantDAO(getApplicationContext());
        ArrayList<Restaurant> allRestos = filterGlobal.getRestaurantsFilter(restosDAO.getRestaurants());

        if (!allRestos.isEmpty()) {
            LatLng firstRestaurantPosition = new LatLng(allRestos.get(0).getLatitude(), allRestos.get(0).getLongitude());
            mMap.addMarker(new MarkerOptions().position(firstRestaurantPosition).title(allRestos.get(0).getName())).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_restaurant));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(firstRestaurantPosition));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
            allRestos.remove(0);
            for (Restaurant resto : allRestos) {
                LatLng position = new LatLng(resto.getLatitude(), resto.getLongitude());
                mMap.addMarker(new MarkerOptions().position(position).title(resto.getName())).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_restaurant));
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



    /**
     * Add restaurant to database
     */
    public void fixtures() {

        // Restaurant
        RestaurantDAO restaurantDao = new RestaurantDAO(getApplicationContext());

        //Si la base est vide on la remplit
        if(restaurantDao.getRestaurants().isEmpty()) {

            //Les deux restaurants pour la vidéo
            Restaurant r1 = new Restaurant("Quick", "5 rue des fleurs 59000 Lille", "0656546576", "www.quick.fr", "r1", 3.137273, 50.612136, false, false, "fastfood", "musical", 2, 11, "cartebancaire,espece,cheque", 10, 10, true, true);
            Restaurant r2 = new Restaurant("KFC", "34 rue des épaules 59000 Lille", "0627678789", "www.kfc.fr", "r2", 3.141820, 50.613579, false, false, "fastfood", "jeune", 2, 12, "cartebancaire,espece,cheque,ticketrestaurant", 5, 15, false, true);
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


            //Remplissage random de la BDD

            //Listes d'éléments
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

            //Champs
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

                //Ajout du restaurant
                r = new Restaurant(nom, adresse, tel, site, image, longitude, latitude, false, false, type, atmosphere, startBudget, endBudget, payment, places, waitingTimg, terrace, airConditionner);
                restaurantDao.putRestaurant(r);

                //Horaires
                rdm = rand.nextInt(16 - 1 + 1) + 1;
                for( int j = 0 ; j < rdm ; j++) {
                    //Jours
                    rdm5 = rand.nextInt(joursListe.size());
                    jour = joursListe.get(rdm5);
                    //Heure début
                    rdm5 = rand.nextInt(hours.size());
                    hourBegin = hours.get(rdm5);
                    //Heure fin
                    hourEnd = hours.get(rand.nextInt(hours.size() - rdm5) + rdm5);
                    //Ajout de l'horaire
                    h = new Horaire(id,jour+" "+hourBegin+" "+hourEnd);
                    horaireDao.putHoraire(h);
                }
            }

        }
    }
}
