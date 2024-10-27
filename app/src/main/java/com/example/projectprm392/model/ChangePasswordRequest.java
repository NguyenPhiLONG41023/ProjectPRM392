package com.example.projectprm392.model;

public class ChangePasswordRequest {
    private String email;
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;

    public ChangePasswordRequest(String email, String currentPassword, String newPassword, String confirmNewPassword) {
        this.email = email;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }
}
