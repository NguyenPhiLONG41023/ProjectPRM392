package com.example.projectprm392.interfaceAPI;

import com.example.projectprm392.model.LoginRequest;
import com.example.projectprm392.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceLogin {
    @POST("api/User/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
