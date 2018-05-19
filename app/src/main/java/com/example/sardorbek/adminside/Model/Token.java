package com.example.sardorbek.adminside.Model;

/**
 * Created by sardorbek on 4/25/18.
 */

public class Token {
    public String token;
    public boolean isAdminToken;

    public Token() {
    }

    public Token(String token, boolean isAdminToken) {
        this.token = token;
        this.isAdminToken = isAdminToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAdminToken() {
        return isAdminToken;
    }

    public void setAdminToken(boolean adminToken) {
        isAdminToken = adminToken;
    }
}
