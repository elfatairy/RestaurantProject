package com.example.restaurantproject.models;

public class FoodAddition {
    private int id;
    private int foodId;
    private String additionName;
    private double price;

    public FoodAddition() {}

    public FoodAddition(int id, int foodId, String additionName, double price) {
        this.id = id;
        this.foodId = foodId;
        this.additionName = additionName;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getFoodId() { return foodId; }
    public void setFoodId(int foodId) { this.foodId = foodId; }
    
    public String getAdditionName() { return additionName; }
    public void setAdditionName(String additionName) { this.additionName = additionName; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
