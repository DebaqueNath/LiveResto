package com.platine.liveresto.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.platine.liveresto.R;
import com.platine.liveresto.adapter.Adapter;
import com.platine.liveresto.model.Data;
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

        listData = (ArrayList) Data.getListData();

        recView = (RecyclerView)findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter(Data.getListData(), this);
        recView.setAdapter(adapter);
        adapter.setItemClickCallback(this);
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
}
