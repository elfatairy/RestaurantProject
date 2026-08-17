package com.example.restaurantproject.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.example.restaurantproject.R;

public class FoodSeeder {
    public static void seed(SQLiteDatabase db, String tableName) {
        ContentValues values = new ContentValues();

        // Initial 3 items
        insertFood(db, tableName, values, "Classic Burger", "Beef burger with cheese", 150.0, "Burger");
        insertFood(db, tableName, values, "Chicken Pizza", "Chicken pizza with cheese", 180.0, "Pizza");
        insertFood(db, tableName, values, "Pasta", "Creamy pasta with chicken", 120.0, "Pasta");

        // Next 10 items
        insertFood(db, tableName, values, "Cola", "Refreshing cold cola", 20.0, "Drinks");
        insertFood(db, tableName, values, "Lemonade", "Fresh squeezed lemonade", 25.0, "Drinks");
        insertFood(db, tableName, values, "Margherita Pizza", "Classic cheese and tomato pizza", 150.0, "Pizza");
        insertFood(db, tableName, values, "BBQ Chicken Pizza", "Pizza with BBQ sauce and chicken", 200.0, "Pizza");
        insertFood(db, tableName, values, "Cheeseburger", "Classic burger with double cheese", 160.0, "Burger");
        insertFood(db, tableName, values, "Double Smash Burger", "Two smashed beef patties with cheese", 220.0, "Burger");
        insertFood(db, tableName, values, "Alfredo Pasta", "Creamy white sauce pasta", 140.0, "Pasta");
        insertFood(db, tableName, values, "Bolognese Pasta", "Pasta with rich meat sauce", 150.0, "Pasta");
        insertFood(db, tableName, values, "Caesar Salad", "Lettuce, croutons, and Caesar dressing", 80.0, "Salad");
        insertFood(db, tableName, values, "Greek Salad", "Tomatoes, cucumbers, olives, and feta cheese", 75.0, "Salad");

        // Next 10 items
        insertFood(db, tableName, values, "Water", "Mineral water", 10.0, "Drinks");
        insertFood(db, tableName, values, "Iced Tea", "Peach iced tea", 30.0, "Drinks");
        insertFood(db, tableName, values, "Pepperoni Pizza", "Classic pepperoni and cheese", 170.0, "Pizza");
        insertFood(db, tableName, values, "Vegetarian Pizza", "Pizza with fresh vegetables", 140.0, "Pizza");
        insertFood(db, tableName, values, "Mushroom Swiss Burger", "Burger with mushrooms and swiss cheese", 180.0, "Burger");
        insertFood(db, tableName, values, "Chicken Burger", "Crispy chicken breast burger", 150.0, "Burger");
        insertFood(db, tableName, values, "Mac and Cheese", "Classic macaroni and cheese", 130.0, "Pasta");
        insertFood(db, tableName, values, "Penne Arrabbiata", "Spicy tomato sauce pasta", 110.0, "Pasta");
        insertFood(db, tableName, values, "Chocolate Cake", "Rich chocolate layer cake", 90.0, "Dessert");
        insertFood(db, tableName, values, "Ice Cream", "Vanilla bean ice cream", 60.0, "Dessert");

        // Additional 10 NEW items
        insertFood(db, tableName, values, "Orange Juice", "Fresh orange juice", 25.0, "Drinks");
        insertFood(db, tableName, values, "Milkshake", "Chocolate milkshake", 40.0, "Drinks");
        insertFood(db, tableName, values, "Hawaiian Pizza", "Pineapple and ham pizza", 160.0, "Pizza");
        insertFood(db, tableName, values, "Buffalo Chicken Pizza", "Spicy buffalo chicken pizza", 190.0, "Pizza");
        insertFood(db, tableName, values, "Veggie Burger", "Plant-based burger patty", 150.0, "Burger");
        insertFood(db, tableName, values, "Bacon Burger", "Burger with crispy bacon", 190.0, "Burger");
        insertFood(db, tableName, values, "Spaghetti Meatballs", "Spaghetti with homemade meatballs", 140.0, "Pasta");
        insertFood(db, tableName, values, "Lasagna", "Layered pasta with meat and cheese", 170.0, "Pasta");
        insertFood(db, tableName, values, "Cheesecake", "New York style cheesecake", 95.0, "Dessert");
        insertFood(db, tableName, values, "Brownie", "Warm chocolate brownie", 50.0, "Dessert");
    }

    private static void insertFood(SQLiteDatabase db, String tableName, ContentValues values, 
                                   String name, String description, double price, String category) {
        values.put("name", name);
        values.put("description", description);
        values.put("price", price);
        values.put("category", category);
        String fileName = name.toLowerCase().replace(" ", "_") + ".jpg";
        values.put("image", "file:///android_asset/images/" + fileName);
        values.put("is_available", 1);
        db.insert(tableName, null, values);
    }
}
