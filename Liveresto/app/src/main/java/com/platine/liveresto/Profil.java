package com.platine.liveresto;

/**
 * Created by Nathanael on 04/01/2017.
 */

public class Profil {

    private int id;
    private String name;
    private String type;
    private int distance;
    private String atmosphere;
    private int startBudget;
    private int endBudget;
    private String payment;
    private int places;
    private int waitingTime;
    private boolean terrace;
    private boolean airConditionner;
    private String days;
    private double startSchedule;
    private double endSchedule;

    public Profil(int id, String name, String type, int distance, String atmosphere, int startBudget, int endBudget, String payment, int places, int waitingTime, boolean terrace, boolean airConditionner, String days, double startSchedule, double endSchedule) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
        this.distance = distance;
        this.atmosphere = atmosphere;
        this.startBudget = startBudget;
        this.endBudget = endBudget;
        this.payment = payment;
        this.places = places;
        this.waitingTime = waitingTime;
        this.terrace = terrace;
        this.airConditionner = airConditionner;
        this.days = days;
        this.startSchedule = startSchedule;
        this.endSchedule = endSchedule;
    }

    public Profil(String name, String type, int distance, String atmosphere, int startBudget, int endBudget, String payment, int places, int waitingTime, boolean terrace, boolean airConditionner, String days, double startSchedule, double endSchedule) {
        super();
        this.name = name;
        this.type = type;
        this.distance = distance;
        this.atmosphere = atmosphere;
        this.startBudget = startBudget;
        this.endBudget = endBudget;
        this.payment = payment;
        this.places = places;
        this.waitingTime = waitingTime;
        this.terrace = terrace;
        this.airConditionner = airConditionner;
        this.days = days;
        this.startSchedule = startSchedule;
        this.endSchedule = endSchedule;
    }


    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(String atmosphere) {
        this.atmosphere = atmosphere;
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

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public double getStartSchedule() {
        return startSchedule;
    }

    public void setStartSchedule(float startSchedule) {
        this.startSchedule = startSchedule;
    }

    public double getEndSchedule() {
        return endSchedule;
    }

    public void setEndSchedule(float endSchedule) {
        this.endSchedule = endSchedule;
    }
}
