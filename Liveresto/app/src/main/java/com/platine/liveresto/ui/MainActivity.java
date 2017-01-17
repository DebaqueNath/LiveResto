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
import android.widget.TextView;

import com.platine.liveresto.model.Horaire;
import com.platine.liveresto.model.HoraireDAO;
import com.platine.liveresto.R;
import com.platine.liveresto.model.Restaurant;
import com.platine.liveresto.model.RestaurantDAO;
import com.platine.liveresto.adapter.Adapter;
import com.platine.liveresto.model.Item;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Adapter.ItemClickCallback {
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    private RecyclerView recView;

    private Adapter adapter;
    private ArrayList listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fixtures();




        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setTitle("Filtres");



        TextView header = new TextView(this);
        header.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        header.setTextColor(Color.parseColor("#000000"));
        header.setText("Type de restaurant");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 3 : 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        //drawableRight sur button
        ElementsAdapter elementsAdapter = new ElementsAdapter(5);
        elementsAdapter.setHeader(header);

        recyclerView.setAdapter(elementsAdapter);

        /*listData = (ArrayList) Data.getListData();

        recView = (RecyclerView)findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter(Data.getListData(), this);
        recView.setAdapter(adapter);
        adapter.setItemClickCallback(this);*/
    }

    @Override
    public void onItemClick(int p) {
        Item item = (Item) listData.get(p);

        Intent i = new Intent(this, DetailActivity.class);

        Bundle extras = new Bundle();
        extras.putString(EXTRA_QUOTE, item.getTitle());
        i.putExtra(BUNDLE_EXTRAS, extras);

        startActivity(i);
    }


    public class ElementsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int VIEW_HEADER = 0;
        private static final int VIEW_NORMAL = 1;

        private View headerView;
        private int datasetSize;


        public class HeaderViewHolder extends RecyclerView.ViewHolder {
            public HeaderViewHolder(View v) {
                super(v);
            }
        }

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

        public void setHeader(View v) {
            this.headerView = v;
        }

        @Override
        public int getItemViewType(int position) {
            return position == 0 ? VIEW_HEADER : VIEW_NORMAL;
        }

        @Override
        public int getItemCount() {
            return datasetSize + 1;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_HEADER) {
                headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);


                return new HeaderViewHolder(headerView);

            } else {
                View textView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_element, parent, false);
                return new ViewHolder(textView);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

            if (position == 0) return;

            ViewHolder holder = (ViewHolder) viewHolder;
            //holder.textView.setText("Position " + (position - 1));

        }
    }

    //Add restaurant to database
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
        Horaire h1 = new Horaire(1,"LU 08,30 14,30");
        Horaire h2 = new Horaire(1,"LU 19,30 22,30");
        Horaire h3 = new Horaire(2,"MA 08,30 14,30");
        Horaire h4 = new Horaire(2,"MA 19,30 22,30");
        //Add schedule
        horaireDao.putHoraire(h1);
        horaireDao.putHoraire(h2);
        horaireDao.putHoraire(h3);
        horaireDao.putHoraire(h4);
    }
}
