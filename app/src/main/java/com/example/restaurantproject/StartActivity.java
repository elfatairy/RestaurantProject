package com.example.restaurantproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btnGoToLogin = findViewById(R.id.btnGoToLogin);
        Button btnGoToRegister = findViewById(R.id.btnGoToRegister);

        btnGoToLogin.setOnClickListener(v -> {
            startActivity(new Intent(StartActivity.this, LoginActivity.class));
        });

        btnGoToRegister.setOnClickListener(v -> {
            startActivity(new Intent(StartActivity.this, RegisterActivity.class));
        });
    }
}