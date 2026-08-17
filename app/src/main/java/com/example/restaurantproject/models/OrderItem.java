package com.example.restaurantproject.models;

public class OrderItem {
    private int id;
    private int orderId;
    private int foodId;
    private int quantity;
    private String size;
    private double unitPrice;
    private int extraCheese;
    private int extraBacon;
    private int extraSauce;
    private int extraLettuce;

    public OrderItem() {}

    public OrderItem(int id, int orderId, int foodId, int quantity, String size, double unitPrice) {
        this.id = id;
        this.orderId = orderId;
        this.foodId = foodId;
        this.quantity = quantity;
        this.size = size;
        this.unitPrice = unitPrice;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getFoodId() { return foodId; }
    public void setFoodId(int foodId) { this.foodId = foodId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public int getExtraCheese() { return extraCheese; }
    public void setExtraCheese(int extraCheese) { this.extraCheese = extraCheese; }

    public int getExtraBacon() { return extraBacon; }
    public void setExtraBacon(int extraBacon) { this.extraBacon = extraBacon; }

    public int getExtraSauce() { return extraSauce; }
    public void setExtraSauce(int extraSauce) { this.extraSauce = extraSauce; }

    public int getExtraLettuce() { return extraLettuce; }
    public void setExtraLettuce(int extraLettuce) { this.extraLettuce = extraLettuce; }
}
