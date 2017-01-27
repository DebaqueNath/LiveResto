package com.platine.liveresto.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.platine.liveresto.R;

import java.lang.ref.WeakReference;

/**
 * Created by Nath on 27/01/2017.
 **/

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
        this.vName.setText(d.name);
        this.vImage.setImageResource(d.imageId);
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

   /* @Override
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
    }*/
}
