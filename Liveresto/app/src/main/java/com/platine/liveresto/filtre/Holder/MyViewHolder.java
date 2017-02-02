package com.platine.liveresto.filtre.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.platine.liveresto.R;
import com.platine.liveresto.model.Data;

import java.lang.ref.WeakReference;

/**
 * Created by Nath on 27/01/2017.
 **/

//ViewHolder represent a view of a choice in filters
public class MyViewHolder extends RecyclerView.ViewHolder {

    protected TextView vName;
    protected ImageView vImage;
    public View textView;
    public LinearLayout layoutCardGlobal;
    private WeakReference<Listener> mListener;

    public MyViewHolder(View itemView) {
        super(itemView);
        textView = itemView;
        vName =  (TextView) itemView.findViewById(R.id.title);
        vImage =  (ImageView) itemView.findViewById(R.id.image);
        layoutCardGlobal = (LinearLayout) itemView.findViewById(R.id.card_global_layout);
    }

    public void bindViewHolder(Data d){
        if(d.name.contains("TOUS")) {
            this.vName.setText(d.name.substring(0,4));
        } else {
            this.vName.setText(d.name);
        }
        this.vImage.setImageResource(d.imageId);
        //Depends if cardview is selected or not
        if(d.isSelected){
            layoutCardGlobal.setBackgroundResource(R.drawable.card_border);
        } else {
            layoutCardGlobal.setBackgroundResource(R.drawable.card_border_white);
        }
    }

    public Listener getListener() {
        return mListener != null ? mListener.get() : null;
    }

    public void setListener(Listener listener, final int position) {
        mListener = new WeakReference<>(listener);
        if (getListener() != null) {
            layoutCardGlobal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getListener().onCardViewClick(position);
                }
            });
        }
    }

    public interface Listener {
        void onCardViewClick(int position);
    }
}
