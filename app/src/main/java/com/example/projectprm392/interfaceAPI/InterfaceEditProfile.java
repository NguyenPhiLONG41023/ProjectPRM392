package com.example.projectprm392.interfaceAPI;

import com.example.projectprm392.model.ChangePasswordRequest;
import com.example.projectprm392.model.EditProfileRequest;
import com.example.projectprm392.model.LoginRequest;
import com.example.projectprm392.model.LoginResponse;
import com.example.projectprm392.model.MessageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface InterfaceEditProfile {
    @PUT("api/User/update")
    Call<LoginResponse> editProfile(@Body EditProfileRequest editProfileRequest);

    @POST("api/User/forgot-password")
    Call<MessageResponse> forgotPassword(@Query("email") String email);

    @POST("api/User/change-password")
    Call<MessageResponse> changePassword(@Body ChangePasswordRequest request);
}
