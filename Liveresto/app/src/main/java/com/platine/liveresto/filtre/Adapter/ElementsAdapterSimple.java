package com.platine.liveresto.filtre.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.platine.liveresto.R;
import com.platine.liveresto.model.Data;
import com.platine.liveresto.filtre.Holder.MyViewHolder;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Nath on 27/01/2017.
 */

//Adapter for recyclerView with unique selection
public class ElementsAdapterSimple extends RecyclerView.Adapter<MyViewHolder> implements MyViewHolder.Listener{
    private static final int VIEW_NORMAL = 1;
    private List<Data> contactList;
    private WeakReference<Listener> mListener;


    public ElementsAdapterSimple(List<Data> contactList, Listener listener) {
        this.contactList = contactList;
        setListener(listener);
    }


    public void onCardViewClick(int position){
        for (Data d : contactList) {
            d.setSelected(false);
        }
        contactList.get(position).setSelected(!contactList.get(position).isSelected());
        notifyDataSetChanged();
        if(getListener()!=null){
            getListener().onCardViewClick(contactList.get(position));
        }
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

    public Listener getListener() {
        return mListener != null ? mListener.get() : null;
    }

    public void setListener(Listener listener) {
        mListener = new WeakReference<>(listener);
    }

    public interface Listener {
        void onCardViewClick(Data d);
    }
}