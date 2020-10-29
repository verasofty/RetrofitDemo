package com.example.demoretrofit.model;

public class Products {

    //"id": 1,
    //        "name": "Product001",
    //        "cost": 10,
    //        "quantity": 1000,
    //        "locationId": 1,
    //        "familyId": 1

    private int id;
    private String name;
    private int cost;
    private int quantity;
    private int locationId;
    private int familyId;

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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

}
