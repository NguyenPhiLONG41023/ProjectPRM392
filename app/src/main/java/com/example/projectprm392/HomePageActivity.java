package com.example.projectprm392;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.projectprm392.adapter.ProductAdapter;
import com.example.projectprm392.model.Product;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
    EditText searchInput;
    ImageButton searchButton;

    //brandId
    private static final String APPLE_BRAND_ID = "1df36f6e-8bd1-48d6-9dc5-7e96eaa06fb8";
    private static final String XIAOMI_BRAND_ID = "7ace5a25-5572-4c53-11ec-08dce9121502";
    private static final String SAMSUNG_BRAND_ID = "cd22d23c-245e-4715-a2cd-9e8f433002e9";
    private static final String OPPO_BRAND_ID = "cho-them-cho-no-dep";

    private ImageButton brandApple, brandXiaomi, brandSamsung, brandOppo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        searchInput = findViewById(R.id.search_input);
        searchButton = findViewById(R.id.search_button);
        menuIcon = findViewById(R.id.menu);
        cartIcon = findViewById(R.id.cart);
        brandApple = findViewById(R.id.brand_apple);
        brandXiaomi = findViewById(R.id.brand_xiaomi);
        brandSamsung = findViewById(R.id.brand_samsung);
        brandOppo = findViewById(R.id.brand_oppo);

        //open menu
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });

        //search product by name
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchInput.getText().toString().trim();
                if (!searchText.isEmpty()) {
                    new SearchProduct().execute(searchText);
                } else {
                    //get all product if search is empty
                    new FetchPrd().execute();
                }
            }
        });

        //search product by brand
        setupBrandClickListeners();

        //load all product
        lvProduct = findViewById(R.id.home_listProduct);
        adapter = new ProductAdapter(this, list);
        lvProduct.setAdapter(adapter);
        new FetchPrd().execute();
    }

    private void setupBrandClickListeners() {
        brandApple.setOnClickListener(v -> new SearchByBrand().execute(APPLE_BRAND_ID));
        brandXiaomi.setOnClickListener(v -> new SearchByBrand().execute(XIAOMI_BRAND_ID));
        brandSamsung.setOnClickListener(v -> new SearchByBrand().execute(SAMSUNG_BRAND_ID));
        brandOppo.setOnClickListener(v -> new SearchByBrand().execute(OPPO_BRAND_ID));
    }

    //fetch product tu api
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

    //search product by name
    private class SearchProduct extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String searchText = params[0];
            StringBuilder response = new StringBuilder();
            try {
                URL url = new URL("http://10.0.2.2:5191/api/Product/searchbyname?search=" + searchText);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && !result.isEmpty()) {
                try {
                    list.clear();

                    JSONArray pArray = new JSONArray(result);
                    for (int i = 0; i < pArray.length(); i++) {
                        JSONObject p = pArray.getJSONObject(i);
                        String productId = p.getString("productId");
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
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(HomePageActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(HomePageActivity.this, "No products found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //search product by brand
    private class SearchByBrand extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String brandId = params[0];
            StringBuilder response = new StringBuilder();
            try {
                String urlString = "http://10.0.2.2:5191/api/Product?$filter=brandId%20eq%20" + brandId;
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && !result.isEmpty()) {
                try {
                    // Xóa list cũ
                    list.clear();

                    JSONArray pArray = new JSONArray(result);
                    for (int i = 0; i < pArray.length(); i++) {
                        JSONObject p = pArray.getJSONObject(i);
                        String productId = p.getString("productId");
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
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(HomePageActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(HomePageActivity.this, "No products found for this brand", Toast.LENGTH_SHORT).show();
            }
        }
    }
}