package com.platine.liveresto.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.platine.liveresto.R;
import com.platine.liveresto.model.Horaire;
import com.platine.liveresto.model.HoraireDAO;
import com.platine.liveresto.model.Restaurant;
import com.platine.liveresto.model.RestaurantDAO;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final int FILTRESCODE = 42;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private String mLastUpdateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //fragment activity for the google Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");
        setSupportActionBar(myToolbar);

        buildGoogleApiClient();
    }

    /**
     * Actions to do after the creation of the option menu
     * @param menu to inflate
     * @return true if all went well
     */
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
                //item menu option to display the filters
                Intent filtres = new Intent(context, FiltreActivity.class);
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
        //enable to catch the user's current location
        mMap.setMyLocationEnabled(true);
        //enable to display the user's current location on the map
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

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
        locationManager.requestLocationUpdates(bestProvider, 100, 1, this);
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10F));*/
        LatLng userLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10F));

        //Retrieve all the restaurants to display them
        RestaurantDAO restosDAO = new RestaurantDAO(getApplicationContext());
        ArrayList<Restaurant> allRestos = restosDAO.getRestaurants();
        for (Restaurant resto : allRestos) {
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
        if (restaurantDao.getRestaurants().isEmpty()) {
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

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected())
            startLocationUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected())
            stopLocationUpdates();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
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
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "connection failed", Toast.LENGTH_LONG).show();
    }

    private void startLocationUpdates() {
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        }
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
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
    }

    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
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
}
