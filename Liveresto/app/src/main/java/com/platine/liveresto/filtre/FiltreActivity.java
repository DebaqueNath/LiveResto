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
import com.platine.liveresto.filtre.Adapter.ElementsAdapter;
import com.platine.liveresto.filtre.Adapter.ElementsAdapterSimple;
import com.platine.liveresto.model.Filtre;
import com.platine.liveresto.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

public class FiltreActivity extends AppCompatActivity implements RangeSeekBar.OnRangeSeekBarChangeListener<Number>, CompoundButton.OnCheckedChangeListener, ElementsAdapterSimple.Listener, ElementsAdapter.Listener{

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
    private ImageView imageTerrace;
    private ImageView imageAirconditionner;
    private Filtre filterGlobal;
    public static final int FILTRESCODE = 42;
    private Switch switchTerrace;
    private Switch switchAirConditionner;

    private boolean distanceActived;
    private boolean scheduleActived;
    private boolean typeActived;
    private boolean budgetActived;
    private boolean paymentActived;
    private boolean atmosphereActived;
    private boolean placesActived;
    private boolean waitingTimeActived;
    private boolean otherActived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtre);


        //On récupère l'intent et les données
        Intent i = getIntent();
        filterGlobal = new Filtre(i.getDoubleExtra("distanceFilter",0.0),i.getStringArrayListExtra("daysFilter"),i.getDoubleExtra("hourBeginFilter",0.0),i.getDoubleExtra("hourEndFilter",0.0),i.getStringArrayListExtra("typeFilter"),i.getIntExtra("startBudgetFilter",0),i.getIntExtra("endBudgetFilter",0),i.getStringArrayListExtra("paymentFilter"),i.getStringArrayListExtra("atmosphereFilter"),i.getIntExtra("placesFilter",0),i.getIntExtra("waitingTimeFilter",0),i.getBooleanExtra("terraceFilter",false),i.getBooleanExtra("airConditionnerFilter",false));

        distanceActived =  i.getBooleanExtra("distanceActived",false);
        scheduleActived = i.getBooleanExtra("scheduleActived",false);
        typeActived = i.getBooleanExtra("typeActived",false);
        budgetActived = i.getBooleanExtra("budgetActived",false);
        paymentActived = i.getBooleanExtra("paymentActived",false);
        atmosphereActived = i.getBooleanExtra("atmosphereActived",false);
        placesActived = i.getBooleanExtra("placesActived",false);
        waitingTimeActived = i.getBooleanExtra("waitingTimeActived",false);
        otherActived = i.getBooleanExtra("otherActived",false);

        backArrowFilter = (ImageView) findViewById(R.id.back_arrow_filter);
        backArrowFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToMap();
            }
        });


        initToolbar();

        initFilters();

        initElementsView();

        initData();

        addListenerOnButton();

    }

    //Save filters
    @Override
    public void onCardViewClick(Data d) {
        //Days
        if(d.getName()=="Lundi"){
            if(filterGlobal.getDays().contains("LU")) {filterGlobal.removeDay("LU"); } else { filterGlobal.addDay("LU");}
        }
        if(d.getName()=="Mardi"){
            if(filterGlobal.getDays().contains("MA")) {filterGlobal.removeDay("MA"); } else { filterGlobal.addDay("MA");}
        }
        if(d.getName()=="Mercredi"){
            if(filterGlobal.getDays().contains("ME")) {filterGlobal.removeDay("ME"); } else { filterGlobal.addDay("ME");}
        }
        if(d.getName()=="Jeudi"){
            if(filterGlobal.getDays().contains("JE")) {filterGlobal.removeDay("JE"); } else { filterGlobal.addDay("JE");}
        }
        if(d.getName()=="Vendredi"){
            if(filterGlobal.getDays().contains("VE")) {filterGlobal.removeDay("VE"); } else { filterGlobal.addDay("VE");}
        }
        if(d.getName()=="Samedi"){
            if(filterGlobal.getDays().contains("SA")) {filterGlobal.removeDay("SA"); } else { filterGlobal.addDay("SA");}
        }
        if(d.getName()=="Dimanche"){
            if(filterGlobal.getDays().contains("DI")) {filterGlobal.removeDay("DI"); } else { filterGlobal.addDay("DI");}
        }
        if(d.getName()=="TOUSDAY"){
            filterGlobal.removeAllDay();
        }

        //Type
        if(d.getName()=="Pizzeria"){
            if(filterGlobal.getType().contains("pizzeria")) {filterGlobal.removeType("pizzeria"); } else { filterGlobal.addType("pizzeria");}
        }
        if(d.getName()=="Halal"){
            if(filterGlobal.getType().contains("halal")) {filterGlobal.removeType("halal"); } else { filterGlobal.addType("halal");}
        }
        if(d.getName()=="Brasserie"){
            if(filterGlobal.getType().contains("brasserie")) {filterGlobal.removeType("brasserie"); } else { filterGlobal.addType("brasserie");}
        }
        if(d.getName()=="Végétarien"){
            if(filterGlobal.getType().contains("vegetarien")) {filterGlobal.removeType("vegetarien"); } else { filterGlobal.addType("vegetarien");}
        }
        if(d.getName()=="Gastronomique"){
            if(filterGlobal.getType().contains("gastronomique")) {filterGlobal.removeType("gastronomique"); } else { filterGlobal.addType("gastronomique");}
        }
        if(d.getName()=="Bio"){
            if(filterGlobal.getType().contains("bio")) {filterGlobal.removeType("bio"); } else { filterGlobal.addType("bio");}
        }
        if(d.getName()=="Fast-food"){
            if(filterGlobal.getType().contains("fastfood")) {filterGlobal.removeType("fastfood"); } else { filterGlobal.addType("fastfood");}
        }
        if(d.getName()=="Casher"){
            if(filterGlobal.getType().contains("casher")) {filterGlobal.removeType("casher"); } else { filterGlobal.addType("casher");}
        }
        if(d.getName()=="Italien"){
            if(filterGlobal.getType().contains("italien")) {filterGlobal.removeType("italien"); } else { filterGlobal.addType("italien");}
        }
        if(d.getName()=="Chinois"){
            if(filterGlobal.getType().contains("chinois")) {filterGlobal.removeType("chinois"); } else { filterGlobal.addType("chinois");}
        }
        if(d.getName()=="TOUSTYPE"){
            filterGlobal.removeAllType();
        }

        //Budget
        if(d.getName()=="<20"){
            if(filterGlobal.getStartBudget()==0 && filterGlobal.getEndBudget()==19) {filterGlobal.setStartBudget(0);filterGlobal.setEndBudget(0); } else { filterGlobal.setStartBudget(0);filterGlobal.setEndBudget(19);}
        }
        if(d.getName()=="20 à 39"){
            if(filterGlobal.getStartBudget()==20 && filterGlobal.getEndBudget()==39) {filterGlobal.setStartBudget(0);filterGlobal.setEndBudget(0); } else { filterGlobal.setStartBudget(20);filterGlobal.setEndBudget(39);}
        }
        if(d.getName()=="40 à 59"){
            if(filterGlobal.getStartBudget()==40 && filterGlobal.getEndBudget()==59) {filterGlobal.setStartBudget(0);filterGlobal.setEndBudget(0); } else { filterGlobal.setStartBudget(40);filterGlobal.setEndBudget(59);}
        }
        if(d.getName()=="60 à 79"){
            if(filterGlobal.getStartBudget()==60 && filterGlobal.getEndBudget()==79) {filterGlobal.setStartBudget(0);filterGlobal.setEndBudget(0); } else { filterGlobal.setStartBudget(60);filterGlobal.setEndBudget(79);}
        }
        if(d.getName()==">80"){
            if(filterGlobal.getStartBudget()==80 && filterGlobal.getEndBudget()==1000) {filterGlobal.setStartBudget(0);filterGlobal.setEndBudget(0); } else { filterGlobal.setStartBudget(80);filterGlobal.setEndBudget(1000);}
        }
        if(d.getName()=="TOUSBUDGET"){
            filterGlobal.setStartBudget(0);
            filterGlobal.setEndBudget(0);
        }

        //Payment
        if(d.getName()=="Carte bancaire"){
            if(filterGlobal.getPayment().contains("cartebancaire")) {filterGlobal.removePayment("cartebancaire"); } else { filterGlobal.addPayment("cartebancaire");}
        }
        if(d.getName()=="Cheque"){
            if(filterGlobal.getPayment().contains("cheque")) {filterGlobal.removePayment("cheque"); } else { filterGlobal.addPayment("cheque");}
        }
        if(d.getName()=="Cheque vacances"){
            if(filterGlobal.getPayment().contains("chequevac")) {filterGlobal.removePayment("chequevac"); } else { filterGlobal.addPayment("chequevac");}
        }
        if(d.getName()=="Espece"){
            if(filterGlobal.getPayment().contains("espece")) {filterGlobal.removePayment("espece"); } else { filterGlobal.addPayment("espece");}
        }
        if(d.getName()=="Ticket restaurant"){
            if(filterGlobal.getPayment().contains("ticketrestaurant")) {filterGlobal.removePayment("ticketrestaurant"); } else { filterGlobal.addPayment("ticketrestaurant");}
        }
        if(d.getName()=="TOUSPAYMENT"){
            filterGlobal.removeAllPayment();
        }

        //Atmosphere
        if(d.getName()=="Retro"){
            if(filterGlobal.getAtmosphere().contains("retro")) {filterGlobal.removeAtmosphere("retro"); } else { filterGlobal.addAtmosphere("retro");}
        }
        if(d.getName()=="Musical"){
            if(filterGlobal.getAtmosphere().contains("musical")) {filterGlobal.removeAtmosphere("musical"); } else { filterGlobal.addAtmosphere("musical");}
        }
        if(d.getName()=="Jeune"){
            if(filterGlobal.getAtmosphere().contains("jeune")) {filterGlobal.removeAtmosphere("jeune"); } else { filterGlobal.addAtmosphere("jeune");}
        }
        if(d.getName()=="Chic"){
            if(filterGlobal.getAtmosphere().contains("chic")) {filterGlobal.removeAtmosphere("chic"); } else { filterGlobal.addAtmosphere("chic");}
        }
        if(d.getName()=="Romantique"){
            if(filterGlobal.getAtmosphere().contains("romantique")) {filterGlobal.removeAtmosphere("romantique"); } else { filterGlobal.addAtmosphere("romantique");}
        }
        if(d.getName()=="Historique"){
            if(filterGlobal.getAtmosphere().contains("historique")) {filterGlobal.removeAtmosphere("historique"); } else { filterGlobal.addAtmosphere("historique");}
        }
        if(d.getName()=="Spectacle"){
            if(filterGlobal.getAtmosphere().contains("spectacle")) {filterGlobal.removeAtmosphere("spectacle"); } else { filterGlobal.addAtmosphere("spectacle");}
        }
        if(d.getName()=="TOUSATMOSPHERE"){
            filterGlobal.removeAllAtmosphere();
        }

        //WaitingTime
        if(d.getName()=="<5min"){
            if(filterGlobal.getWaitingTime()==5) {filterGlobal.setWaitingTime(0); } else { filterGlobal.setWaitingTime(5);}
        }
        if(d.getName()=="<10min"){
            if(filterGlobal.getWaitingTime()==10) {filterGlobal.setWaitingTime(0); } else { filterGlobal.setWaitingTime(10);}
        }
        if(d.getName()=="<15min"){
            if(filterGlobal.getWaitingTime()==15) {filterGlobal.setWaitingTime(0); } else { filterGlobal.setWaitingTime(15);}
        }
        if(d.getName()=="<30min"){
            if(filterGlobal.getWaitingTime()==30) {filterGlobal.setWaitingTime(0); } else { filterGlobal.setWaitingTime(30);}
        }
        if(d.getName()=="<45min"){
            if(filterGlobal.getWaitingTime()==45) {filterGlobal.setWaitingTime(0); } else { filterGlobal.setWaitingTime(45);}
        }
        if(d.getName()=="<60min"){
            if(filterGlobal.getWaitingTime()==60) {filterGlobal.setWaitingTime(0); } else { filterGlobal.setWaitingTime(60);}
        }
        if(d.getName()=="TOUSWAITING"){
            filterGlobal.setWaitingTime(0);
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
        i.putExtra("distanceActived",distanceActived);
        i.putExtra("scheduleActived",scheduleActived);
        i.putExtra("typeActived",typeActived);
        i.putExtra("budgetActived",budgetActived);
        i.putExtra("paymentActived",paymentActived);
        i.putExtra("atmosphereActived",atmosphereActived);
        i.putExtra("placesActived",placesActived);
        i.putExtra("waitingTimeActived",waitingTimeActived);
        i.putExtra("otherActived",otherActived);
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
        if(buttonView.getText().equals("Terrasse")){
            if(isChecked){
                filterGlobal.setTerrace(true);
            } else {
                filterGlobal.setTerrace(false);
            }
        } else if(buttonView.getText().equals("Climatisation")){
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
        scheduleList.add(new Data("TOUSDAY",R.drawable.icon_all,filterGlobal.getDays().isEmpty()));

        typeList = new ArrayList<>();
        typeList.add(new Data("Pizzeria", R.drawable.pizzeria,filterGlobal.getType().contains("pizzeria")));
        typeList.add(new Data("Halal", R.drawable.halal,filterGlobal.getType().contains("halal")));
        typeList.add(new Data("Brasserie", R.drawable.brasserie,filterGlobal.getType().contains("brasserie")));
        typeList.add(new Data("Végétarien", R.drawable.vegetarien,filterGlobal.getType().contains("vegetarien")));
        typeList.add(new Data("Etoilé", R.drawable.gastronomique,filterGlobal.getType().contains("gastronomique")));
        typeList.add(new Data("Bio", R.drawable.bio,filterGlobal.getType().contains("bio")));
        typeList.add(new Data("Fast-food", R.drawable.fastfood,filterGlobal.getType().contains("fastfood")));
        typeList.add(new Data("Casher", R.drawable.casher,filterGlobal.getType().contains("casher")));
        typeList.add(new Data("Italien", R.drawable.italien,filterGlobal.getType().contains("italien")));
        typeList.add(new Data("Chinois", R.drawable.chinois,filterGlobal.getType().contains("chinois")));
        typeList.add(new Data("TOUSTYPE",R.drawable.icon_all,filterGlobal.getType().isEmpty()));

        budgetList = new ArrayList<>();
        budgetList.add(new Data("<20€",R.drawable.icon_inf_20,((filterGlobal.getStartBudget()==0) && (filterGlobal.getEndBudget()==19)) ? true : false));
        budgetList.add(new Data("20 à 39€",R.drawable.icon_from_20,((filterGlobal.getStartBudget()==20) && (filterGlobal.getEndBudget()==39)) ? true : false));
        budgetList.add(new Data("40 à 59€",R.drawable.icon_from_40,((filterGlobal.getStartBudget()==40) && (filterGlobal.getEndBudget()==59)) ? true : false));
        budgetList.add(new Data("60 à 79€",R.drawable.icon_from_60,((filterGlobal.getStartBudget()==60) && (filterGlobal.getEndBudget()==79)) ? true : false));
        budgetList.add(new Data(">80€",R.drawable.icon_sup_80,((filterGlobal.getStartBudget()==80) && (filterGlobal.getEndBudget()==1000)) ? true : false));
        budgetList.add(new Data("TOUSBUDGET",R.drawable.icon_all,((filterGlobal.getStartBudget()==0) && (filterGlobal.getEndBudget()==0)) ? true : false));

        paymentList = new ArrayList<>();
        paymentList.add(new Data("Carte bancaire",R.drawable.icon_cb,filterGlobal.getPayment().contains("cartebancaire")));
        paymentList.add(new Data("Cheque",R.drawable.icon_cheque,filterGlobal.getPayment().contains("cheque")));
        paymentList.add(new Data("Cheque vacances",R.drawable.icon_cheque_vac,filterGlobal.getPayment().contains("chequevac")));
        paymentList.add(new Data("Espece",R.drawable.icon_espece,filterGlobal.getPayment().contains("espece")));
        paymentList.add(new Data("Ticket restaurant",R.drawable.icon_ticket,filterGlobal.getPayment().contains("ticketrestaurant")));
        paymentList.add(new Data("TOUSPAYMENT",R.drawable.icon_all,filterGlobal.getPayment().isEmpty()));

        atmosphereList = new ArrayList<>();
        atmosphereList.add(new Data("Retro",R.drawable.icon_retro,filterGlobal.getAtmosphere().contains("retro")));
        atmosphereList.add(new Data("Musical",R.drawable.icon_music,filterGlobal.getAtmosphere().contains("musical")));
        atmosphereList.add(new Data("Jeune",R.drawable.icon_jeune,filterGlobal.getAtmosphere().contains("jeune")));
        atmosphereList.add(new Data("Chic",R.drawable.icon_chic,filterGlobal.getAtmosphere().contains("chic")));
        atmosphereList.add(new Data("Romance",R.drawable.icon_romantique,filterGlobal.getAtmosphere().contains("romantique")));
        atmosphereList.add(new Data("Historique",R.drawable.icon_historique,filterGlobal.getAtmosphere().contains("historique")));
        atmosphereList.add(new Data("Spectacle",R.drawable.icon_spectacle,filterGlobal.getAtmosphere().contains("spectacle")));
        atmosphereList.add(new Data("TOUSATMOSPHERE",R.drawable.icon_all,filterGlobal.getAtmosphere().isEmpty()));

        waitingTimeList = new ArrayList<>();
        waitingTimeList.add(new Data("<5min",R.drawable.icon_wait_inf_5,(filterGlobal.getWaitingTime()==5)? true : false));
        waitingTimeList.add(new Data("<10min",R.drawable.icon_wait_inf_10,(filterGlobal.getWaitingTime()==10)? true : false));
        waitingTimeList.add(new Data("<15min",R.drawable.icon_wait_inf_15,(filterGlobal.getWaitingTime()==15)? true : false));
        waitingTimeList.add(new Data("<30min",R.drawable.icon_wait_inf_30,(filterGlobal.getWaitingTime()==30)? true : false));
        waitingTimeList.add(new Data("<45min",R.drawable.icon_wait_inf_45,(filterGlobal.getWaitingTime()==45)? true : false));
        waitingTimeList.add(new Data("<60min",R.drawable.icon_wait_inf_60,(filterGlobal.getWaitingTime()==60)? true : false));
        waitingTimeList.add(new Data("TOUSWAITING",R.drawable.icon_all,(filterGlobal.getWaitingTime()==0)? true : false));

    }


    /**
     *
     */
    public void initElementsView(){
        //Initialize RangeSeekBar
        rangeSeekBarDistance = (RangeSeekBar) findViewById(R.id.rangeseekbardistance);
        rangeSeekBarDistance.setLabel("km");
        rangeSeekBarDistance.setRangeValues(0,50);
        rangeSeekBarDistance.setSelectedMaxValue(0);
        rangeSeekBarDistance.setOnRangeSeekBarChangeListener(this);
        rangeSeekBarDistance.setVisibility(distanceActived?View.VISIBLE:View.GONE);

        rangeSeekBarSchedule = (RangeSeekBar) findViewById(R.id.rangeseekbarschedule);
        rangeSeekBarSchedule.setLabel("h");
        rangeSeekBarSchedule.setRangeValues(0,24);
        rangeSeekBarSchedule.setSelectedMinValue(0);
        rangeSeekBarSchedule.setSelectedMaxValue(24);
        rangeSeekBarSchedule.setOnRangeSeekBarChangeListener(this);
        rangeSeekBarSchedule.setVisibility(scheduleActived?View.VISIBLE:View.GONE);

        rangeSeekBarPlaces= (RangeSeekBar) findViewById(R.id.rangeseekbarplaces);
        rangeSeekBarPlaces.setLabel(" personnes");
        rangeSeekBarPlaces.setRangeValues(0,10);
        rangeSeekBarPlaces.setSelectedMaxValue(0);
        rangeSeekBarPlaces.setOnRangeSeekBarChangeListener(this);
        rangeSeekBarPlaces.setVisibility(placesActived?View.VISIBLE:View.GONE);

        //Initialize Recycler
        recyclerViewSchedule = (RecyclerView) findViewById(R.id.recycler_view_schedule);
        recyclerViewSchedule.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerSchedule = new GridLayoutManager(this, 3);
        recyclerViewSchedule.setLayoutManager(gridLayoutManagerSchedule);
        recyclerViewSchedule.setAdapter(new ElementsAdapter(scheduleList,this));
        recyclerViewSchedule.setVisibility(scheduleActived?View.VISIBLE:View.GONE);

        recyclerViewType = (RecyclerView) findViewById(R.id.recycler_view_type);
        recyclerViewType.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerType = new GridLayoutManager(this, 3);
        recyclerViewType.setLayoutManager(gridLayoutManagerType);
        recyclerViewType.setAdapter(new ElementsAdapter(typeList,this));
        recyclerViewType.setVisibility(typeActived?View.VISIBLE:View.GONE);

        recyclerViewBudget = (RecyclerView) findViewById(R.id.recycler_view_budget);
        recyclerViewBudget.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerBudget = new GridLayoutManager(this, 3);
        recyclerViewBudget.setLayoutManager(gridLayoutManagerBudget);
        recyclerViewBudget.setAdapter(new ElementsAdapterSimple(budgetList,this));
        recyclerViewBudget.setVisibility(budgetActived?View.VISIBLE:View.GONE);

        recyclerViewPayment = (RecyclerView) findViewById(R.id.recycler_view_payment);
        recyclerViewPayment.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerPayment = new GridLayoutManager(this, 3);
        recyclerViewPayment.setLayoutManager(gridLayoutManagerPayment);
        recyclerViewPayment.setAdapter(new ElementsAdapter(paymentList,this));
        recyclerViewPayment.setVisibility(paymentActived?View.VISIBLE:View.GONE);

        recyclerViewAtmosphere = (RecyclerView) findViewById(R.id.recycler_view_atmosphere);
        recyclerViewAtmosphere.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerAtmosphere = new GridLayoutManager(this, 3);
        recyclerViewAtmosphere.setLayoutManager(gridLayoutManagerAtmosphere);
        recyclerViewAtmosphere.setAdapter(new ElementsAdapter(atmosphereList,this));
        recyclerViewAtmosphere.setVisibility(atmosphereActived?View.VISIBLE:View.GONE);


        recyclerViewWaitingTime = (RecyclerView) findViewById(R.id.recycler_view_waitingtime);
        recyclerViewWaitingTime.setHasFixedSize(true);
        GridLayoutManager gridLayoutManagerWaitingTime = new GridLayoutManager(this, 3);
        recyclerViewWaitingTime.setLayoutManager(gridLayoutManagerWaitingTime);
        recyclerViewWaitingTime.setAdapter(new ElementsAdapterSimple(waitingTimeList,this));
        recyclerViewWaitingTime.setVisibility(waitingTimeActived?View.VISIBLE:View.GONE);

        //Initialize Switch
        imageTerrace = (ImageView) findViewById(R.id.iconTerrace);
        imageTerrace.setVisibility(otherActived?View.VISIBLE:View.GONE);

        imageAirconditionner = (ImageView) findViewById(R.id.iconAirConditionner);
        imageAirconditionner.setVisibility(otherActived?View.VISIBLE:View.GONE);

        switchTerrace = (Switch)findViewById(R.id.switchTerrace);
        switchTerrace.setChecked(false);
        switchTerrace.setOnCheckedChangeListener(this);
        switchTerrace.setVisibility(otherActived?View.VISIBLE:View.GONE);

        switchAirConditionner = (Switch)findViewById(R.id.switchAirConditionner);
        switchAirConditionner.setChecked(false);
        switchAirConditionner.setOnCheckedChangeListener(this);
        switchAirConditionner.setVisibility(otherActived?View.VISIBLE:View.GONE);
    }

    public void initData(){
        //Distance
        rangeSeekBarDistance.setSelectedMaxValue(filterGlobal.getDistanceMax());

        //Schedule Hours
        rangeSeekBarSchedule.setSelectedMinValue(filterGlobal.getHourBegin());
        rangeSeekBarSchedule.setSelectedMaxValue(filterGlobal.getHourEnd());

        //Number places
        rangeSeekBarPlaces.setSelectedMaxValue(filterGlobal.getPlaces());

        //Terrace
        switchTerrace.setChecked(filterGlobal.isTerrace());

        //AirConditionner
        switchAirConditionner.setChecked(filterGlobal.isAirConditionner());
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
                    distanceActived = true;
                }else{
                    rangeSeekBarDistance.setVisibility(View.GONE);
                    distanceActived = false;
                }
            }
        });

        buttonSchedule = (Button) findViewById(R.id.button_schedule);
        buttonSchedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(recyclerViewSchedule.getVisibility() == View.GONE ){
                    recyclerViewSchedule.setVisibility(View.VISIBLE);
                    scheduleActived = true;
                }else{
                    recyclerViewSchedule.setVisibility(View.GONE);
                    scheduleActived = false;
                }
                if(rangeSeekBarSchedule.getVisibility() == View.GONE){
                    rangeSeekBarSchedule.setVisibility(View.VISIBLE);
                    scheduleActived = true;
                }else{
                    rangeSeekBarSchedule.setVisibility(View.GONE);
                    scheduleActived = false;
                }
            }
        });

        buttonType = (Button) findViewById(R.id.button_type);
        buttonType.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(recyclerViewType.getVisibility() == View.GONE){
                    recyclerViewType.setVisibility(View.VISIBLE);
                    typeActived = true;
                }else{
                    recyclerViewType.setVisibility(View.GONE);
                    typeActived = false;
                }
            }
        });

        buttonBudget = (Button) findViewById(R.id.button_budget);
        buttonBudget.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(recyclerViewBudget.getVisibility() == View.GONE){
                    recyclerViewBudget.setVisibility(View.VISIBLE);
                    budgetActived = true;
                }else{
                    recyclerViewBudget.setVisibility(View.GONE);
                    budgetActived = false;
                }
            }
        });

        buttonPayment = (Button) findViewById(R.id.button_payment);
        buttonPayment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(recyclerViewPayment.getVisibility() == View.GONE){
                    recyclerViewPayment.setVisibility(View.VISIBLE);
                    paymentActived = true;
                }else{
                    recyclerViewPayment.setVisibility(View.GONE);
                    paymentActived = false;
                }
            }
        });

        buttonAtmosphere = (Button) findViewById(R.id.button_atmosphere);
        buttonAtmosphere.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(recyclerViewAtmosphere.getVisibility() == View.GONE){
                    recyclerViewAtmosphere.setVisibility(View.VISIBLE);
                    atmosphereActived = true;
                }else{
                    recyclerViewAtmosphere.setVisibility(View.GONE);
                    atmosphereActived = false;
                }
            }
        });

        buttonNumber = (Button) findViewById(R.id.button_number);
        buttonNumber.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(rangeSeekBarPlaces.getVisibility() == View.GONE){
                    rangeSeekBarPlaces.setVisibility(View.VISIBLE);
                    placesActived = true;
                }else{
                    rangeSeekBarPlaces.setVisibility(View.GONE);
                    placesActived = false;
                }
            }
        });

        buttonWaitingTime = (Button) findViewById(R.id.button_waitingtime);
        buttonWaitingTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(recyclerViewWaitingTime.getVisibility() == View.GONE){
                    recyclerViewWaitingTime.setVisibility(View.VISIBLE);
                    waitingTimeActived = true;
                }else{
                    recyclerViewWaitingTime.setVisibility(View.GONE);
                    waitingTimeActived = false;
                }
            }
        });

        buttonOther = (Button) findViewById(R.id.button_other);
        buttonOther.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(switchTerrace.getVisibility() == View.GONE){
                    switchTerrace.setVisibility(View.VISIBLE);
                    imageTerrace.setVisibility(View.VISIBLE);
                    otherActived = true;
                }else{
                    switchTerrace.setVisibility(View.GONE);
                    imageTerrace.setVisibility(View.GONE);
                    otherActived = false;
                }
                if(switchAirConditionner.getVisibility() == View.GONE){
                    switchAirConditionner.setVisibility(View.VISIBLE);
                    imageAirconditionner.setVisibility(View.VISIBLE);
                    otherActived = true;
                }else{
                    switchAirConditionner.setVisibility(View.GONE);
                    imageAirconditionner.setVisibility(View.GONE);
                    otherActived = false;
                }
            }
        });
    }




}
