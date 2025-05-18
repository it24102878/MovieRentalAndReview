package com.moviesphere.model;

public class OAuthAuth extends Authentication {
    private String token;

    public OAuthAuth(String email, String token) {
        super(email);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean validate() {
        return token != null && !token.isEmpty();
    }
}