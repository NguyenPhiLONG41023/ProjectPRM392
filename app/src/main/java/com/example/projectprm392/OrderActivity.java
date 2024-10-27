package com.example.projectprm392;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectprm392.adapter.OrderAdapter;
import com.example.projectprm392.model.OrderResponse;
import com.example.projectprm392.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {
    ListView lvOrder;
    ImageView btnBack;
    private OrderAdapter adapter;
    private List<OrderResponse> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);
        btnBack = findViewById(R.id.order_btnBack);
        lvOrder = findViewById(R.id.order_list);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new OrderAdapter(this, list);
        lvOrder.setAdapter(adapter);
        new FetchOrd().execute();
    }

    private class FetchOrd extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder response = new StringBuilder();  //chua ket qua
            try{
                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                String userId = sharedPreferences.getString("USERID", "");
                URL url = new URL("http://10.0.2.2:5191/api/Order/getorderbyuserid?uid=" + userId);
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

        @Override
        protected void onPostExecute(String result) {
            if (result != null && !result.isEmpty()) {
                try {
                    list.clear();
                    JSONArray orderArray = new JSONArray(result);
                    for (int i = 0; i < orderArray.length(); i++) {
                        JSONObject orderJson = orderArray.getJSONObject(i);
                        OrderResponse orderResponse = new OrderResponse();
                        orderResponse.setOrderId(orderJson.getString("orderId"));
                        orderResponse.setUserId(orderJson.getString("userId"));
                        String orderDateString = orderJson.getString("orderDate");
                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                        Date orderDate = inputFormat.parse(orderDateString);
                        orderResponse.setOrderDate(orderDate);

                        // Parse and set totalPrice
                        orderResponse.setTotalPrice(orderJson.getDouble("totalPrice"));

                        // Parse and set status (convert to string based on value)
                        String statusValue = orderJson.getString("status");
                        switch (statusValue) {
                            case "0":
                                orderResponse.setStatus("Pending");
                                break;
                            case "1":
                                orderResponse.setStatus("Shipping");
                                break;
                            case "2":
                                orderResponse.setStatus("Done");
                                break;
                            default:
                                orderResponse.setStatus("Unknown");
                        }
                        list.add(orderResponse);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(OrderActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(OrderActivity.this, "Error parsing date", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(OrderActivity.this, "No orders found", Toast.LENGTH_SHORT).show();
            }
        }
    }
}