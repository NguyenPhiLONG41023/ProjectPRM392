package com.example.projectprm392;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectprm392.adapter.CartAdapter;
import com.example.projectprm392.model.CartManager;
import com.example.projectprm392.model.Product;

import java.text.DecimalFormat;
import java.util.List;

public class CartMainActivity extends AppCompatActivity {
    ListView lv;
    TextView txtTotal;
    private CartAdapter cartAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart_main);
        lv = findViewById(R.id.list_cart_item);
        txtTotal = findViewById(R.id.cart_total);
        //Lay danh sach product trong cart
        List<Product> cartItems = CartManager.getInstance().getCartItems();

        //Tinh total price
//        double total = 0;
//        for(Product item : cartItems) {
//            total += Double.parseDouble(item.getPrice());
//        }
//
//        DecimalFormat formatter = new DecimalFormat("#,###,###");
//        String formattedTotal = formatter.format(total);
//        txtTotal.setText(formattedTotal + " VND");


        cartAdapter = new CartAdapter(this, cartItems);
        lv.setAdapter(cartAdapter);

        calculateTotalPrice();
    }

    public void calculateTotalPrice() {
        List<Product> cartItems = CartManager.getInstance().getCartItems();

        double total = 0;
        for (Product item : cartItems) {
            total += Double.parseDouble(item.getPrice()) * item.getQuantity(); // Nhân với số lượng
        }

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedTotal = formatter.format(total);
        txtTotal.setText(formattedTotal + " VND");
    }

}