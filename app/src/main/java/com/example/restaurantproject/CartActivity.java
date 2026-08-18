package com.example.restaurantproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantproject.database.DatabaseHelper;
import com.example.restaurantproject.models.CartItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private int userId;
    private TextView tvEmptyCart, tvCartTotal;
    private RecyclerView recyclerCart;
    private Button btnCheckout;
    private View layoutCheckout;
    private CartAdapter cartAdapter;
    private List<CartItem> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        dbHelper = new DatabaseHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("user_id", -1);

        tvEmptyCart = findViewById(R.id.tvEmptyCart);
        tvCartTotal = findViewById(R.id.tvCartTotal);
        recyclerCart = findViewById(R.id.recyclerCart);
        btnCheckout = findViewById(R.id.btnCheckout);
        layoutCheckout = findViewById(R.id.layoutCheckout);

        recyclerCart.setLayoutManager(new LinearLayoutManager(this));

        loadCartData();

        btnCheckout.setOnClickListener(v -> {
            if (cartList == null || cartList.isEmpty()) {
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            long orderId = dbHelper.checkout(userId, "My Delivery Address"); // Using default for now
            if (orderId != -1) {
                Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, OrdersActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Checkout failed.", Toast.LENGTH_SHORT).show();
            }
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }
            if (item.getItemId() == R.id.action_cart) {
                // Already in CartActivity
                return true;
            }
            return false;
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_cart);
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_menu) {
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            if (item.getItemId() == R.id.nav_cart) {
                return true;
            }
            if (item.getItemId() == R.id.nav_orders) {
                startActivity(new Intent(this, OrdersActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            if (item.getItemId() == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });
    }

    private void loadCartData() {
        if (userId == -1) return;
        
        cartList = dbHelper.getCart(userId);
        if (cartList.isEmpty()) {
            tvEmptyCart.setVisibility(View.VISIBLE);
            recyclerCart.setVisibility(View.GONE);
            layoutCheckout.setVisibility(View.GONE);
        } else {
            tvEmptyCart.setVisibility(View.GONE);
            recyclerCart.setVisibility(View.VISIBLE);
            layoutCheckout.setVisibility(View.VISIBLE);
            
            cartAdapter = new CartAdapter(cartList, dbHelper, this::updateTotalPrice);
            recyclerCart.setAdapter(cartAdapter);
            updateTotalPrice();
        }
    }

    private void updateTotalPrice() {
        if (cartList.isEmpty()) {
            tvEmptyCart.setVisibility(View.VISIBLE);
            recyclerCart.setVisibility(View.GONE);
            layoutCheckout.setVisibility(View.GONE);
        } else {
            double total = dbHelper.getCartTotalPrice(userId);
            tvCartTotal.setText(String.format("Total: %.2f EGP", total));
        }
    }
}
