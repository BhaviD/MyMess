package com.example.mymess;

public class ChooseMessProduct {

    private int id;
    private String meal;
    private String registered_mess;

    public ChooseMessProduct(int id, String meal) {
        this.id = id;
        this.meal = meal;
        this.registered_mess = null;
    }

    public int getId() {
        return id;
    }

    public String getMeal() {
        return meal;
    }

    public String getRegistered_mess() {
        return registered_mess;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public void setRegistered_mess(String registered_mess) {
        this.registered_mess = registered_mess;
    }
}
