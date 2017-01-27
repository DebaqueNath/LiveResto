package com.platine.liveresto.filtre;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.platine.liveresto.R;
import com.platine.liveresto.model.Data;
import com.platine.liveresto.model.Filtre;
import com.platine.liveresto.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

public class FiltreActivity extends AppCompatActivity implements RangeSeekBar.OnRangeSeekBarChangeListener<Number>, CompoundButton.OnCheckedChangeListener{

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

        addListenerOnButton();

        //On appelle méthode qui met à jour les filtres en fonction des filtres reçus
        /* ------------- A FAIRE -------------------------------------------- */
        updateFilter();
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


    private void updateFilter(){
        //Distance
        rangeSeekBarDistance.setSelectedMaxValue(filterGlobal.getDistanceMax());

        //Schedule
        if(filterGlobal.getDays().contains("LU")){
            recyclerViewSchedule.findViewHolderForAdapterPosition(0).itemView.setBackgroundResource(R.drawable.card_border);
        } else if(filterGlobal.getDays().contains("MA")) {
            recyclerViewSchedule.findViewHolderForAdapterPosition(1).itemView.setBackgroundResource(R.drawable.card_border);
        } else if(filterGlobal.getDays().contains("ME")) {
            recyclerViewSchedule.findViewHolderForAdapterPosition(2).itemView.setBackgroundResource(R.drawable.card_border);
        } else if(filterGlobal.getDays().contains("JE")) {
            recyclerViewSchedule.findViewHolderForAdapterPosition(3).itemView.setBackgroundResource(R.drawable.card_border);
        } else if(filterGlobal.getDays().contains("VE")) {
            recyclerViewSchedule.findViewHolderForAdapterPosition(4).itemView.setBackgroundResource(R.drawable.card_border);
        } else if(filterGlobal.getDays().contains("SA")) {
            recyclerViewSchedule.findViewHolderForAdapterPosition(5).itemView.setBackgroundResource(R.drawable.card_border);
        } else if(filterGlobal.getDays().contains("DI")) {
            recyclerViewSchedule.findViewHolderForAdapterPosition(6).itemView.setBackgroundResource(R.drawable.card_border);
        }


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
        scheduleList = new ArrayList<>();
        scheduleList.add(new Data("Lundi",R.drawable.icon_monday));
        scheduleList.add(new Data("Mardi",R.drawable.icon_tuesday));
        scheduleList.add(new Data("Mercredi", R.drawable.icon_wednesday));
        scheduleList.add(new Data("Jeudi",R.drawable.icon_thursday));
        scheduleList.add(new Data("Vendredi",R.drawable.icon_friday));
        scheduleList.add(new Data("Samedi",R.drawable.icon_saturday));
        scheduleList.add(new Data("Dimanche",R.drawable.icon_sunday));
        scheduleList.add(new Data("TOUS"));

        typeList = new ArrayList<>();
        typeList.add(new Data("Pizzeria", R.drawable.pizzeria));
        typeList.add(new Data("Halal", R.drawable.halal));
        typeList.add(new Data("Brasserie", R.drawable.brasserie));
        typeList.add(new Data("Végétarien", R.drawable.vegetarien));
        typeList.add(new Data("Gastronomique", R.drawable.gastronomique));
        typeList.add(new Data("Bio", R.drawable.bio));
        typeList.add(new Data("Fast-food", R.drawable.fastfood));
        typeList.add(new Data("Casher", R.drawable.casher));
        typeList.add(new Data("Italien", R.drawable.italien));
        typeList.add(new Data("Chinois", R.drawable.chinois));
        typeList.add(new Data("TOUS"));

        budgetList = new ArrayList<>();
        budgetList.add(new Data("<20",R.drawable.icon_inf_20));
        budgetList.add(new Data("20 à 39",R.drawable.icon_from_20_to_39));
        budgetList.add(new Data("40 à 59",R.drawable.icon_from_40_to_59));
        budgetList.add(new Data("60 à 79",R.drawable.icon_from_60_to_79));
        budgetList.add(new Data(">80",R.drawable.icon_sup_80));
        budgetList.add(new Data("TOUS"));

        paymentList = new ArrayList<>();
        paymentList.add(new Data("Carte bancaire",R.drawable.icon_cb));
        paymentList.add(new Data("Cheque",R.drawable.icon_cheque));
        paymentList.add(new Data("Cheque vacances",R.drawable.icon_vac));
        paymentList.add(new Data("Espece",R.drawable.icon_money));
        paymentList.add(new Data("Ticket restaurant",R.drawable.icon_ticket));
        paymentList.add(new Data("TOUS"));

        atmosphereList = new ArrayList<>();
        atmosphereList.add(new Data("Retro"));
        atmosphereList.add(new Data("Musical"));
        atmosphereList.add(new Data("Jeune"));
        atmosphereList.add(new Data("Chic"));
        atmosphereList.add(new Data("Romantique"));
        atmosphereList.add(new Data("Historique"));
        atmosphereList.add(new Data("Spectacle"));
        atmosphereList.add(new Data("TOUS"));

        waitingTimeList = new ArrayList<>();
        waitingTimeList.add(new Data("<5min"));
        waitingTimeList.add(new Data("<10min"));
        waitingTimeList.add(new Data("<15min"));
        waitingTimeList.add(new Data("<30min"));
        waitingTimeList.add(new Data("<45min"));
        waitingTimeList.add(new Data("<60min"));
        waitingTimeList.add(new Data("TOUS"));

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
            //Gestion de choix de l'utilisateur
            public void onClick(View view) {

                RecyclerView r = (RecyclerView)view.getParent();
                View v = r.findViewHolderForAdapterPosition(0).itemView;
                TextView name =  (TextView) v.findViewById(R.id.title);
                String category = (String)name.getText();

                if(category.equals("Lundi")){
                    if(!this.isSelected){
                        view.setBackgroundResource(R.drawable.card_border);
                        this.isSelected = true;
                        Drawable viewColor = view.getBackground();
                        if(viewColor instanceof GradientDrawable) {
                            view.setBackgroundColor(Color.WHITE);
                            this.isSelected = false;
                        } else {
                            if (vName.getText() == "Lundi") {
                                filterGlobal.addDay("LU");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if (vName.getText() == "Mardi") {
                                filterGlobal.addDay("MA");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if (vName.getText() == "Mercredi") {
                                filterGlobal.addDay("ME");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if (vName.getText() == "Jeudi") {
                                filterGlobal.addDay("JE");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if (vName.getText() == "Vendredi") {
                                filterGlobal.addDay("VE");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if (vName.getText() == "Samedi") {
                                filterGlobal.addDay("SA");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if (vName.getText() == "Dimanche") {
                                filterGlobal.addDay("DI");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if (vName.getText() == "TOUS") {
                                filterGlobal.removeAllDay();
                                r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(6).itemView.setBackgroundColor(Color.WHITE);
                            }
                        }
                    }else{
                        Drawable viewColor = view.getBackground();
                        if(viewColor instanceof ColorDrawable) {
                            if(vName.getText()=="Lundi"){
                                filterGlobal.addDay("LU");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Mardi"){
                                filterGlobal.addDay("MA");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Mercredi"){
                                filterGlobal.addDay("ME");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Jeudi"){
                                filterGlobal.addDay("JE");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Vendredi"){
                                filterGlobal.addDay("VE");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Samedi"){
                                filterGlobal.addDay("SA");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Dimanche"){
                                filterGlobal.addDay("DI");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="TOUS"){
                                filterGlobal.removeAllDay();
                                r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(6).itemView.setBackgroundColor(Color.WHITE);
                            }
                            this.isSelected = true;
                            view.setBackgroundResource(R.drawable.card_border);
                        } else {
                            view.setBackgroundColor(Color.WHITE);
                            this.isSelected = false;
                            if (vName.getText() == "Lundi") {
                                filterGlobal.removeDay("LU");
                            } else if (vName.getText() == "Mardi") {
                                filterGlobal.removeDay("MA");
                            } else if (vName.getText() == "Mercredi") {
                                filterGlobal.removeDay("ME");
                            } else if (vName.getText() == "Jeudi") {
                                filterGlobal.removeDay("JE");
                            } else if (vName.getText() == "Vendredi") {
                                filterGlobal.removeDay("VE");
                            } else if (vName.getText() == "Samedi") {
                                filterGlobal.removeDay("SA");
                            } else if (vName.getText() == "Dimanche") {
                                filterGlobal.removeDay("DI");
                            }
                        }
                    }
                } else if(category.equals("Pizzeria")){
                    if(!this.isSelected){
                        view.setBackgroundResource(R.drawable.card_border);
                        this.isSelected = true;
                        if(vName.getText()=="Pizzeria"){
                            filterGlobal.addType("pizzeria");
                            r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="Halal"){
                            filterGlobal.addType("halal");
                            r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="Brasserie"){
                            filterGlobal.addType("brasserie");
                            r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="Végétarien"){
                            filterGlobal.addType("vegetarien");
                            r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="Gastronomique"){
                            filterGlobal.addType("gastronomique");
                            r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="Bio"){
                            filterGlobal.addType("bio");
                            r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="Fast-food"){
                            filterGlobal.addType("fast-food");
                            r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="Casher"){
                            filterGlobal.addType("casher");
                            r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="Italien"){
                            filterGlobal.addType("italien");
                            r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="Chinois"){
                            filterGlobal.addType("chinois");
                            r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="TOUS"){
                            filterGlobal.removeAllType();
                            r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(6).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(8).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(9).itemView.setBackgroundColor(Color.WHITE);
                        }
                    }else{
                        Drawable viewColor = view.getBackground();
                        if(viewColor instanceof ColorDrawable) {
                            view.setBackgroundResource(R.drawable.card_border);
                            this.isSelected = true;
                            if(vName.getText()=="Pizzeria"){
                                filterGlobal.addType("pizzeria");
                                r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Halal"){
                                filterGlobal.addType("halal");
                                r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Brasserie"){
                                filterGlobal.addType("brasserie");
                                r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Végétarien"){
                                filterGlobal.addType("vegetarien");
                                r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Gastronomique"){
                                filterGlobal.addType("gastronomique");
                                r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Bio"){
                                filterGlobal.addType("bio");
                                r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Fast-food"){
                                filterGlobal.addType("fast-food");
                                r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Casher"){
                                filterGlobal.addType("casher");
                                r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Italien"){
                                filterGlobal.addType("italien");
                                r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Chinois"){
                                filterGlobal.addType("chinois");
                                r.findViewHolderForAdapterPosition(10).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="TOUS"){
                                filterGlobal.removeAllType();
                                r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(6).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(8).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(9).itemView.setBackgroundColor(Color.WHITE);
                            }
                        } else {
                            view.setBackgroundColor(Color.WHITE);
                            this.isSelected = false;
                            if (vName.getText() == "Pizzeria") {
                                filterGlobal.removeType("pizzeria");
                            } else if (vName.getText() == "Halal") {
                                filterGlobal.removeType("halal");
                            } else if (vName.getText() == "Brasserie") {
                                filterGlobal.removeType("brasserie");
                            } else if (vName.getText() == "Végétarien") {
                                filterGlobal.removeType("vegetarien");
                            } else if (vName.getText() == "Gastronomique") {
                                filterGlobal.removeType("gastronomique");
                            } else if (vName.getText() == "Bio") {
                                filterGlobal.removeType("bio");
                            } else if (vName.getText() == "Fast-food") {
                                filterGlobal.removeType("fast-food");
                            } else if (vName.getText() == "Casher") {
                                filterGlobal.removeType("casher");
                            } else if (vName.getText() == "Italien") {
                                filterGlobal.removeType("italien");
                            } else if (vName.getText() == "Chinois") {
                                filterGlobal.removeType("chinois");
                            }
                        }
                    }
                } else if(category.equals("<20")){
                    //Choix unique
                    if(!this.isSelected) {
                        view.setBackgroundResource(R.drawable.card_border);
                        this.isSelected = true;
                        if(vName.getText()=="<20"){
                            r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                            filterGlobal.setStartBudget(0);
                            filterGlobal.setEndBudget(19);
                        } else if(vName.getText()=="20 à 39"){
                            r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                            filterGlobal.setStartBudget(20);
                            filterGlobal.setEndBudget(39);
                        } else if(vName.getText()=="40 à 59"){
                            r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                            filterGlobal.setStartBudget(40);
                            filterGlobal.setEndBudget(59);
                        } else if(vName.getText()=="60 à 79"){
                            r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                            filterGlobal.setStartBudget(60);
                            filterGlobal.setEndBudget(79);
                        } else if(vName.getText()==">80"){
                            r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                            filterGlobal.setStartBudget(80);
                            filterGlobal.setEndBudget(1000);
                        } else if(vName.getText()=="TOUS") {
                            r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                            filterGlobal.setStartBudget(0);
                            filterGlobal.setEndBudget(0);
                        }
                    } else {
                        Drawable viewColor = view.getBackground();
                        if(viewColor instanceof ColorDrawable) {
                            if(vName.getText()=="<20"){
                                r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                                filterGlobal.setStartBudget(0);
                                filterGlobal.setEndBudget(19);
                            } else if(vName.getText()=="20 à 39"){
                                r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                                filterGlobal.setStartBudget(20);
                                filterGlobal.setEndBudget(39);
                            } else if(vName.getText()=="40 à 59"){
                                r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                                filterGlobal.setStartBudget(40);
                                filterGlobal.setEndBudget(59);
                            } else if(vName.getText()=="60 à 79"){
                                r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                                filterGlobal.setStartBudget(60);
                                filterGlobal.setEndBudget(79);
                            } else if(vName.getText()==">80"){
                                r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                                filterGlobal.setStartBudget(80);
                                filterGlobal.setEndBudget(1000);
                            } else if(vName.getText()=="TOUS") {
                                r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                                filterGlobal.setStartBudget(0);
                                filterGlobal.setEndBudget(0);
                            }
                            this.isSelected = true;
                            view.setBackgroundResource(R.drawable.card_border);
                        } else {
                            view.setBackgroundColor(Color.WHITE);
                            this.isSelected = false;
                            filterGlobal.setStartBudget(0);
                            filterGlobal.setEndBudget(0);
                        }

                    }
                } else if(category.equals("Carte bancaire")){
                    if(!this.isSelected){
                        view.setBackgroundResource(R.drawable.card_border);
                        this.isSelected = true;
                        if(vName.getText()=="Carte bancaire"){
                            filterGlobal.addPayment("cartebancaire");
                            r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="Cheque"){
                            filterGlobal.addPayment("cheque");
                            r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="Cheque vacances"){
                            filterGlobal.addPayment("chequevacance");
                            r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="Espece"){
                            filterGlobal.addPayment("espece");
                            r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="Ticket restaurant"){
                            filterGlobal.addPayment("ticketrestaurant");
                            r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                        }  else if(vName.getText()=="TOUS") {
                            r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                            filterGlobal.removeAllPayment();
                        }
                    }else{
                        Drawable viewColor = view.getBackground();
                        if(viewColor instanceof ColorDrawable) {
                            view.setBackgroundResource(R.drawable.card_border);
                            this.isSelected = true;
                            if(vName.getText()=="Carte bancaire"){
                                filterGlobal.addPayment("cartebancaire");
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Cheque"){
                                filterGlobal.addPayment("cheque");
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Cheque vacances"){
                                filterGlobal.addPayment("chequevacance");
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Espece"){
                                filterGlobal.addPayment("espece");
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Ticket restaurant"){
                                filterGlobal.addPayment("ticketrestaurant");
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                            }  else if(vName.getText()=="TOUS") {
                                r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                                filterGlobal.removeAllPayment();
                            }

                        } else {
                            view.setBackgroundColor(Color.WHITE);
                            this.isSelected = false;
                            if (vName.getText() == "Carte bancaire") {
                                filterGlobal.removePayment("cartebancaire");
                            } else if (vName.getText() == "Cheque") {
                                filterGlobal.removePayment("cheque");
                            } else if (vName.getText() == "Cheque vacances") {
                                filterGlobal.removePayment("chequevacance");
                            } else if (vName.getText() == "Espece") {
                                filterGlobal.removePayment("espece");
                            } else if (vName.getText() == "Ticket restaurant") {
                                filterGlobal.removePayment("ticketrestaurant");
                            }
                        }
                    }
                } else if(category.equals("Retro")){
                    if(!this.isSelected){
                        view.setBackgroundResource(R.drawable.card_border);
                        this.isSelected = true;
                        if(vName.getText()=="Retro"){
                            filterGlobal.addAtmosphere("retro");
                            r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="Musical"){
                            filterGlobal.addAtmosphere("musical");
                            r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="Jeune"){
                            filterGlobal.addAtmosphere("jeune");
                            r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="Chic"){
                            filterGlobal.addAtmosphere("chic");
                            r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="Romantique"){
                            filterGlobal.addAtmosphere("romantique");
                            r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="Historique"){
                            filterGlobal.addAtmosphere("historique");
                            r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                        } else if(vName.getText()=="Spectacle"){
                            filterGlobal.addAtmosphere("spectacle");
                            r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                        }  else if(vName.getText()=="TOUS") {
                            r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(6).itemView.setBackgroundColor(Color.WHITE);
                            filterGlobal.removeAllAtmosphere();
                        }
                    }else{
                        Drawable viewColor = view.getBackground();
                        if(viewColor instanceof ColorDrawable) {
                            if(vName.getText()=="Retro"){
                                view.setBackgroundResource(R.drawable.card_border);
                                this.isSelected = true;
                                filterGlobal.addAtmosphere("retro");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Musical"){
                                filterGlobal.addAtmosphere("musical");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Jeune"){
                                filterGlobal.addAtmosphere("jeune");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Chic"){
                                filterGlobal.addAtmosphere("chic");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Romantique"){
                                filterGlobal.addAtmosphere("romantique");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Historique"){
                                filterGlobal.addAtmosphere("historique");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            } else if(vName.getText()=="Spectacle"){
                                filterGlobal.addAtmosphere("spectacle");
                                r.findViewHolderForAdapterPosition(7).itemView.setBackgroundColor(Color.WHITE);
                            }  else if(vName.getText()=="TOUS") {
                                r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(6).itemView.setBackgroundColor(Color.WHITE);
                                filterGlobal.removeAllAtmosphere();
                            }
                        } else {
                            view.setBackgroundColor(Color.WHITE);
                            this.isSelected = false;
                            if (vName.getText() == "Retro") {
                                filterGlobal.removeAtmosphere("retro");
                            } else if (vName.getText() == "Musical") {
                                filterGlobal.removeAtmosphere("musical");
                            } else if (vName.getText() == "Jeune") {
                                filterGlobal.removeAtmosphere("jeune");
                            } else if (vName.getText() == "Chic") {
                                filterGlobal.removeAtmosphere("chic");
                            } else if (vName.getText() == "Romantique") {
                                filterGlobal.removeAtmosphere("romantique");
                            } else if (vName.getText() == "Historique") {
                                filterGlobal.removeAtmosphere("historique");
                            } else if (vName.getText() == "Spectacle") {
                                filterGlobal.removeAtmosphere("spectacle");
                            }
                        }
                    }
                } else if(category.equals("<5min")){
//Choix unique
                    if(!this.isSelected) {
                        view.setBackgroundResource(R.drawable.card_border);
                        this.isSelected = true;
                        if(vName.getText()=="<5min"){
                            r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(6).itemView.setBackgroundColor(Color.WHITE);
                            filterGlobal.setWaitingTime(5);
                        } else if(vName.getText()=="<10min"){
                            r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(6).itemView.setBackgroundColor(Color.WHITE);
                            filterGlobal.setWaitingTime(10);
                        } else if(vName.getText()=="<15min"){
                            r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(6).itemView.setBackgroundColor(Color.WHITE);
                            filterGlobal.setWaitingTime(15);
                        } else if(vName.getText()=="<30min"){
                            r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(6).itemView.setBackgroundColor(Color.WHITE);
                            filterGlobal.setWaitingTime(30);
                        } else if(vName.getText()=="<45min"){
                            r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(6).itemView.setBackgroundColor(Color.WHITE);
                            filterGlobal.setWaitingTime(45);
                        } else if(vName.getText()=="<60min") {
                            r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(6).itemView.setBackgroundColor(Color.WHITE);
                            filterGlobal.setWaitingTime(60);
                        }  else if(vName.getText()=="TOUS") {
                            r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                            r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                            filterGlobal.setWaitingTime(0);
                        }
                    } else {
                        Drawable viewColor = view.getBackground();
                        if(viewColor instanceof ColorDrawable) {
                            if(vName.getText()=="<5min"){
                                r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(6).itemView.setBackgroundColor(Color.WHITE);
                                filterGlobal.setWaitingTime(5);
                            } else if(vName.getText()=="<10min"){
                                r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(6).itemView.setBackgroundColor(Color.WHITE);
                                filterGlobal.setWaitingTime(10);
                            } else if(vName.getText()=="<15min"){
                                r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(6).itemView.setBackgroundColor(Color.WHITE);
                                filterGlobal.setWaitingTime(15);
                            } else if(vName.getText()=="<30min"){
                                r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(6).itemView.setBackgroundColor(Color.WHITE);
                                filterGlobal.setWaitingTime(30);
                            } else if(vName.getText()=="<45min"){
                                r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(6).itemView.setBackgroundColor(Color.WHITE);
                                filterGlobal.setWaitingTime(45);
                            } else if(vName.getText()=="<60min") {
                                r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(6).itemView.setBackgroundColor(Color.WHITE);
                                filterGlobal.setWaitingTime(60);
                            }  else if(vName.getText()=="TOUS") {
                                r.findViewHolderForAdapterPosition(1).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(2).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(3).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(4).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(5).itemView.setBackgroundColor(Color.WHITE);
                                r.findViewHolderForAdapterPosition(0).itemView.setBackgroundColor(Color.WHITE);
                                filterGlobal.setWaitingTime(0);
                            }
                            this.isSelected = true;
                            view.setBackgroundResource(R.drawable.card_border);
                        } else {
                            view.setBackgroundColor(Color.WHITE);
                            this.isSelected = false;
                            filterGlobal.setWaitingTime(0);
                        }

                    }
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


}
