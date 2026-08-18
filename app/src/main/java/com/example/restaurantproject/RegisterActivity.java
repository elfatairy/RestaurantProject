package com.example.restaurantproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantproject.database.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText etRegName, etRegEmail, etRegPassword, etRegPhone, etRegAddress;
    Button btnDoRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        etRegName = findViewById(R.id.etRegName);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        etRegPhone = findViewById(R.id.etRegPhone);
        etRegAddress = findViewById(R.id.etRegAddress);
        btnDoRegister = findViewById(R.id.btnDoRegister);

        btnDoRegister.setOnClickListener(v -> {
            String name = etRegName.getText().toString();
            String email = etRegEmail.getText().toString();
            String password = etRegPassword.getText().toString();
            String phone = etRegPhone.getText().toString();
            String address = etRegAddress.getText().toString();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                long result = db.registerUser(name, email, password, phone, address);
                if (result != -1) {
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Registration Failed (Email might exist)", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}