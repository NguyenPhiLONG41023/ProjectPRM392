package com.example.projectprm392.interfaceAPI;

import com.example.projectprm392.model.Order;
import com.example.projectprm392.model.OrderDetail;
import com.example.projectprm392.model.OrderRequest;
import com.example.projectprm392.model.OrderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceCreateOrder {
    @POST("api/Order")
    Call<OrderResponse> createOrder(@Body OrderRequest order);

    @POST("api/OrderDetail")
    Call<OrderDetail> createOrderDetail(@Body OrderDetail orderDetail);
}
