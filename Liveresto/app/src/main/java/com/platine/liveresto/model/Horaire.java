package com.platine.liveresto.model;

/**
 * Created by Nathanael on 04/01/2017.
 * Represent a schedule
 */

public class Horaire {

    private int id;
    private int idRestaurant;
    private String schedule;

    public Horaire(int id, int idRestaurant, String schedule) {
        super();
        this.id = id;
        this.idRestaurant = idRestaurant;
        this.schedule = schedule;
    }

    //Constructor without id
    public Horaire(int idRestaurant, String schedule) {
        super();
        this.idRestaurant = idRestaurant;
        this.schedule = schedule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(int idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getDay(){
        return this.schedule.substring(0,2);
    }

    public double getBeginHour(){
        return Double.parseDouble(this.schedule.substring(3,8));
    }

    public double getEndHour(){
        return Double.parseDouble(this.schedule.substring(9,14));
    }

    @Override
    public String toString() {
        return "Horaire{" +
                "id=" + id +
                ", idRestaurant=" + idRestaurant +
                ", schedule='" + schedule + '\'' +
                '}';
    }
}
