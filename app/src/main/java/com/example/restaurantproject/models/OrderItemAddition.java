package com.example.restaurantproject.models;

public class OrderItemAddition {
    private int id;
    private int orderItemId;
    private String additionName;
    private double price;

    public OrderItemAddition() {}

    public OrderItemAddition(int id, int orderItemId, String additionName, double price) {
        this.id = id;
        this.orderItemId = orderItemId;
        this.additionName = additionName;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderItemId() { return orderItemId; }
    public void setOrderItemId(int orderItemId) { this.orderItemId = orderItemId; }

    public String getAdditionName() { return additionName; }
    public void setAdditionName(String additionName) { this.additionName = additionName; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
