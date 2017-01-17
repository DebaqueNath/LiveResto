package com.platine.liveresto.model;


import java.util.ArrayList;

/**
 * Created by Nath on 17/01/2017.
 */
public class Filtre {

    //------- List of filters
    private double distanceMax;

    private ArrayList<String> days;
    private double hourBegin;
    private double hourEnd;
    private ArrayList<String> type;
    private int startBudget;
    private int endBudget;
    private ArrayList<String> payment;
    private ArrayList<String> atmosphere;
    private int places;
    private int waitingTime;
    private boolean terrace;
    private boolean airConditionner;

    public Filtre(double distanceMax, ArrayList<String> days, double hourBegin, double hourEnd, ArrayList<String> type, int startBudget, int endBudget, ArrayList<String> payment, ArrayList<String> atmosphere, int places, int waitingTime, boolean terrace, boolean airConditionner) {
        this.distanceMax = distanceMax;
        this.days = days;
        this.hourBegin = hourBegin;
        this.hourEnd = hourEnd;
        this.type = type;
        this.startBudget = startBudget;
        this.endBudget = endBudget;
        this.payment = payment;
        this.atmosphere = atmosphere;
        this.places = places;
        this.waitingTime = waitingTime;
        this.terrace = terrace;
        this.airConditionner = airConditionner;
    }

    public double getDistanceMax() {
        return distanceMax;
    }

    public void setDistanceMax(double distanceMax) {
        this.distanceMax = distanceMax;
    }

    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }

    public double getHourBegin() {
        return hourBegin;
    }

    public void setHourBegin(double hourBegin) {
        this.hourBegin = hourBegin;
    }

    public double getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(double hourEnd) {
        this.hourEnd = hourEnd;
    }

    public ArrayList<String> getType() {
        return type;
    }

    public void setType(ArrayList<String> type) {
        this.type = type;
    }

    public int getStartBudget() {
        return startBudget;
    }

    public void setStartBudget(int startBudget) {
        this.startBudget = startBudget;
    }

    public int getEndBudget() {
        return endBudget;
    }

    public void setEndBudget(int endBudget) {
        this.endBudget = endBudget;
    }

    public ArrayList<String> getPayment() {
        return payment;
    }

    public void setPayment(ArrayList<String> payment) {
        this.payment = payment;
    }

    public ArrayList<String> getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(ArrayList<String> atmosphere) {
        this.atmosphere = atmosphere;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public boolean isTerrace() {
        return terrace;
    }

    public void setTerrace(boolean terrace) {
        this.terrace = terrace;
    }

    public boolean isAirConditionner() {
        return airConditionner;
    }

    public void setAirConditionner(boolean airConditionner) {
        this.airConditionner = airConditionner;
    }

    //Get restaurant with filter
    public ArrayList<Restaurant> getRestaurantsFilter(ArrayList<Restaurant> restaurants){
        ArrayList<Restaurant> restaurantsResult = new ArrayList<>();

        for (Restaurant r : restaurants) {
            /*Calcul de la distance du restaurant par rapport à la position de l'utilisateur
            getPosX de l'user
            getPosY de l'user
            ....
            x user + distanceMax > latitude du restaurant
            y user + distanceMax > longitude du restaurant

            Pour calculer la distance entre 2 coordonnées
            https://developers.google.com/maps/documentation/distance-matrix/intro?hl=fr

            Si distance < distanceMax, on garde le restaurant
            */

            //Horaires du restaurant
            ArrayList<Horaire> schedule = r.getShedule();
            boolean flag = false;
            for (Horaire h : schedule) {
                String day = h.getDay();
                double begin = h.getBeginHour();
                double end = h.getEndHour();
                if(days.contains(day)){
                    if((begin >= this.hourBegin && begin <= this.hourEnd)||(end >= this.hourBegin && end <= this.hourEnd)||(begin < this.hourBegin && end > this.hourEnd)){
                        flag = true;
                    }
                }
            }
            if(!flag){continue;}

            //Type du restaurant
            String type = r.getType();
            String[] split = type.split(",");
            flag = false;
            for (String s : split) {
                if(getType().contains(s)){
                    flag = true;
                }
            }
            if(!flag){continue;}

            //Budget
            if((r.getStartBudget() >= this.startBudget && r.getStartBudget() <= this.endBudget)||(r.getEndBudget() >= this.startBudget && r.getEndBudget() <= this.endBudget)||(r.getStartBudget() < this.startBudget && r.getEndBudget() > this.endBudget)){
                //OKAY
            } else {
                continue;
            }

            //Payment
            String payment = r.getPayment();
            String[] split2 = payment.split(",");
            flag = false;
            for (String s : split2) {
                if(getPayment().contains(s)){
                    flag = true;
                }
            }
            if(!flag){continue;}

            //Atmosphere
            String atmosphere = r.getAtmosphere();
            String[] split3 = atmosphere.split(",");
            flag = false;
            for (String s : split3) {
                if(getAtmosphere().contains(s)){
                    flag = true;
                }
            }
            if(!flag){continue;}

            //Places
            if(r.getPlaces() < this.places) {
                continue;
            }


            //WaitingTime
            if(r.getWaitingTime() > this.waitingTime){
                continue;
            }

            //Terrace
            if(this.terrace && !r.isTerrace()){
                continue;
            }

            //AirConditionner
            if(this.airConditionner && !r.isAirConditionner()){
                continue;
            }

            //Si on arrive ici, le restaurant correspond aux filtres
            restaurantsResult.add(r);
        }

        return restaurantsResult;
    }
}
