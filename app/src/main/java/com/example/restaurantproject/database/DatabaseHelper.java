package com.example.restaurantproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

import com.example.restaurantproject.models.CartItem;
import com.example.restaurantproject.models.Food;
import com.example.restaurantproject.models.Order;
import com.example.restaurantproject.models.OrderItem;
import com.example.restaurantproject.R;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Restaurant.db";
    private static final int DATABASE_VERSION = 3;

    // Table Names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_FOODS = "foods";
    public static final String TABLE_CART_ITEMS = "cart_items";
    public static final String TABLE_ORDERS = "orders";
    public static final String TABLE_ORDER_ITEMS = "order_items";

    // Create Tables Statements
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT,"
            + "email TEXT,"
            + "password TEXT,"
            + "phone_number TEXT,"
            + "address TEXT,"
            + "created_at DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")";

    private static final String CREATE_TABLE_FOODS = "CREATE TABLE " + TABLE_FOODS + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT,"
            + "description TEXT,"
            + "price REAL,"
            + "category TEXT,"
            + "image TEXT,"
            + "is_available INTEGER DEFAULT 1"
            + ")";

    private static final String CREATE_TABLE_CART_ITEMS = "CREATE TABLE " + TABLE_CART_ITEMS + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "user_id INTEGER,"
            + "food_id INTEGER,"
            + "quantity INTEGER,"
            + "size TEXT,"
            + "special_instructions TEXT,"
            + "extra_cheese INTEGER DEFAULT 0,"
            + "extra_bacon INTEGER DEFAULT 0,"
            + "extra_sauce INTEGER DEFAULT 0,"
            + "extra_lettuce INTEGER DEFAULT 0,"
            + "FOREIGN KEY(user_id) REFERENCES " + TABLE_USERS + "(id),"
            + "FOREIGN KEY(food_id) REFERENCES " + TABLE_FOODS + "(id)"
            + ")";

    private static final String CREATE_TABLE_ORDERS = "CREATE TABLE " + TABLE_ORDERS + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "user_id INTEGER,"
            + "total_price REAL,"
            + "status TEXT,"
            + "delivery_address TEXT,"
            + "ordered_at DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + "FOREIGN KEY(user_id) REFERENCES " + TABLE_USERS + "(id)"
            + ")";

    private static final String CREATE_TABLE_ORDER_ITEMS = "CREATE TABLE " + TABLE_ORDER_ITEMS + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "order_id INTEGER,"
            + "food_id INTEGER,"
            + "quantity INTEGER,"
            + "size TEXT,"
            + "unit_price REAL,"
            + "extra_cheese INTEGER DEFAULT 0,"
            + "extra_bacon INTEGER DEFAULT 0,"
            + "extra_sauce INTEGER DEFAULT 0,"
            + "extra_lettuce INTEGER DEFAULT 0,"
            + "FOREIGN KEY(order_id) REFERENCES " + TABLE_ORDERS + "(id),"
            + "FOREIGN KEY(food_id) REFERENCES " + TABLE_FOODS + "(id)"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_FOODS);
        db.execSQL(CREATE_TABLE_CART_ITEMS);
        db.execSQL(CREATE_TABLE_ORDERS);
        db.execSQL(CREATE_TABLE_ORDER_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS order_item_additions");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS cart_item_additions");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS food_additions");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        
        // Create tables again
        onCreate(db);
    }
    // ==========================================
    // CRUD & BUSINESS LOGIC
    // ==========================================

    public long registerUser(String name, String email, String password, String phone, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        // Check if email already exists
        Cursor cursor = db.query(TABLE_USERS, new String[]{"id"}, "email=?", new String[]{email}, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return -1; // Email already exists
        }
        cursor.close();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("password", password);
        values.put("phone_number", phone);
        values.put("address", address);

        return db.insert(TABLE_USERS, null, values);
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{"id"}, "email=? AND password=?", new String[]{email, password}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public int getUserIdByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{"id"}, "email=?", new String[]{email}, null, null, null);
        int id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    public void seedInitialFoods() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_FOODS, null);
        if (cursor.moveToFirst() && cursor.getInt(0) == 0) {
            FoodSeeder.seed(db, TABLE_FOODS);
        }
        cursor.close();
    }

    public List<Food> getFoods() {
        List<Food> foods = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FOODS, null);

        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                food.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                food.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                food.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow("price")));
                food.setCategory(cursor.getString(cursor.getColumnIndexOrThrow("category")));
                food.setImage(cursor.getString(cursor.getColumnIndexOrThrow("image")));
                food.setAvailable(cursor.getInt(cursor.getColumnIndexOrThrow("is_available")) == 1);
                foods.add(food);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return foods;
    }

    public Food getFoodById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FOODS, null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        Food food = null;
        if (cursor.moveToFirst()) {
            food = new Food();
            food.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            food.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            food.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
            food.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow("price")));
            food.setCategory(cursor.getString(cursor.getColumnIndexOrThrow("category")));
            food.setImage(cursor.getString(cursor.getColumnIndexOrThrow("image")));
            food.setAvailable(cursor.getInt(cursor.getColumnIndexOrThrow("is_available")) == 1);
        }
        cursor.close();
        return food;
    }

    public long addFoodToCart(int userId, int foodId, int quantity, String size, String specialInstructions, int extraCheese, int extraBacon, int extraSauce, int extraLettuce) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("food_id", foodId);
        values.put("quantity", quantity);
        values.put("size", size);
        values.put("special_instructions", specialInstructions);
        values.put("extra_cheese", extraCheese);
        values.put("extra_bacon", extraBacon);
        values.put("extra_sauce", extraSauce);
        values.put("extra_lettuce", extraLettuce);
        return db.insert(TABLE_CART_ITEMS, null, values);
    }

    public int editCartItem(int cartItemId, int quantity, String size) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", quantity);
        values.put("size", size);
        return db.update(TABLE_CART_ITEMS, values, "id=?", new String[]{String.valueOf(cartItemId)});
    }

    public void removeCartItem(int cartItemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART_ITEMS, "id=?", new String[]{String.valueOf(cartItemId)});
    }

    public List<CartItem> getCart(int userId) {
        List<CartItem> cart = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CART_ITEMS, null, "user_id=?", new String[]{String.valueOf(userId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                CartItem item = new CartItem();
                item.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                item.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow("user_id")));
                item.setFoodId(cursor.getInt(cursor.getColumnIndexOrThrow("food_id")));
                item.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow("quantity")));
                item.setSize(cursor.getString(cursor.getColumnIndexOrThrow("size")));
                item.setSpecialInstructions(cursor.getString(cursor.getColumnIndexOrThrow("special_instructions")));
                item.setExtraCheese(cursor.getInt(cursor.getColumnIndexOrThrow("extra_cheese")));
                item.setExtraBacon(cursor.getInt(cursor.getColumnIndexOrThrow("extra_bacon")));
                item.setExtraSauce(cursor.getInt(cursor.getColumnIndexOrThrow("extra_sauce")));
                item.setExtraLettuce(cursor.getInt(cursor.getColumnIndexOrThrow("extra_lettuce")));
                cart.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cart;
    }

    public double getCartTotalPrice(int userId) {
        double total = 0;
        List<CartItem> cartItems = getCart(userId);
        
        for (CartItem item : cartItems) {
            Food food = getFoodById(item.getFoodId());
            if (food != null) {
                double itemTotal = food.getPrice();
                
                if (item.getExtraCheese() == 1) itemTotal += 15.0;
                if (item.getExtraSauce() == 1) itemTotal += 10.0;
                if (item.getExtraLettuce() == 1) itemTotal += 20.0;
                if (item.getExtraBacon() == 1) itemTotal += 30.0;
                
                total += itemTotal * item.getQuantity();
            }
        }
        return total;
    }

    public long checkout(int userId, String deliveryAddress) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<CartItem> cartItems = getCart(userId);
        if (cartItems.isEmpty()) return -1;

        double totalPrice = getCartTotalPrice(userId);

        long orderId = -1;
        db.beginTransaction();
        try {
            ContentValues orderValues = new ContentValues();
            orderValues.put("user_id", userId);
            orderValues.put("total_price", totalPrice);
            orderValues.put("status", "Pending");
            orderValues.put("delivery_address", deliveryAddress);
            orderId = db.insert(TABLE_ORDERS, null, orderValues);

            if (orderId != -1) {
                for (CartItem item : cartItems) {
                    Food food = getFoodById(item.getFoodId());
                    if (food != null) {
                        ContentValues orderItemValues = new ContentValues();
                        orderItemValues.put("order_id", orderId);
                        orderItemValues.put("food_id", item.getFoodId());
                        orderItemValues.put("quantity", item.getQuantity());
                        orderItemValues.put("size", item.getSize());
                        orderItemValues.put("unit_price", food.getPrice());
                        orderItemValues.put("extra_cheese", item.getExtraCheese());
                        orderItemValues.put("extra_bacon", item.getExtraBacon());
                        orderItemValues.put("extra_sauce", item.getExtraSauce());
                        orderItemValues.put("extra_lettuce", item.getExtraLettuce());
                        db.insert(TABLE_ORDER_ITEMS, null, orderItemValues);
                    }
                }
                // Clear cart items
                db.delete(TABLE_CART_ITEMS, "user_id=?", new String[]{String.valueOf(userId)});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return orderId;
    }

    public List<Order> getOrders(int userId) {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ORDERS, null, "user_id=?", new String[]{String.valueOf(userId)}, null, null, "ordered_at DESC");

        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                order.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow("user_id")));
                order.setTotalPrice(cursor.getDouble(cursor.getColumnIndexOrThrow("total_price")));
                order.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                order.setDeliveryAddress(cursor.getString(cursor.getColumnIndexOrThrow("delivery_address")));
                order.setOrderedAt(cursor.getString(cursor.getColumnIndexOrThrow("ordered_at")));
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orders;
    }
}
