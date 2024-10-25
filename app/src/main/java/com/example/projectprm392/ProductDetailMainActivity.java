package com.example.projectprm392;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectprm392.model.CartManager;
import com.example.projectprm392.model.Product;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ProductDetailMainActivity extends AppCompatActivity {
    ImageView img;
    TextView txtName, txtPrice;
    WebView webDesc;
    Button btnAddToCart;
    ImageButton btnBack, btnCart;
    private CartManager cartManager;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail_main);
        img = findViewById(R.id.product_image);
        txtName = findViewById(R.id.product_name);
        txtPrice = findViewById(R.id.product_price);
        webDesc = findViewById(R.id.product_desc);
        btnAddToCart = findViewById(R.id.addtocart_button);
        btnBack = findViewById(R.id.btn_back);
        btnCart = findViewById(R.id.btn_cart);
        //khoi tao cart
        cartManager = CartManager.getInstance();
        //nhan du lieu tu intent
        Intent intent = getIntent();
        //lay ve product
        Product p = intent.getParcelableExtra("PRD");
        if(p != null) {
            Picasso.get().load(p.getImage()).into(img);
            txtName.setText(p.getProductName());
            //chuyen dinh dang price
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String formattedPrice = formatter.format(Double.parseDouble(p.getPrice()));
            txtPrice.setText(formattedPrice + " VND");
            String htmlDescription = p.getDescription();
            webDesc.loadData(htmlDescription, "text/html; charset=utf-8", "UTF-8");
        }
        //xu li su kien add to cart
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCartClicked();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ProductDetailMainActivity.this, CartMainActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void addToCartClicked() {
        Product p = getIntent().getParcelableExtra("PRD");

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        Product existingProduct = cartManager.getProductById(p.getProductId());

        if (existingProduct != null) {
            existingProduct.setQuantity(existingProduct.getQuantity() + 1);
        } else {
            p.setQuantity(1);
            cartManager.addToCart(p);
        }
        Intent intent = new Intent(ProductDetailMainActivity.this, CartMainActivity.class);
        startActivity(intent);
    }


}