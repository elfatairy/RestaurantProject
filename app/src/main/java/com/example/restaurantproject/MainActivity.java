package com.example.restaurantproject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantproject.database.DatabaseHelper;
import com.example.restaurantproject.models.Food;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerFood = findViewById(R.id.recyclerFood);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.seedInitialFoods();

        List<Food> foodList = dbHelper.getFoods();

        FoodAdapter adapter = new FoodAdapter(foodList);

        recyclerFood.setLayoutManager(new LinearLayoutManager(this));
        recyclerFood.setAdapter(adapter);
        Spinner spinnerCategory = findViewById(R.id.spinnerCategory);

        String[] categories = {
                "All",
                "Burger",
                "Pizza",
                "Pasta",
                "Drinks",
                "Salad",
                "Dessert"
        };
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        categories
                );

        spinnerAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerCategory.setAdapter(spinnerAdapter);
        spinnerCategory.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        String selectedCategory =
                                parent.getItemAtPosition(position).toString();

                        adapter.filterByCategory(selectedCategory);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}