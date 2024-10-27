package com.example.projectprm392;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectprm392.interfaceAPI.InterfaceEditProfile;
import com.example.projectprm392.model.EditProfileRequest;
import com.example.projectprm392.model.LoginResponse;
import com.example.projectprm392.model.MessageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText tvEmail;
    Button confirmEmail;
    ImageView btnBack;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        tvEmail = findViewById(R.id.forgotpassword_email);
        confirmEmail = findViewById(R.id.forgotpassword_send);
        btnBack = findViewById(R.id.forgotPassword_btnBack);
        confirmEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPassword(tvEmail.getText().toString());
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void ForgotPassword(String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5191/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterfaceEditProfile interfaceEditProfile = retrofit.create(InterfaceEditProfile.class);
        Call<MessageResponse> call = interfaceEditProfile.forgotPassword(email);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                Toast.makeText(ForgotPasswordActivity.this, "New password is sent to your email", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable throwable) {
                Toast.makeText(ForgotPasswordActivity.this, "API call failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}