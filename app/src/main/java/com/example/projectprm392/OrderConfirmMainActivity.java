package com.example.projectprm392;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OrderConfirmMainActivity extends AppCompatActivity {
    ImageView btnBack;
    Button btnContinueShopping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_confirm_main);
        btnBack = findViewById(R.id.orderconfirm_btnBack);
        btnContinueShopping = findViewById(R.id.orderconfirm_continueshopping);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHomePage();
            }
        });
        btnContinueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHomePage();
            }
        });
    }

    private void navigateToHomePage() {
        Intent intent = new Intent(OrderConfirmMainActivity.this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }
}