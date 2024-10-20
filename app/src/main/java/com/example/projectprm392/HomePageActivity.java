package com.example.projectprm392;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.projectprm392.adapter.ProductAdapter;
import com.example.projectprm392.model.Product;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    ImageButton menuIcon, cartIcon;
    GridView lvProduct;
    ProductAdapter adapter;
    private List<Product> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);

        menuIcon = findViewById(R.id.menu);
        cartIcon = findViewById(R.id.cart);

        //click vào menu icon thì mở menu
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });

        lvProduct = findViewById(R.id.home_listProduct);
        adapter = new ProductAdapter(this, list);
        lvProduct.setAdapter(adapter);
        new FetchPrd().execute();
    }

    private class FetchPrd extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder response = new StringBuilder();  //chua ket qua
            try{
                URL url = new URL("http://10.0.2.2:5191/api/Product");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while((line = reader.readLine()) != null) {  //doc tung dong du lieu
                    response.append(line);
                }
            }catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }catch (IOException e) {
                throw  new RuntimeException(e);
            }
            return response.toString();
        }

        //tra ve ket qua cho client
        @Override
        protected void onPostExecute(String result) {
            if(result != null && !result.isEmpty()) {
                try{
                    //dung jsonobject de lay ve doi tuong
                    JSONArray pArray = new JSONArray(result);  //tra ve mang product
                    //doc tung truong va dua vao doi tuong
                    for(int i = 0; i < pArray.length(); i++) {
                        JSONObject p = pArray.getJSONObject(i);  //lay ve doi tuong con
                        String productId = p.getString("productId");  //lay du lieu truong styleID
                        String brandName = p.getString("brandName");
                        String productName = p.getString("productName");
                        String quantity = p.getString("quantity");
                        String description = p.getString("description");
                        String price = p.getString("price");
                        String image = p.getString("image");
                        String status = p.getString("status");

                        Product product = new Product();
                        product.setProductId(productId);
                        product.setBrandName(brandName);
                        product.setProductName(productName);
                        product.setQuantity(Integer.parseInt(quantity));
                        product.setDescription(description);
                        product.setPrice(price);
                        product.setImage(image);
                        product.setStatus(Integer.parseInt(status));
                        list.add(product);
                    }

                    //refresh lai product list
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}