package com.platine.liveresto.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.platine.liveresto.model.Horaire;
import com.platine.liveresto.model.HoraireDAO;
import com.platine.liveresto.R;
import com.platine.liveresto.model.Restaurant;
import com.platine.liveresto.model.RestaurantDAO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    private Button buttonDistance;
    private Button buttonSchedule;
    private Button buttonType;
    private Button buttonBudget;
    private Button buttonPayment;
    private Button buttonAtmosphere;
    private Button buttonNumber;
    private Button buttonWaitingTime;
    private Button buttonOther;
    private RecyclerView recyclerViewDistance;
    private RecyclerView recyclerViewSchedule;
    private RecyclerView recyclerViewType;
    private RecyclerView recyclerViewBudget;
    private RecyclerView recyclerViewPayment;
    private RecyclerView recyclerViewAtmosphere;
    private RecyclerView recyclerViewWaitingTime;
    private RecyclerView recyclerViewNumber;
    private RecyclerView recyclerViewOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // ******************** DB  ********************
        fixtures();

        RestaurantDAO restaurantDao = new RestaurantDAO(getApplicationContext());
        ArrayList<Restaurant> liste = restaurantDao.getRestaurants();

        HoraireDAO horaireDao = new HoraireDAO(getApplicationContext());
        ArrayList<Horaire> liste2 = horaireDao.getSchedule(liste.get(0));
        ArrayList<Horaire> liste3 = horaireDao.getSchedule(liste.get(1));

        System.out.println("Liste des restaurants :"+ liste.toString());
        System.out.println("Horaires du restaurant 1 : "+liste2.toString());
        System.out.println("Horaires du restaurant 2 : "+liste3.toString());



        // ******************** TOOLBAR ********************
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setTitle("Filtres");



        // ******************** RECYCLERVIEW ********************
        ElementsAdapter elementsAdapter = new ElementsAdapter(3);

        recyclerViewDistance = (RecyclerView) findViewById(R.id.recycler_view_distance);
        recyclerViewDistance.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerViewDistance.setLayoutManager(gridLayoutManager);
        recyclerViewDistance.setAdapter(elementsAdapter);
        recyclerViewDistance.setVisibility(View.GONE);

        recyclerViewSchedule = (RecyclerView) findViewById(R.id.recycler_view_schedule);
        recyclerViewSchedule.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerSchedule = new GridLayoutManager(this, 3);
        recyclerViewSchedule.setLayoutManager(gridLayoutManagerSchedule);
        recyclerViewSchedule.setAdapter(elementsAdapter);
        recyclerViewSchedule.setVisibility(View.GONE);

        recyclerViewType = (RecyclerView) findViewById(R.id.recycler_view_type);
        recyclerViewType.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerType = new GridLayoutManager(this, 3);
        recyclerViewType.setLayoutManager(gridLayoutManagerType);
        recyclerViewType.setAdapter(elementsAdapter);
        recyclerViewType.setVisibility(View.GONE);

        recyclerViewBudget = (RecyclerView) findViewById(R.id.recycler_view_budget);
        recyclerViewBudget.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerBudget = new GridLayoutManager(this, 3);
        recyclerViewBudget.setLayoutManager(gridLayoutManagerBudget);
        recyclerViewBudget.setAdapter(elementsAdapter);
        recyclerViewBudget.setVisibility(View.GONE);

        recyclerViewPayment = (RecyclerView) findViewById(R.id.recycler_view_payment);
        recyclerViewPayment.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerPayment = new GridLayoutManager(this, 3);
        recyclerViewPayment.setLayoutManager(gridLayoutManagerPayment);
        recyclerViewPayment.setAdapter(elementsAdapter);
        recyclerViewPayment.setVisibility(View.GONE);

        recyclerViewAtmosphere = (RecyclerView) findViewById(R.id.recycler_view_atmosphere);
        recyclerViewAtmosphere.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerAtmosphere = new GridLayoutManager(this, 3);
        recyclerViewAtmosphere.setLayoutManager(gridLayoutManagerAtmosphere);
        recyclerViewAtmosphere.setAdapter(elementsAdapter);
        recyclerViewAtmosphere.setVisibility(View.GONE);

        recyclerViewNumber = (RecyclerView) findViewById(R.id.recycler_view_number);
        recyclerViewNumber.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerNumber = new GridLayoutManager(this, 3);
        recyclerViewNumber.setLayoutManager(gridLayoutManagerNumber);
        recyclerViewNumber.setAdapter(elementsAdapter);
        recyclerViewNumber.setVisibility(View.GONE);

        recyclerViewWaitingTime = (RecyclerView) findViewById(R.id.recycler_view_waitingtime);
        recyclerViewWaitingTime.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerWaitingTime = new GridLayoutManager(this, 3);
        recyclerViewWaitingTime.setLayoutManager(gridLayoutManagerWaitingTime);
        recyclerViewWaitingTime.setAdapter(elementsAdapter);
        recyclerViewWaitingTime.setVisibility(View.GONE);

        recyclerViewOther = (RecyclerView) findViewById(R.id.recycler_view_other);
        recyclerViewOther.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerOther = new GridLayoutManager(this, 3);
        recyclerViewOther.setLayoutManager(gridLayoutManagerOther);
        recyclerViewOther.setAdapter(elementsAdapter);
        recyclerViewOther.setVisibility(View.GONE);

        // ******************** BUTTON ********************
        addListenerOnButton();
    }


    /**
     *
     */
    public void addListenerOnButton() {
        buttonDistance = (Button) findViewById(R.id.button_distance);
        buttonDistance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(recyclerViewDistance.getVisibility() == View.GONE){
                    recyclerViewDistance.setVisibility(View.VISIBLE);
                }else{
                    recyclerViewDistance.setVisibility(View.GONE);
                }
            }
        });

        buttonSchedule = (Button) findViewById(R.id.button_schedule);
        buttonSchedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(recyclerViewSchedule.getVisibility() == View.GONE){
                    recyclerViewSchedule.setVisibility(View.VISIBLE);
                }else{
                    recyclerViewSchedule.setVisibility(View.GONE);
                }
            }
        });

        buttonType = (Button) findViewById(R.id.button_type);
        buttonType.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(recyclerViewType.getVisibility() == View.GONE){
                    recyclerViewType.setVisibility(View.VISIBLE);
                }else{
                    recyclerViewType.setVisibility(View.GONE);
                }
            }
        });

        buttonBudget = (Button) findViewById(R.id.button_budget);
        buttonBudget.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(recyclerViewBudget.getVisibility() == View.GONE){
                    recyclerViewBudget.setVisibility(View.VISIBLE);
                }else{
                    recyclerViewBudget.setVisibility(View.GONE);
                }
            }
        });

        buttonPayment = (Button) findViewById(R.id.button_payment);
        buttonPayment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(recyclerViewPayment.getVisibility() == View.GONE){
                    recyclerViewPayment.setVisibility(View.VISIBLE);
                }else{
                    recyclerViewPayment.setVisibility(View.GONE);
                }
            }
        });

        buttonAtmosphere = (Button) findViewById(R.id.button_atmosphere);
        buttonAtmosphere.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(recyclerViewAtmosphere.getVisibility() == View.GONE){
                    recyclerViewAtmosphere.setVisibility(View.VISIBLE);
                }else{
                    recyclerViewAtmosphere.setVisibility(View.GONE);
                }
            }
        });

        buttonNumber = (Button) findViewById(R.id.button_number);
        buttonNumber.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(recyclerViewNumber.getVisibility() == View.GONE){
                    recyclerViewNumber.setVisibility(View.VISIBLE);
                }else{
                    recyclerViewNumber.setVisibility(View.GONE);
                }
            }
        });

        buttonWaitingTime = (Button) findViewById(R.id.button_waitingtime);
        buttonWaitingTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(recyclerViewWaitingTime.getVisibility() == View.GONE){
                    recyclerViewWaitingTime.setVisibility(View.VISIBLE);
                }else{
                    recyclerViewWaitingTime.setVisibility(View.GONE);
                }
            }
        });

        buttonOther = (Button) findViewById(R.id.button_other);
        buttonOther.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(recyclerViewOther.getVisibility() == View.GONE){
                    recyclerViewOther.setVisibility(View.VISIBLE);
                }else{
                    recyclerViewOther.setVisibility(View.GONE);
                }
            }
        });
    }

    public class ElementsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int VIEW_NORMAL = 1;

        private int datasetSize;


        public class ViewHolder extends RecyclerView.ViewHolder {

            public View textView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = itemView;
            }
        }

        public ElementsAdapter(int size) {
            this.datasetSize = size;
        }


        @Override
        public int getItemViewType(int position) {
            return VIEW_NORMAL;
        }

        @Override
        public int getItemCount() {
            return datasetSize ;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View textView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_element, parent, false);
            return new ViewHolder(textView);

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

            if (position == 0) return;

        }
    }

    /**
     * Add restaurant to database
     */
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
