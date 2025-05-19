package com.movieuser.model;

public class CustomerUser extends User {
    public CustomerUser(String id, String name, String email, String password) {
        super(id, name, email, password);
    }

    @Override
    public String getUserType() {
        return "Customer";
    }
}