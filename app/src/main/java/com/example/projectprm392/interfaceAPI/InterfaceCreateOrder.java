package com.example.projectprm392.interfaceAPI;

import com.example.projectprm392.model.OrderDetail;
import com.example.projectprm392.model.OrderRequest;
import com.example.projectprm392.model.OrderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface InterfaceCreateOrder {
    //tao moi order
    @POST("api/Order")
    Call<OrderResponse> createOrder(@Body OrderRequest order);

    //tao orderdetail
    @POST("api/OrderDetail")
    Call<OrderDetail> createOrderDetail(@Body OrderDetail orderDetail);

    //update product quantity
    @PUT("api/Product/UpdateProductQuantity")
    Call<Void> updateProductQuantity(
            @Query("id") String productId,
            @Query("quantity") int quantity
    );
}
