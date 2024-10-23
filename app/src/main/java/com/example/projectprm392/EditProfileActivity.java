package com.example.projectprm392;

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
import com.example.projectprm392.model.EditProfileRequest;
import com.example.projectprm392.model.LoginResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProfileActivity extends AppCompatActivity {
    EditText userIdField, userNameField, emailField, phoneNumberField;
    Button btnEdit;
    ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        userIdField = findViewById(R.id.editprofile_uid);
        userNameField = findViewById(R.id.editprofile_username);
        emailField = findViewById(R.id.editprofile_email);
        phoneNumberField = findViewById(R.id.editprofile_phonenumber);
        btnEdit = findViewById(R.id.edit_button);
        btnBack = findViewById(R.id.btnBack);

        //Display user infomation stored in sharepreference
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String userId = sharedPreferences.getString("USERID", "");
        String userName = sharedPreferences.getString("USERNAME", "");
        String email = sharedPreferences.getString("EMAIL", "");
        String phoneNumber = sharedPreferences.getString("PHONENUMBER", "");
        String roleId = sharedPreferences.getString("ROLEID", "");
        userIdField.setText(userId);
        userNameField.setText(userName);
        emailField.setText(email);
        phoneNumberField.setText(phoneNumber);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedUserName = userNameField.getText().toString();
                String updatedEmail = emailField.getText().toString();
                String updatedPhoneNumber = phoneNumberField.getText().toString();

                updateProfile(userId, updatedUserName, updatedEmail, updatedPhoneNumber, roleId);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateProfile(String userId, String userName, String email, String phoneNumber, String roleId) {
        if(TextUtils.isEmpty(userName)) {
            Toast.makeText(getApplicationContext(), "Please input your username", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please input your email", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(getApplicationContext(), "Please input your phonenumber", Toast.LENGTH_SHORT).show();
        }
        else{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:5191/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InterfaceEditProfile interfaceEditProfile = retrofit.create(InterfaceEditProfile.class);
            EditProfileRequest editProfileRequest = new EditProfileRequest(userId, userName, email, phoneNumber, roleId);
            Call<LoginResponse> call = interfaceEditProfile.editProfile(editProfileRequest);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    Toast.makeText(EditProfileActivity.this, "Edit profile successful!", Toast.LENGTH_SHORT).show();
                    //update sharepreference
                    saveUpToPreference(userId, userName, email, phoneNumber, roleId, true);
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(EditProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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