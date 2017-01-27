package com.platine.liveresto.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.platine.liveresto.R;

import java.util.List;

/**
 * Created by Nath on 27/01/2017.
 */
public class ElementsAdapter extends RecyclerView.Adapter<MyViewHolder> implements MyViewHolder.Listener{
    private static final int VIEW_NORMAL = 1;
    private List<Data> contactList;


    public ElementsAdapter(List<Data> contactList) {
        this.contactList = contactList;
    }


    public void onCardViewClick(int position){
        contactList.get(position).setSelected(!contactList.get(position).isSelected());
        notifyItemChanged(position);
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
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        Data ci = contactList.get(position);
        viewHolder.bindViewHolder(ci);
        viewHolder.setListener(this,position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View textView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_element, parent, false);
        return new MyViewHolder(textView);
    }



}