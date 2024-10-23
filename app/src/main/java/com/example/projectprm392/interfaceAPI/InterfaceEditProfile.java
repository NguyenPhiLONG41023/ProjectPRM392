package com.example.projectprm392.interfaceAPI;

import com.example.projectprm392.model.EditProfileRequest;
import com.example.projectprm392.model.LoginRequest;
import com.example.projectprm392.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface InterfaceEditProfile {
    @PUT("api/User/update")
    Call<LoginResponse> editProfile(@Body EditProfileRequest editProfileRequest);
}
