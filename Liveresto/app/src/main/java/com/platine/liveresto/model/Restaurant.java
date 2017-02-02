package com.platine.liveresto.model;

import java.util.ArrayList;

/**
 * Created by Nathanael on 04/01/2017.
 * Represent a Restaurant
 */

public class Restaurant {

    private int id;
    private String name;
    private String adress;
    private String phoneNumber;
    private String website;
    //Url of the picture
    private String picture;
    private double longitude;
    private double latitude;
    private boolean favorite;
    private boolean historic;
    private String type;
    private String atmosphere;
    private int startBudget;
    private int endBudget;
    private String payment;
    private int places;
    private int waitingTime;
    private boolean terrace;
    private boolean airConditionner;
    //Schedule list
    private ArrayList<Horaire> shedule;


    public Restaurant(int id, String name, String adress, String phoneNumber, String website, String picture, double longitude, double latitude, boolean favorite, boolean historic, String type, String atmosphere, int startBudget, int endBudget, String payment, int places, int waitingTime, boolean terrace, boolean airConditionner) {
        super();
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.picture = picture;
        this.longitude = longitude;
        this.latitude = latitude;
        this.favorite = favorite;
        this.historic = historic;
        this.type = type;
        this.atmosphere = atmosphere;
        this.startBudget = startBudget;
        this.endBudget = endBudget;
        this.payment = payment;
        this.places = places;
        this.waitingTime = waitingTime;
        this.terrace = terrace;
        this.airConditionner = airConditionner;
    }

    //Constructor without id
    public Restaurant(String name, String adress, String phoneNumber, String website, String picture, double longitude, double latitude, boolean favorite, boolean historic, String type, String atmosphere, int startBudget, int endBudget, String payment, int places, int waitingTime, boolean terrace, boolean airConditionner) {
        super();
        this.name = name;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.picture = picture;
        this.longitude = longitude;
        this.latitude = latitude;
        this.favorite = favorite;
        this.historic = historic;
        this.type = type;
        this.atmosphere = atmosphere;
        this.startBudget = startBudget;
        this.endBudget = endBudget;
        this.payment = payment;
        this.places = places;
        this.waitingTime = waitingTime;
        this.terrace = terrace;
        this.airConditionner = airConditionner;
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isHistoric() {
        return historic;
    }

    public void setHistoric(boolean historic) {
        this.historic = historic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public ArrayList<Horaire> getShedule() {
        return shedule;
    }

    public void setShedule(ArrayList<Horaire> shedule) {
        this.shedule = shedule;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", adress='" + adress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", website='" + website + '\'' +
                ", picture='" + picture + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", favorite=" + favorite +
                ", historic=" + historic +
                ", type='" + type + '\'' +
                ", atmosphere='" + atmosphere + '\'' +
                ", startBudget=" + startBudget +
                ", endBudget=" + endBudget +
                ", payment='" + payment + '\'' +
                ", places=" + places +
                ", waitingTime=" + waitingTime +
                ", terrace=" + terrace +
                ", airConditionner=" + airConditionner +
                ", shedule=" + shedule +
                '}';
    }
}
