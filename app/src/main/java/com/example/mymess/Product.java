package com.example.mymess;

public class Product {

    private int id;
    private String meal;

    public Product(int id, String meal) {
        this.id = id;
        this.meal = meal;
    }

    public int getId() {
        return id;
    }

    public String getMeal() {
        return meal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }
}
