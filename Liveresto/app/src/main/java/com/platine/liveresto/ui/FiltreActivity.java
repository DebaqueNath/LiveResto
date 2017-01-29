package com.platine.liveresto.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.platine.liveresto.R;
import com.platine.liveresto.model.Data;
import com.platine.liveresto.model.Filtre;
import com.platine.liveresto.rangeseekbar.RangeSeekBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.platine.liveresto.ui.MainActivity.filterGlobal;

public class FiltreActivity extends AppCompatActivity {
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
    private RangeSeekBar rangeSeekBarDistance;
    private RangeSeekBar rangeSeekBarSchedule;
    private RangeSeekBar rangeSeekBarPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtre);

        /*
        //On récupère l'intent et les données
        Intent i = getIntent();
        filterGlobal = new Filtre(i.getDoubleExtra("distanceFilter",0.0),i.getStringArrayListExtra("daysFilter"),i.getDoubleExtra("hourBeginFilter",0.0),i.getDoubleExtra("hourEndFilter",0.0),i.getStringArrayListExtra("typeFilter"),i.getIntExtra("startBudgetFilter",0),i.getIntExtra("endBudgetFilter",0),i.getStringArrayListExtra("paymentFilter"),i.getStringArrayListExtra("atmosphereFilter"),i.getIntExtra("placesFilter",0),i.getIntExtra("waitingTimeFilter",0),i.getBooleanExtra("terraceFilter",false),i.getBooleanExtra("airConditionnerFilter",false));
*/

        backArrowFilter = (ImageView) findViewById(R.id.back_arrow_filter);
        backArrowFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),RestaurantActivity.class);
                startActivity(i);
            }
        });


        initToolbar();

        initFilters();

        initRecyclerView();

        addListenerOnButton();
    }

    protected void onStop() {
        super.onStop();
        /*Intent i = getIntent();
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
        setResult(RESULT_OK, i);*/
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

        budgetList = new ArrayList<>();
        budgetList.add(new Data("<20", R.drawable.icon_inf_20));
        budgetList.add(new Data("20 à 39", R.drawable.icon_from_20_to_39));
        budgetList.add(new Data("40 à 59", R.drawable.icon_from_40_to_59));
        budgetList.add(new Data("60 à 79", R.drawable.icon_from_60_to_79));
        budgetList.add(new Data(">80", R.drawable.icon_sup_80));

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
        //On initialise tous les éléments
        rangeSeekBarDistance = (RangeSeekBar) findViewById(R.id.rangeseekbardistance);
        rangeSeekBarDistance.setLabel("km");
        rangeSeekBarDistance.setRangeValues(0,50);
        rangeSeekBarDistance.setSelectedMaxValue(0);
        rangeSeekBarDistance.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {
                filterGlobal.setDistanceMax(Double.parseDouble(maxValue.toString()));
            }
        });
        rangeSeekBarDistance.setVisibility(View.GONE);

        rangeSeekBarSchedule = (RangeSeekBar) findViewById(R.id.rangeseekbarschedule);
        rangeSeekBarSchedule.setLabel("h");
        rangeSeekBarSchedule.setRangeValues(0,24);
        rangeSeekBarSchedule.setSelectedMinValue(0);
        rangeSeekBarSchedule.setSelectedMaxValue(24);
        rangeSeekBarSchedule.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {
                filterGlobal.setHourBegin(Double.parseDouble(minValue.toString()));
                filterGlobal.setHourEnd(Double.parseDouble(maxValue.toString()));
            }
        });
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
        rangeSeekBarPlaces.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {
                System.out.println("VALEUR   MAX : "+maxValue);
            }
        });
        rangeSeekBarPlaces.setVisibility(View.GONE);

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


}
