package com.example.restaurantproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.restaurantproject.database.DatabaseHelper;
import com.example.restaurantproject.models.Food;
import java.util.ArrayList;
import java.util.List;

public class FoodDetailsActivities extends AppCompatActivity {

    private double basePrice = 150.0;
    private int quantity = 1;
    private int foodId = -1; // Default
    private DatabaseHelper dbHelper;

    private TextView tvQuantity;
    private Button btnAddToCart;
    private RadioGroup radioGroupSize;
    private CheckBox cbExtraCheese, cbExtraSauce, cbExtraLettuce, cbExtraBeefBacon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details_activities);

        dbHelper = new DatabaseHelper(this);

        // Get Food ID from Intent
        foodId = getIntent().getIntExtra("food_id", -1);
        if (foodId != -1) {
            Food food = dbHelper.getFoodById(foodId);
            if (food != null) {
                basePrice = food.getPrice();
            }
        }

        // ربط العناصر مع XML
        tvQuantity = findViewById(R.id.tvQuantity);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        radioGroupSize = findViewById(R.id.radioGroupSize);
        cbExtraCheese = findViewById(R.id.cbExtraCheese);
        cbExtraSauce = findViewById(R.id.cbExtraSauce);
        cbExtraLettuce = findViewById(R.id.cbExtraLettuce);
        cbExtraBeefBacon = findViewById(R.id.cbExtraBeefBacon);

        Button btnPlus = findViewById(R.id.btnPlus);
        Button btnMinus = findViewById(R.id.btnMinus);

        // برمجة زر الزيادة (+)
        btnPlus.setOnClickListener(v -> {
            quantity++;
            updateUI();
        });

        // برمجة زر النقصان (-)
        btnMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                updateUI();
            }
        });

        // التحديث عند تغيير الأحجام أو الإضافات
        radioGroupSize.setOnCheckedChangeListener((g, id) -> updateUI());
        cbExtraCheese.setOnCheckedChangeListener((v, c) -> updateUI());
        cbExtraSauce.setOnCheckedChangeListener((v, c) -> updateUI());
        cbExtraLettuce.setOnCheckedChangeListener((v, c) -> updateUI());
        cbExtraBeefBacon.setOnCheckedChangeListener((v, c) -> updateUI());

        btnAddToCart.setOnClickListener(v -> {
            if (foodId == -1) {
                Toast.makeText(this, "Error: No food selected", Toast.LENGTH_SHORT).show();
                return;
            }

            int userId = 1; // Assuming a dummy user for now
            String selectedSize = "Small";
            int id = radioGroupSize.getCheckedRadioButtonId();
            if (id == R.id.radioMedium) selectedSize = "Medium";
            else if (id == R.id.radioLarge) selectedSize = "Large";

            int extraCheese = cbExtraCheese.isChecked() ? 1 : 0;
            int extraSauce = cbExtraSauce.isChecked() ? 1 : 0;
            int extraLettuce = cbExtraLettuce.isChecked() ? 1 : 0;
            int extraBacon = cbExtraBeefBacon.isChecked() ? 1 : 0;

            dbHelper.addFoodToCart(userId, foodId, quantity, selectedSize, "", extraCheese, extraBacon, extraSauce, extraLettuce);
            Toast.makeText(this, "Added to cart successfully!", Toast.LENGTH_SHORT).show();
            finish();
        });

        updateUI();
    }

    private void updateUI() {
        double sizePrice = 0;
        int id = radioGroupSize.getCheckedRadioButtonId();
        if (id == R.id.radioMedium) sizePrice = 50.0;
        else if (id == R.id.radioLarge) sizePrice = 100.0;

        double extras = (cbExtraCheese.isChecked() ? 15 : 0)
                + (cbExtraSauce.isChecked() ? 10 : 0)
                + (cbExtraLettuce.isChecked() ? 20 : 0)
                + (cbExtraBeefBacon.isChecked() ? 30 : 0);

        double total = (basePrice + sizePrice + extras) * quantity;

        tvQuantity.setText(String.valueOf(quantity));
        btnAddToCart.setText(String.format("Add to Cart - %.2f LE", total));
    }
}