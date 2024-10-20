package com.example.projectprm392.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projectprm392.CartMainActivity;
import com.example.projectprm392.R;
import com.example.projectprm392.model.CartManager;
import com.example.projectprm392.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends ArrayAdapter<Product> {
    private CartMainActivity cartMainActivity;

    public CartAdapter(CartMainActivity context, List<Product> products) {
        super(context, 0, products);
        this.cartMainActivity = context; // Lưu tham chiếu đến CartMainActivity
    }

    @Override
    public View getView(int position, @Nullable View converview, @NonNull ViewGroup parent) {
        View listItem = converview;
        if(listItem == null) {
            listItem = LayoutInflater.from(cartMainActivity).inflate(R.layout.cart_item, parent, false);

        }
        //lay ve prd hien tai
        Product currentP = getItem(position);
        //hien thi thong tin san pham trong danh sach gio hang
        ImageView imgProduct = listItem.findViewById(R.id.cart_item_image);
        Picasso.get().load(currentP.getImage()).into(imgProduct);

        TextView tvProductName = listItem.findViewById(R.id.cart_item_name);
        tvProductName.setText(currentP.getProductName());


        TextView tvPrice = listItem.findViewById(R.id.cart_item_price);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(Double.parseDouble(currentP.getPrice()));
        tvPrice.setText(formattedPrice + " VND");

        TextView tvQuantity = listItem.findViewById(R.id.cart_item_quantity);
        tvQuantity.setText(String.valueOf(currentP.getQuantity()));

        //button
        ImageButton btnDecrease = listItem.findViewById(R.id.button_decrease_quantity);
        btnDecrease.setOnClickListener(v -> {
            if (currentP.getQuantity() > 1) {
                currentP.setQuantity(currentP.getQuantity() - 1);
                tvQuantity.setText(String.valueOf(currentP.getQuantity()));
            } else {
                removeProductFromCart(currentP);
            }
            notifyDataSetChanged();
            cartMainActivity.calculateTotalPrice();
        });

        ImageButton btnIncrease = listItem.findViewById(R.id.button_increase_quantity);
        btnIncrease.setOnClickListener(v -> {
            currentP.setQuantity(currentP.getQuantity() + 1);
            tvQuantity.setText(String.valueOf(currentP.getQuantity()));
            notifyDataSetChanged();
            cartMainActivity.calculateTotalPrice();
        });

        ImageButton btnRemove = listItem.findViewById(R.id.button_remove_item);
        btnRemove.setOnClickListener(v -> {
            removeProductFromCart(currentP);
            cartMainActivity.calculateTotalPrice();
        });

        return listItem;


    }

    private void removeProductFromCart(Product product) {
        CartManager.getInstance().getCartItems().remove(product);
        notifyDataSetChanged();
    }

}
