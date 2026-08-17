package com.example.restaurantproject.models;

public class CartItem {
    private int id;
    private int userId;
    private int foodId;
    private int quantity;
    private String size;
    private String specialInstructions;

    public CartItem() {}

    public CartItem(int id, int userId, int foodId, int quantity, String size, String specialInstructions) {
        this.id = id;
        this.userId = userId;
        this.foodId = foodId;
        this.quantity = quantity;
        this.size = size;
        this.specialInstructions = specialInstructions;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public int getFoodId() { return foodId; }
    public void setFoodId(int foodId) { this.foodId = foodId; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    
    public String getSpecialInstructions() { return specialInstructions; }
    public void setSpecialInstructions(String specialInstructions) { this.specialInstructions = specialInstructions; }
}
