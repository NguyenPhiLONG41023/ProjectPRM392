package com.example.projectprm392;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectprm392.interfaceAPI.InterfaceEditProfile;
import com.example.projectprm392.model.ChangePasswordRequest;
import com.example.projectprm392.model.EditProfileRequest;
import com.example.projectprm392.model.LoginResponse;
import com.example.projectprm392.model.MessageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewPasswordActivity extends AppCompatActivity {
    EditText currentPasswordEditText, newPasswordEditText, confirmPasswordEditText;
    Button resetPasswordButton;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_password);
        currentPasswordEditText = findViewById(R.id.newpassword_currentpassword);
        newPasswordEditText = findViewById(R.id.newpassword_newpassword);
        confirmPasswordEditText = findViewById(R.id.newpassword_cfpassword);
        resetPasswordButton = findViewById(R.id.login_button);
        btnBack = findViewById(R.id.newpassword_btnBack);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = currentPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                ChangePassword(currentPassword, newPassword, confirmPassword);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void ChangePassword(String currentPassword, String newPassword, String confirmNewPassword) {
        if(TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(getApplicationContext(), "Please input your currentPassword", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(newPassword)) {
            Toast.makeText(getApplicationContext(), "Please input your newPassword", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(confirmNewPassword)) {
            Toast.makeText(getApplicationContext(), "Please input your confirmNewPassword", Toast.LENGTH_SHORT).show();
        } else if (!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(getApplicationContext(), "New password and confirm new password do not match", Toast.LENGTH_SHORT).show();
        } else{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:5191/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceEditProfile interfaceEditProfile = retrofit.create(InterfaceEditProfile.class);
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
            String email = sharedPreferences.getString("EMAIL", "");
            ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(email, currentPassword, newPassword, confirmNewPassword);
            Call<MessageResponse> call = interfaceEditProfile.changePassword(changePasswordRequest);
            call.enqueue(new Callback<MessageResponse>() {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    Toast.makeText(NewPasswordActivity.this, "Change success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable throwable) {
                    Toast.makeText(NewPasswordActivity.this, "Call API fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}