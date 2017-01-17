package com.platine.liveresto.model;


/**
 * Created by Nath on 17/01/2017.
 */
public class Filtre {

    //------- List of filters
    private double distanceMax;
    //exemple : LU,MA,JE
    private String days;
    private double hourBegin;
    private double hourEnd;
    //exemple : pizzeria,halal,bio
    private String type;
    private int startBudget;
    private int endBudget;
    //exemple : espece,cheque
    private String payment;
    //exemple : musical,chic,spectacle
    private String atmosphere;
    private int places;
    private int waitingTime;
    private boolean terrace;
    private boolean airConditionner;

    public Filtre(double distanceMax, String days, double hourBegin, double hourEnd, String type, int startBudget, int endBudget, String payment, String atmosphere, int places, int waitingTime, boolean terrace, boolean airConditionner) {
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

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(String atmosphere) {
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
}
