package com.platine.liveresto.ui;

import android.content.Context;
import android.content.Intent;
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
import com.platine.liveresto.model.Restaurant;
import com.platine.liveresto.model.RestaurantDAO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final int FILTRESCODE = 42;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
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
}
