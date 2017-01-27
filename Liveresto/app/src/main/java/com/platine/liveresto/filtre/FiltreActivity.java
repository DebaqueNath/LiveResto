package com.platine.liveresto.filtre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.platine.liveresto.R;
import com.platine.liveresto.model.Data;
import com.platine.liveresto.model.ElementsAdapter;
import com.platine.liveresto.model.ElementsAdapterSimple;
import com.platine.liveresto.model.Filtre;
import com.platine.liveresto.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

public class FiltreActivity extends AppCompatActivity implements RangeSeekBar.OnRangeSeekBarChangeListener<Number>, CompoundButton.OnCheckedChangeListener, ElementsAdapterSimple.Listener{

    private Button buttonDistance;
    private Button buttonSchedule;
    private Button buttonType;
    private Button buttonBudget;
    private Button buttonPayment;
    private Button buttonAtmosphere;
    private Button buttonNumber;
    private Button buttonWaitingTime;
    private Button buttonOther;
    private RecyclerView recyclerViewSchedule;
    private RecyclerView recyclerViewType;
    private RecyclerView recyclerViewBudget;
    private RecyclerView recyclerViewPayment;
    private RecyclerView recyclerViewAtmosphere;
    private RecyclerView recyclerViewWaitingTime;
    private RecyclerView recyclerViewOther;
    private Toolbar myToolbar;
    private List<Data> scheduleList;
    private List<Data> typeList;
    private List<Data> budgetList;
    private List<Data> paymentList;
    private List<Data> atmosphereList;
    private List<Data> waitingTimeList;
    private ImageView backArrowFilter;
    private TextView title_toolbar;
    private RangeSeekBar rangeSeekBarDistance;
    private RangeSeekBar rangeSeekBarSchedule;
    private RangeSeekBar rangeSeekBarPlaces;
    private Filtre filterGlobal;
    public static final int FILTRESCODE = 42;
    private Switch switchTerrace;
    private Switch switchAirConditionner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtre);


        //On récupère l'intent et les données
        Intent i = getIntent();
        filterGlobal = new Filtre(i.getDoubleExtra("distanceFilter",0.0),i.getStringArrayListExtra("daysFilter"),i.getDoubleExtra("hourBeginFilter",0.0),i.getDoubleExtra("hourEndFilter",0.0),i.getStringArrayListExtra("typeFilter"),i.getIntExtra("startBudgetFilter",0),i.getIntExtra("endBudgetFilter",0),i.getStringArrayListExtra("paymentFilter"),i.getStringArrayListExtra("atmosphereFilter"),i.getIntExtra("placesFilter",0),i.getIntExtra("waitingTimeFilter",0),i.getBooleanExtra("terraceFilter",false),i.getBooleanExtra("airConditionnerFilter",false));


        backArrowFilter = (ImageView) findViewById(R.id.back_arrow_filter);
        backArrowFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i = new Intent(getApplicationContext(),RestaurantActivity.class);
                startActivity(i);*/
                returnToMap();
            }
        });


        initToolbar();

        initFilters();

        initRecyclerView();

        //Distance
        rangeSeekBarDistance.setSelectedMaxValue(filterGlobal.getDistanceMax());

        addListenerOnButton();

    }

    @Override
    public void onCardViewClick(Data d) {
        if(d.getName()=="Distance"){

        }

    }

    @Override
    public void onBackPressed() {
        returnToMap();
    }


    private void returnToMap(){
        Intent i = getIntent();
        i.putExtra("distanceFilter", filterGlobal.getDistanceMax());
        i.putExtra("daysFilter", filterGlobal.getDays());
        i.putExtra("hourBeginFilter", filterGlobal.getHourBegin());
        i.putExtra("hourEndFilter", filterGlobal.getHourEnd());
        i.putExtra("typeFilter", filterGlobal.getType());
        i.putExtra("startBudgetFilter", filterGlobal.getStartBudget());
        i.putExtra("endBudgetFilter", filterGlobal.getEndBudget());
        i.putExtra("paymentFilter", filterGlobal.getPayment());
        i.putExtra("atmosphereFilter", filterGlobal.getAtmosphere());
        i.putExtra("placesFilter", filterGlobal.getPlaces());
        i.putExtra("waitingTimeFilter", filterGlobal.getWaitingTime());
        i.putExtra("terraceFilter", filterGlobal.isTerrace());
        i.putExtra("airConditionnerFilter", filterGlobal.isAirConditionner());
        setResult(RESULT_OK, i);
        finish();
    }



    public void onRangeSeekBarValuesChanged(RangeSeekBar<Number> bar, Number minValue, Number maxValue){
        if(bar.equals(this.rangeSeekBarDistance)) {
            if(minValue==maxValue){
                rangeSeekBarDistance.setSelectedMaxValue((int)maxValue+1);
            }
            filterGlobal.setDistanceMax(Double.parseDouble(maxValue.toString()));
        } else if(bar.equals(this.rangeSeekBarSchedule)){
            //Si les deux valeurs sont égales on désactive un thumb sinon on active les deux
            if(minValue==maxValue){
                rangeSeekBarSchedule.setSelectedMaxValue((int)maxValue+1);
            }
            filterGlobal.setHourBegin(Double.parseDouble(minValue.toString()));
            filterGlobal.setHourEnd(Double.parseDouble(maxValue.toString()));
        } else if(bar.equals(this.rangeSeekBarPlaces)){
            //Si les deux valeurs sont égales on désactive un thumb sinon on active les deux
            if(minValue==maxValue){
                rangeSeekBarPlaces.setSelectedMaxValue((int)maxValue+1);
            }
            filterGlobal.setPlaces(Integer.parseInt(maxValue.toString()));
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getText() == "Terrasse"){
            if(isChecked){
                filterGlobal.setTerrace(true);
            } else {
                filterGlobal.setTerrace(false);
            }
        } else if(buttonView.getText() == "Climatisation"){
            if(isChecked){
                filterGlobal.setAirConditionner(true);
            } else {
                filterGlobal.setAirConditionner(false);
            }
        }
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


        //Schedule
        scheduleList = new ArrayList<>();
        scheduleList.add(new Data("Lundi",R.drawable.icon_monday,filterGlobal.getDays().contains("LU")));
        scheduleList.add(new Data("Mardi",R.drawable.icon_tuesday,filterGlobal.getDays().contains("MA")));
        scheduleList.add(new Data("Mercredi", R.drawable.icon_wednesday,filterGlobal.getDays().contains("ME")));
        scheduleList.add(new Data("Jeudi",R.drawable.icon_thursday,filterGlobal.getDays().contains("JE")));
        scheduleList.add(new Data("Vendredi",R.drawable.icon_friday,filterGlobal.getDays().contains("VE")));
        scheduleList.add(new Data("Samedi",R.drawable.icon_saturday,filterGlobal.getDays().contains("SA")));
        scheduleList.add(new Data("Dimanche",R.drawable.icon_sunday,filterGlobal.getDays().contains("DI")));
        scheduleList.add(new Data("TOUS",R.drawable.icon_ticket,filterGlobal.getDays().isEmpty()));

        typeList = new ArrayList<>();
        typeList.add(new Data("Pizzeria", R.drawable.pizzeria,false));
        typeList.add(new Data("Halal", R.drawable.halal,false));
        typeList.add(new Data("Brasserie", R.drawable.brasserie,false));
        typeList.add(new Data("Végétarien", R.drawable.vegetarien,false));
        typeList.add(new Data("Gastronomique", R.drawable.gastronomique,false));
        typeList.add(new Data("Bio", R.drawable.bio,false));
        typeList.add(new Data("Fast-food", R.drawable.fastfood,false));
        typeList.add(new Data("Casher", R.drawable.casher,false));
        typeList.add(new Data("Italien", R.drawable.italien,false));
        typeList.add(new Data("Chinois", R.drawable.chinois,false));
        typeList.add(new Data("TOUS",R.drawable.icon_ticket,false));

        budgetList = new ArrayList<>();
        budgetList.add(new Data("<20",R.drawable.icon_inf_20,false));
        budgetList.add(new Data("20 à 39",R.drawable.icon_from_20_to_39,false));
        budgetList.add(new Data("40 à 59",R.drawable.icon_from_40_to_59,false));
        budgetList.add(new Data("60 à 79",R.drawable.icon_from_60_to_79,false));
        budgetList.add(new Data(">80",R.drawable.icon_sup_80,false));
        budgetList.add(new Data("TOUS",R.drawable.icon_ticket,false));

        paymentList = new ArrayList<>();
        paymentList.add(new Data("Carte bancaire",R.drawable.icon_cb,false));
        paymentList.add(new Data("Cheque",R.drawable.icon_cheque,false));
        paymentList.add(new Data("Cheque vacances",R.drawable.icon_vac,false));
        paymentList.add(new Data("Espece",R.drawable.icon_money,false));
        paymentList.add(new Data("Ticket restaurant",R.drawable.icon_ticket,false));
        paymentList.add(new Data("TOUS",R.drawable.icon_ticket,false));

        atmosphereList = new ArrayList<>();
        atmosphereList.add(new Data("Retro",R.drawable.icon_ticket,false));
        atmosphereList.add(new Data("Musical",R.drawable.icon_ticket,false));
        atmosphereList.add(new Data("Jeune",R.drawable.icon_ticket,false));
        atmosphereList.add(new Data("Chic",R.drawable.icon_ticket,false));
        atmosphereList.add(new Data("Romantique",R.drawable.icon_ticket,false));
        atmosphereList.add(new Data("Historique",R.drawable.icon_ticket,false));
        atmosphereList.add(new Data("Spectacle",R.drawable.icon_ticket,false));
        atmosphereList.add(new Data("TOUS",R.drawable.icon_ticket,false));

        waitingTimeList = new ArrayList<>();
        waitingTimeList.add(new Data("<5min",R.drawable.icon_ticket,false));
        waitingTimeList.add(new Data("<10min",R.drawable.icon_ticket,false));
        waitingTimeList.add(new Data("<15min",R.drawable.icon_ticket,false));
        waitingTimeList.add(new Data("<30min",R.drawable.icon_ticket,false));
        waitingTimeList.add(new Data("<45min",R.drawable.icon_ticket,false));
        waitingTimeList.add(new Data("<60min",R.drawable.icon_ticket,false));
        waitingTimeList.add(new Data("TOUS",R.drawable.icon_ticket,false));

    }


    /**
     *
     */
    public void initRecyclerView(){
        //On initialise tous les éléments
        rangeSeekBarDistance = (RangeSeekBar) findViewById(R.id.rangeseekbardistance);
        rangeSeekBarDistance.setLabel("km");
        rangeSeekBarDistance.setRangeValues(0,50);
        rangeSeekBarDistance.setSelectedMaxValue(0);
        rangeSeekBarDistance.setOnRangeSeekBarChangeListener(this);
        rangeSeekBarDistance.setVisibility(View.GONE);

        rangeSeekBarSchedule = (RangeSeekBar) findViewById(R.id.rangeseekbarschedule);
        rangeSeekBarSchedule.setLabel("h");
        rangeSeekBarSchedule.setRangeValues(0,24);
        rangeSeekBarSchedule.setSelectedMinValue(0);
        rangeSeekBarSchedule.setSelectedMaxValue(24);
        rangeSeekBarSchedule.setOnRangeSeekBarChangeListener(this);
        rangeSeekBarSchedule.setVisibility(View.GONE);


        recyclerViewSchedule = (RecyclerView) findViewById(R.id.recycler_view_schedule);
        recyclerViewSchedule.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerSchedule = new GridLayoutManager(this, 3);
        recyclerViewSchedule.setLayoutManager(gridLayoutManagerSchedule);
        recyclerViewSchedule.setAdapter(new ElementsAdapterSimple(scheduleList,this));
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

        rangeSeekBarPlaces= (RangeSeekBar) findViewById(R.id.rangeseekbarplaces);
        rangeSeekBarPlaces.setLabel(" personnes");
        rangeSeekBarPlaces.setRangeValues(0,10);
        rangeSeekBarPlaces.setSelectedMaxValue(0);
        rangeSeekBarPlaces.setOnRangeSeekBarChangeListener(this);
        rangeSeekBarPlaces.setVisibility(View.GONE);

        recyclerViewWaitingTime = (RecyclerView) findViewById(R.id.recycler_view_waitingtime);
        recyclerViewWaitingTime.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerWaitingTime = new GridLayoutManager(this, 3);
        recyclerViewWaitingTime.setLayoutManager(gridLayoutManagerWaitingTime);
        recyclerViewWaitingTime.setAdapter(new ElementsAdapter(waitingTimeList));
        recyclerViewWaitingTime.setVisibility(View.GONE);

        switchTerrace = (Switch)findViewById(R.id.switchTerrace);
        switchTerrace.setChecked(false);
        switchTerrace.setOnCheckedChangeListener(this);
        switchTerrace.setVisibility(View.GONE);

        switchAirConditionner = (Switch)findViewById(R.id.switchAirConditionner);
        switchAirConditionner.setChecked(false);
        switchAirConditionner.setOnCheckedChangeListener(this);
        switchAirConditionner.setVisibility(View.GONE);
    }

    /**
     *
     */
    public void addListenerOnButton() {
        buttonDistance = (Button) findViewById(R.id.button_distance);
        buttonDistance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(rangeSeekBarDistance.getVisibility() == View.GONE){
                    rangeSeekBarDistance.setVisibility(View.VISIBLE);
                }else{
                    rangeSeekBarDistance.setVisibility(View.GONE);
                }
            }
        });

        buttonSchedule = (Button) findViewById(R.id.button_schedule);
        buttonSchedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(recyclerViewSchedule.getVisibility() == View.GONE ){
                    recyclerViewSchedule.setVisibility(View.VISIBLE);
                }else{
                    recyclerViewSchedule.setVisibility(View.GONE);
                }
                if(rangeSeekBarSchedule.getVisibility() == View.GONE){
                    rangeSeekBarSchedule.setVisibility(View.VISIBLE);
                }else{
                    rangeSeekBarSchedule.setVisibility(View.GONE);
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
                if(rangeSeekBarPlaces.getVisibility() == View.GONE){
                    rangeSeekBarPlaces.setVisibility(View.VISIBLE);
                }else{
                    rangeSeekBarPlaces.setVisibility(View.GONE);
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
                if(switchTerrace.getVisibility() == View.GONE){
                    switchTerrace.setVisibility(View.VISIBLE);
                }else{
                    switchTerrace.setVisibility(View.GONE);
                }
                if(switchAirConditionner.getVisibility() == View.GONE){
                    switchAirConditionner.setVisibility(View.VISIBLE);
                }else{
                    switchAirConditionner.setVisibility(View.GONE);
                }
            }
        });
    }




}
