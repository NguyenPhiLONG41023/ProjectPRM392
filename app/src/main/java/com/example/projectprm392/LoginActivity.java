package com.example.projectprm392;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectprm392.interfaceAPI.InterfaceLogin;
import com.example.projectprm392.model.LoginRequest;
import com.example.projectprm392.model.LoginResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    TextView tvForgotPassword, tvRegister;
    Button btnLogin;
    CheckBox rememberme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.login_username);
        etPassword = findViewById(R.id.login_password);
        tvForgotPassword = findViewById(R.id.login_forgotpassword);
        tvRegister = findViewById(R.id.login_register);
        rememberme = findViewById(R.id.login_rememberme);
        btnLogin = findViewById(R.id.login_button);

        //check rememberme
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        Boolean remember = sharedPreferences.getBoolean("REMEMBER", false);
        if(remember == true) {
            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
            startActivity(intent);
        } else if(remember == false) {
            Toast.makeText(LoginActivity.this, "Please sign in", Toast.LENGTH_SHORT).show();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = etUsername.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();
                login(uname, pass);
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login(String username, String password) {
        if(TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(), "Please input your username", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please input your password", Toast.LENGTH_SHORT).show();
        }
        else{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:5191/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceLogin interfaceLogin = retrofit.create(InterfaceLogin.class);
            LoginRequest loginRequest = new LoginRequest(username, password);
            Call<LoginResponse> call = interfaceLogin.loginUser(loginRequest);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse loginResponse = response.body();
                        if (loginResponse != null) {
                            String userId = loginResponse.getUserId();
                            String userName = loginResponse.getUserName();
                            String email = loginResponse.getEmail();
                            String phoneNumber = loginResponse.getPhoneNumber();
                            String roleId = loginResponse.getRoleId();

                            Toast.makeText(LoginActivity.this, "Welcome " +  userName, Toast.LENGTH_SHORT).show();
                            saveUpToPreference(userId, userName, email, phoneNumber, roleId, rememberme.isChecked());
                            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Username or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "API call failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void saveUpToPreference(String strUID, String strUName, String strEmail
            , String strPhone, String strRoleId, boolean status) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("USERID", strUID);
            editor.putString("USERNAME", strUName);
            editor.putString("EMAIL", strEmail);
            editor.putString("PHONENUMBER", strPhone);
            editor.putString("ROLEID", strRoleId);
            editor.putBoolean("REMEMBER", status);
        editor.commit();
    }
}