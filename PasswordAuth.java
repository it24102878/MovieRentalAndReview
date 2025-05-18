package com.moviesphere.model;

public class PasswordAuth extends Authentication {
    private String fullName;
    private String password;
    private String confirmPassword;

    public PasswordAuth(String email, String fullName, String password, String confirmPassword) {
        super(email);
        this.fullName = fullName;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public boolean validate() {
        return password != null && password.equals(confirmPassword) && !password.isEmpty();
    }
}