package com.example.projectprm392.model;

public class EditProfileRequest {
    private String userid;
    private String username;
    private String email;
    private String phonenumber;
    private String roleId;

    public EditProfileRequest(String userid, String username, String email, String phonenumber, String roleId) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.phonenumber = phonenumber;
        this.roleId = roleId;
    }
}
