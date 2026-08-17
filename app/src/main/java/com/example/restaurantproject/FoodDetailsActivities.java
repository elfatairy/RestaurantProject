package com.example.restaurantproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FoodDetailsActivities extends AppCompatActivity {

    private double basePrice = 150.0;
    private int quantity = 1;

    private TextView tvQuantity;
    private Button btnAddToCart;
    private RadioGroup radioGroupSize;
    private CheckBox cbExtraCheese, cbExtraSauce, cbExtraLettuce, cbExtraBeefBacon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details_activities);

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