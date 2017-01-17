package com.platine.liveresto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.platine.liveresto.R;
import com.platine.liveresto.model.Item;

import java.util.List;

/**
 * Created by --- on 03/01/2017.
 */
/*
public class Adapter extends RecyclerView.Adapter<Adapter.Holder>{
    private List<Item> listData;
    private LayoutInflater inflater;

    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback {
        void onItemClick(int p);
    }

    public Adapter(List<Item> listData, Context c){
        inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    @Override
    public Adapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Item item = listData.get(position);
        holder.title.setText(item.getTitle());
        //holder.icon_down.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }



    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        View container;

        public Holder(View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.lbl_item_text);
            //icon_down = (ImageView)itemView.findViewById(R.id.im_icon_flower);
            container = (View)itemView.findViewById(R.id.cont_item_root);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.cont_item_root){
                itemClickCallback.onItemClick(getAdapterPosition());
            } else {

            }
        }
    }
}*/
