package com.platine.liveresto;

/**
 * Created by Nathanael on 04/01/2017.
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
}
