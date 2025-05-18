package com.moviesphere.model;

public abstract class Authentication {
    private String email;

    public Authentication(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public abstract boolean validate();
}