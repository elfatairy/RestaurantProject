package com.example.restaurantproject;

public class Food {

    private String name;
    private String description;
    private double price;
    private String category;
    private int image;

    public Food(String name, String description, double price,
                String category, int image) {

        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public int getImage() {
        return image;
    }
}