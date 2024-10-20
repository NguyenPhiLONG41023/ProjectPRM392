package com.example.projectprm392.model;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<Product> cartItems;

    private CartManager() {
        this.cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance(){
        if(instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(Product p) {
        cartItems.add(p);
    }

    public List<Product> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }

    public Product getProductById(String id) {
        for (Product product : cartItems) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        return null;
    }
}
