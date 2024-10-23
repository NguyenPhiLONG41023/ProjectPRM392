package com.example.projectprm392.interfaceAPI;

import com.example.projectprm392.model.LoginResponse;
import com.example.projectprm392.model.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceRegister {
    @POST("api/User/register")
    Call<LoginResponse> registerUser(@Body RegisterRequest request);
}
