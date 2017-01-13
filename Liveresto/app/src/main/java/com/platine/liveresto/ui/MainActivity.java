package com.platine.liveresto.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setTitle("Filtres");



        TextView header = new TextView(this);
        header.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        header.setTextColor(Color.parseColor("#000000"));
        header.setText("This is the header");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);

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

            public TextView textView;

            public ViewHolder(TextView v) {
                super(v);
                textView = v;
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
                return new HeaderViewHolder(headerView);

            } else {
                TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_element, parent, false);
                return new ViewHolder(textView);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

            if (position == 0) return;

            ViewHolder holder = (ViewHolder) viewHolder;
            holder.textView.setText("Position " + (position - 1));

        }
    }
}
