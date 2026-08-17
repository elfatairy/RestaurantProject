package com.example.restaurantproject.models;

public class CartItemAddition {
    private int id;
    private int cartItemId;
    private int additionId;

    public CartItemAddition() {}

    public CartItemAddition(int id, int cartItemId, int additionId) {
        this.id = id;
        this.cartItemId = cartItemId;
        this.additionId = additionId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getCartItemId() { return cartItemId; }
    public void setCartItemId(int cartItemId) { this.cartItemId = cartItemId; }
    
    public int getAdditionId() { return additionId; }
    public void setAdditionId(int additionId) { this.additionId = additionId; }
}
