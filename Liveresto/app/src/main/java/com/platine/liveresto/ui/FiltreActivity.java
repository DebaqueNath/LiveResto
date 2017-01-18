package com.platine.liveresto.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.platine.liveresto.R;
import com.platine.liveresto.model.Data;
import com.platine.liveresto.model.Horaire;
import com.platine.liveresto.model.HoraireDAO;
import com.platine.liveresto.model.Restaurant;
import com.platine.liveresto.model.RestaurantDAO;

import java.util.ArrayList;
import java.util.List;

public class FiltreActivity extends AppCompatActivity  {
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
    private Toolbar myToolbar;
    private List<Data> distanceList;
    private List<Data> scheduleList;
    private List<Data> typeList;
    private List<Data> budgetList;
    private List<Data> paymentList;
    private List<Data> atmosphereList;
    private List<Data> numberList;
    private List<Data> waitingTimeList;
    private List<Data> otherList;
    private ImageView backArrowFilter;
    private TextView title_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtre);

        // ******************** DB  ********************
        fixtures();

        backArrowFilter = (ImageView) findViewById(R.id.back_arrow_filter);
        backArrowFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),RestaurantActivity.class);
                startActivity(i);
            }
        });


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



        initToolbar();

        initFilters();

        initRecyclerView();

        addListenerOnButton();
    }

    /**
     *
     */
    public void initToolbar(){
        myToolbar = (Toolbar) findViewById(R.id.toolbar_filter);
        setSupportActionBar(myToolbar);
        setTitle("");
        this.title_toolbar = (TextView) findViewById(R.id.toolbar_title_filter);
        this.title_toolbar.setText("Filtres");
    }

    /**
     *
     */
    public void initFilters(){
        distanceList = new ArrayList<>();
        distanceList.add(new Data("<1km"));
        distanceList.add(new Data("<5km"));
        distanceList.add(new Data("<10km"));

        scheduleList = new ArrayList<>();
        scheduleList.add(new Data("L"));
        scheduleList.add(new Data("M"));
        scheduleList.add(new Data("M"));
        scheduleList.add(new Data("J"));
        scheduleList.add(new Data("V"));
        scheduleList.add(new Data("S"));
        scheduleList.add(new Data("D"));

        typeList = new ArrayList<>();
        typeList.add(new Data("Pizzeria", R.drawable.pizzeria));
        typeList.add(new Data("halal", R.drawable.halal));
        typeList.add(new Data("Brasserie", R.drawable.brasserie));
        typeList.add(new Data("Végétarien", R.drawable.vegetarien));
        typeList.add(new Data("Gastronomique", R.drawable.gastronomique));
        typeList.add(new Data("Bio", R.drawable.bio));
        typeList.add(new Data("Fast food", R.drawable.fastfood));
        typeList.add(new Data("Casher", R.drawable.casher));
        typeList.add(new Data("italien", R.drawable.italien));
        typeList.add(new Data("Chinois", R.drawable.chinois));

        budgetList = new ArrayList<>();
        budgetList.add(new Data("<20"));
        budgetList.add(new Data("20 à 39"));
        budgetList.add(new Data("40 à 59"));
        budgetList.add(new Data("60 à 79"));
        budgetList.add(new Data(">80"));

        paymentList = new ArrayList<>();
        paymentList.add(new Data("CB"));
        paymentList.add(new Data("Cheque"));
        paymentList.add(new Data("Cheque vac"));
        paymentList.add(new Data("Espece"));
        paymentList.add(new Data("Ticket resto"));

        atmosphereList = new ArrayList<>();
        atmosphereList.add(new Data("Retro"));
        atmosphereList.add(new Data("Musical"));
        atmosphereList.add(new Data("Jeune"));
        atmosphereList.add(new Data("Chic"));
        atmosphereList.add(new Data("Romantique"));
        atmosphereList.add(new Data("Historique"));
        atmosphereList.add(new Data("Spectacle"));

        numberList = new ArrayList<>();
        numberList.add(new Data("TODO"));

        waitingTimeList = new ArrayList<>();
        waitingTimeList.add(new Data("TODO"));

        otherList = new ArrayList<>();
        otherList.add(new Data("TODO"));
    }

    /**
     *
     */
    public void initRecyclerView(){
        recyclerViewDistance = (RecyclerView) findViewById(R.id.recycler_view_distance);
        recyclerViewDistance.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerViewDistance.setLayoutManager(gridLayoutManager);
        recyclerViewDistance.setAdapter(new ElementsAdapter(distanceList));
        recyclerViewDistance.setVisibility(View.GONE);

        recyclerViewSchedule = (RecyclerView) findViewById(R.id.recycler_view_schedule);
        recyclerViewSchedule.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerSchedule = new GridLayoutManager(this, 3);
        recyclerViewSchedule.setLayoutManager(gridLayoutManagerSchedule);
        recyclerViewSchedule.setAdapter(new ElementsAdapter(scheduleList));
        recyclerViewSchedule.setVisibility(View.GONE);

        recyclerViewType = (RecyclerView) findViewById(R.id.recycler_view_type);
        recyclerViewType.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerType = new GridLayoutManager(this, 3);
        recyclerViewType.setLayoutManager(gridLayoutManagerType);
        recyclerViewType.setAdapter(new ElementsAdapter(typeList));
        recyclerViewType.setVisibility(View.GONE);

        recyclerViewBudget = (RecyclerView) findViewById(R.id.recycler_view_budget);
        recyclerViewBudget.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerBudget = new GridLayoutManager(this, 3);
        recyclerViewBudget.setLayoutManager(gridLayoutManagerBudget);
        recyclerViewBudget.setAdapter(new ElementsAdapter(budgetList));
        recyclerViewBudget.setVisibility(View.GONE);

        recyclerViewPayment = (RecyclerView) findViewById(R.id.recycler_view_payment);
        recyclerViewPayment.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerPayment = new GridLayoutManager(this, 3);
        recyclerViewPayment.setLayoutManager(gridLayoutManagerPayment);
        recyclerViewPayment.setAdapter(new ElementsAdapter(paymentList));
        recyclerViewPayment.setVisibility(View.GONE);

        recyclerViewAtmosphere = (RecyclerView) findViewById(R.id.recycler_view_atmosphere);
        recyclerViewAtmosphere.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerAtmosphere = new GridLayoutManager(this, 3);
        recyclerViewAtmosphere.setLayoutManager(gridLayoutManagerAtmosphere);
        recyclerViewAtmosphere.setAdapter(new ElementsAdapter(atmosphereList));
        recyclerViewAtmosphere.setVisibility(View.GONE);

        recyclerViewNumber = (RecyclerView) findViewById(R.id.recycler_view_number);
        recyclerViewNumber.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerNumber = new GridLayoutManager(this, 3);
        recyclerViewNumber.setLayoutManager(gridLayoutManagerNumber);
        recyclerViewNumber.setAdapter(new ElementsAdapter(numberList));
        recyclerViewNumber.setVisibility(View.GONE);

        recyclerViewWaitingTime = (RecyclerView) findViewById(R.id.recycler_view_waitingtime);
        recyclerViewWaitingTime.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerWaitingTime = new GridLayoutManager(this, 3);
        recyclerViewWaitingTime.setLayoutManager(gridLayoutManagerWaitingTime);
        recyclerViewWaitingTime.setAdapter(new ElementsAdapter(waitingTimeList));
        recyclerViewWaitingTime.setVisibility(View.GONE);

        recyclerViewOther = (RecyclerView) findViewById(R.id.recycler_view_other);
        recyclerViewOther.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerOther = new GridLayoutManager(this, 3);
        recyclerViewOther.setLayoutManager(gridLayoutManagerOther);
        recyclerViewOther.setAdapter(new ElementsAdapter(otherList));
        recyclerViewOther.setVisibility(View.GONE);
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

    public class ElementsAdapter extends RecyclerView.Adapter<ElementsAdapter.ViewHolder> {
        private static final int VIEW_NORMAL = 1;
        private List<Data> contactList;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            protected TextView vName;
            protected ImageView vImage;
            public View textView;
            public boolean isSelected;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = itemView;
                vName =  (TextView) itemView.findViewById(R.id.title);
                itemView.setOnClickListener(this);
                vImage =  (ImageView) itemView.findViewById(R.id.image);
                isSelected = false;
            }

            @Override
            public void onClick(View view) {
                if(!this.isSelected){
                    view.setBackgroundResource(R.drawable.card_border);
                    this.isSelected = true;
                }else{
                    view.setBackgroundColor(Color.WHITE);
                    this.isSelected = false;
                }
            }
        }


        public ElementsAdapter(List<Data> contactList) {
            this.contactList = contactList;
        }


        @Override
        public int getItemViewType(int position) {
            return VIEW_NORMAL;
        }

        @Override
        public int getItemCount() {
            return this.contactList.size() ;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            Data ci = contactList.get(position);
            viewHolder.vName.setText(ci.name);
            viewHolder.vImage.setImageResource(ci.imageId);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View textView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_element, parent, false);
            return new ViewHolder(textView);
        }
    }

    /**
     * Add restaurant to database
     */
    public void fixtures() {
        // Restaurant
        RestaurantDAO restaurantDao = new RestaurantDAO(getApplicationContext());

        Restaurant r1 = new Restaurant("Quick", "5 rue des fleurs 59000 Lille", "0656546576", "www.quick.fr", "r1", 3.121059, 50.616862, false, false, "Fast-Food", "Musical", 2, 11, "cartebancaire,especes,cheque", 10, 10, true, true);
        Restaurant r2 = new Restaurant("KFC", "34 rue des épaules 59000 Lille", "0627678789", "www.kfc.fr", "r2", 3.071162, 50.636491, false, false, "Fast-Food", "Jeune", 2, 12, "cartebancaire,especes,cheque,ticketsrestaurant", 5, 15, false, true);
        //Add restaurant
        restaurantDao.putRestaurant(r1);
        restaurantDao.putRestaurant(r2);

        // Schedule
        HoraireDAO horaireDao = new HoraireDAO(getApplicationContext());

        Horaire h1 = new Horaire(1,"LU 08.30 14.30");
        Horaire h2 = new Horaire(1,"LU 19.30 22.30");
        Horaire h3 = new Horaire(2,"MA 08.30 14.30");
        Horaire h4 = new Horaire(2,"MA 17.30 19.30");
        //Add schedule

        horaireDao.putHoraire(h1);
        horaireDao.putHoraire(h2);
        horaireDao.putHoraire(h3);
        horaireDao.putHoraire(h4);
    }
}
