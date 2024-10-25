package com.example.projectprm392;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectprm392.adapter.CartAdapter;
import com.example.projectprm392.interfaceAPI.InterfaceCreateOrder;
import com.example.projectprm392.model.CartManager;
import com.example.projectprm392.model.OrderDetail;
import com.example.projectprm392.model.OrderRequest;
import com.example.projectprm392.model.OrderResponse;
import com.example.projectprm392.model.Product;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartMainActivity extends AppCompatActivity {
    ListView lv;
    TextView txtTotal;
    private CartAdapter cartAdapter;
    ImageButton btnBack;
    Button btnCheckout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart_main);
        lv = findViewById(R.id.list_cart_item);
        txtTotal = findViewById(R.id.cart_total);
        btnBack = findViewById(R.id.btn_back);
        btnCheckout = findViewById(R.id.checkout_button);
        //Lay danh sach product trong cart
        List<Product> cartItems = CartManager.getInstance().getCartItems();

        cartAdapter = new CartAdapter(this, cartItems);
        lv.setAdapter(cartAdapter);

        calculateTotalPrice();



        //back to previous activity
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrder();
            }
        });
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

    private void createOrder() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5191/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterfaceCreateOrder interfaceCreateOrder = retrofit.create(InterfaceCreateOrder.class);
        //create object order
        List<Product> cartItems = CartManager.getInstance().getCartItems();
        double total = 0;
        for (Product item : cartItems) {
            total += Double.parseDouble(item.getPrice()) * item.getQuantity(); // Nhân với số lượng
        }
        //user id
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String userId = sharedPreferences.getString("USERID", "");
        //nhet bua 1 date vao, api se xu li date.now
        OrderRequest orderRequest = new OrderRequest(userId, "2024-10-25T06:29:22.886Z", total, "0");
        Call<OrderResponse> call = interfaceCreateOrder.createOrder(orderRequest);

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                //lay id cua order moi dc tao
                OrderResponse orderResponse = response.body();
                String orderId = orderResponse.getOrderId();
                //add orderdetail vao order moi dc tao
                processOrderDetails(orderId, cartItems);
            }
            @Override
            public void onFailure(Call<OrderResponse> call, Throwable throwable) {

            }
        });
    }

    private void processOrderDetails(String orderId, List<Product> cartItems) {
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicBoolean hasError = new AtomicBoolean(false);
         int totalItems = cartItems.size();

        for (Product product : cartItems) {
            // Create OrderDetail
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setProductId(product.getProductId());
            orderDetail.setPrice(Double.parseDouble(product.getPrice()));
            orderDetail.setQuantity(product.getQuantity());

            // Create OrderDetail
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:5191/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceCreateOrder interfaceCreateOrder = retrofit.create(InterfaceCreateOrder.class);
            interfaceCreateOrder.createOrderDetail(orderDetail).enqueue(new Callback<OrderDetail>() {
                @Override
                public void onResponse(Call<OrderDetail> call, Response<OrderDetail> response) {
                    if (response.isSuccessful()) {
                        //update product quantity
                        updateProductQuantity(interfaceCreateOrder, product.getProductId(), product.getQuantity());

                        int count = successCount.incrementAndGet();
                        if (count == totalItems && !hasError.get()) {
                            //update thanh cong, sang trang thong bao
                            runOnUiThread(() -> {
                                CartManager.getInstance().clearCart();
                                Intent intent = new Intent(CartMainActivity.this, OrderConfirmMainActivity.class);
                                startActivity(intent);
                                finish();
                            });
                        }
                    } else {
                        hasError.set(true);
                        runOnUiThread(() -> {
                            Toast.makeText(CartMainActivity.this, "Failed to create order detail", Toast.LENGTH_SHORT).show();
                        });
                    }
                }

                @Override
                public void onFailure(Call<OrderDetail> call, Throwable t) {

                }
            });
        }
    }

    private void updateProductQuantity(InterfaceCreateOrder interfaceCreateOrder, String productId, int quantity) {
        interfaceCreateOrder.updateProductQuantity(productId, quantity).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    runOnUiThread(() -> {
                        Toast.makeText(CartMainActivity.this, "Failed to update product quantity", Toast.LENGTH_SHORT).show();
                    });
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                runOnUiThread(() -> {
                    Toast.makeText(CartMainActivity.this, "Error updating product quantity: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

}